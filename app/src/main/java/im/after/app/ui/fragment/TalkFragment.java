package im.after.app.ui.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.api.TalkAPI;
import im.after.app.entity.bean.TalkBean;
import im.after.app.entity.bean.TalkDialogItemBean;
import im.after.app.entity.bean.TalkItemBean;
import im.after.app.helper.SweetDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.ui.ComposeActivity;
import im.after.app.ui.adapter.BaseFragment;
import im.after.app.ui.adapter.DialogTalkItemAdapter;
import im.after.app.ui.adapter.FragmentTalkItemAdapter;
import im.after.app.ui.listener.RecyclerViewItemClickListener;

public class TalkFragment extends BaseFragment {

    private static final String TAG = TalkFragment.class.getSimpleName();

    private static final int REQUEST_CODE_COMPOSE = 1;

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButtonFragmentTalk;

    private FragmentTalkItemAdapter fragmentTalkItemAdapter;
    private SweetDialogHelper sweetDialogHelper;
    private Handler handler;

    private int currentPageNo     = 1;
    private boolean pageHasNext   = false;
    private boolean pageIsLoading = false;
    private boolean showingNoMore = false;

    interface RequestPageFinishedCallBack {
        void onFinish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewFragmentTalk = inflater.inflate(R.layout.fragment_talk, container, false);

        this.swipeRefreshLayoutFragmentTalk   = (SwipeRefreshLayout) viewFragmentTalk.findViewById(R.id.swipeRefreshLayoutFragmentTalk);
        this.recyclerViewFragmentTalk         = (RecyclerView) viewFragmentTalk.findViewById(R.id.recyclerViewFragmentTalk);
        this.floatingActionButtonFragmentTalk = (FloatingActionButton) viewFragmentTalk.findViewById(R.id.floatingActionButtonFragmentTalk);

        // Set base object
        this.sweetDialogHelper = new SweetDialogHelper(this.getActivity());
        this.handler           = new Handler();

        return viewFragmentTalk;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set UIHelper
        this.sweetDialogHelper = new SweetDialogHelper(this.getActivity());
        this.handler  = new Handler();

        // Set recycler view is not fixed size, layout manager and adapter
        this.linearLayoutManager = new LinearLayoutManager(this.getActivity());
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.fragmentTalkItemAdapter = new FragmentTalkItemAdapter();

        this.recyclerViewFragmentTalk.setHasFixedSize(false);
        this.recyclerViewFragmentTalk.setAdapter(this.fragmentTalkItemAdapter);
        this.recyclerViewFragmentTalk.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewFragmentTalk.setLayoutManager(this.linearLayoutManager);

        // Attach float button to recyler view
        this.floatingActionButtonFragmentTalk.attachToRecyclerView(this.recyclerViewFragmentTalk);

        // Set action
        this.setCreateTalkEvent();
        this.setPullDownEvent();
        this.setLoadMoreEvent();
        this.setClickItemEvent();
        this.requestTalkPage(this.currentPageNo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch(requestCode) {
            case REQUEST_CODE_COMPOSE:
                TalkItemBean talkItemBean = (TalkItemBean) data.getExtras().getSerializable("talkItemBean");

                this.fragmentTalkItemAdapter.prependTalkItemBean(talkItemBean);

                ToastHelper.show(this.getActivity(), R.string.talk_fragment_compose_talk_created);
                break;
        }
    }

    private void setCreateTalkEvent() {
        this.floatingActionButtonFragmentTalk.setOnClickListener((View v) -> {
            Intent intent = new Intent(this.getActivity(), ComposeActivity.class);
            intent.putExtra("type", ComposeActivity.TYPE_TALK);

            this.startActivityForResult(intent, REQUEST_CODE_COMPOSE);
        });
    }

    private void setPullDownEvent() {
        this.swipeRefreshLayoutFragmentTalk.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        );
        this.swipeRefreshLayoutFragmentTalk.setOnRefreshListener(() -> {
            this.floatingActionButtonFragmentTalk.hide();
            this.swipeRefreshLayoutFragmentTalk.setRefreshing(true);
            this.fragmentTalkItemAdapter.clearTalkItemBeans();

            this.requestTalkPage(1, () -> {
                this.currentPageNo = 1;
                this.floatingActionButtonFragmentTalk.show();
                this.swipeRefreshLayoutFragmentTalk.setRefreshing(false);
            });
        });
    }

    private void setLoadMoreEvent() {
        this.recyclerViewFragmentTalk.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!pageIsLoading && pageHasNext && fragmentTalkItemAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    pageIsLoading = true;

                    currentPageNo = currentPageNo + 1;

                    ToastHelper.show(
                        getActivity(),
                        String.format(getActivity().getString(R.string.talk_fragment_current_loading_page), currentPageNo)
                    );

                    requestTalkPage(currentPageNo);
                }

                if (!showingNoMore && !pageHasNext && fragmentTalkItemAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    showingNoMore = true;

                    ToastHelper.show(getActivity(), R.string.talk_fragment_no_more_page);

                    handler.postDelayed(() -> showingNoMore = false, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Fix swipe refresh layout and recycler view will cut the card view item
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewFragmentTalk.getLayoutManager();

                swipeRefreshLayoutFragmentTalk.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);

                // Hide the button when scroll down, show the button when scroll up
                if (dy > 0) {
                    floatingActionButtonFragmentTalk.hide();
                }else{
                    floatingActionButtonFragmentTalk.show();
                }
            }

        });
    }

    private void setClickItemEvent() {
        this.recyclerViewFragmentTalk.addOnItemTouchListener(new RecyclerViewItemClickListener(this.getActivity(), this.recyclerViewFragmentTalk, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showOptionsMenu(position);
            }
        }));
    }

    private void requestTalkPage(int pageNo) {
        this.requestTalkPage(pageNo, () -> {});
    }

    private void requestTalkPage(int pageNo, RequestPageFinishedCallBack callback) {
        this.pageIsLoading = true;

        TalkAPI talkAPI = new TalkAPI(this.getActivity());

        talkAPI.page(
            pageNo,
            (JSONObject response) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    TalkBean talkBean = objectMapper.readValue(response.toString(), TalkBean.class);

                    this.pageHasNext = (talkBean.getNext() != null);

                    if (pageNo > 1) {
                        this.fragmentTalkItemAdapter.addTalkItemBeans(talkBean.getItems());
                    }else{
                        this.fragmentTalkItemAdapter.setTalkItemBeans(talkBean.getItems());
                    }

                    callback.onFinish();
                }catch(Exception e) {
                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_request_page_error), "loadTalkPage::JSONSuccessListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            },
            (JSONObject response) -> {
                try {
                    JSONObject errorObject = response.getJSONObject("error");
                    String message = errorObject.getString("message");
                    int status     = errorObject.getInt("status");

                    if (status == 404) {
                        sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_not_found_page_number), pageNo));
                    }else{
                        sweetDialogHelper.alertError("Oops", message);
                    }
                }catch(Exception e) {
                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_request_page_error), "loadTalkPage::JSONFailureListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            }
        );
    }

    private void showOptionsMenu(int position) {
        ArrayList<TalkDialogItemBean> dialogTalkItem = new ArrayList<TalkDialogItemBean>();
        dialogTalkItem.add(new TalkDialogItemBean(R.drawable.ic_edit_white, this.getString(R.string.talk_fragment_dialog_item_edit)));
        dialogTalkItem.add(new TalkDialogItemBean(R.drawable.ic_delete_white, this.getString(R.string.talk_fragment_dialog_item_delete)));

        DialogTalkItemAdapter dialogTalkItemAdapter = new DialogTalkItemAdapter(this.getActivity(), R.layout.dialog_talk_item, dialogTalkItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setAdapter(dialogTalkItemAdapter, (DialogInterface dialog, int which) -> {
            this.clickOnOptionsMenuItem(dialog, which, position);
        });
        builder.show();
    }

    public void clickOnOptionsMenuItem(DialogInterface dialog, int which, int position) {
        TalkItemBean talkItemBean = this.fragmentTalkItemAdapter.getTalkItem(position);

        switch(which) {
            case 0: // edit
                Log.d(TAG, "Edit > " + talkItemBean.getContent());
                break;
            case 1: // delete
                Log.d(TAG, "Delete > " + talkItemBean.getContent());
                break;
        }
    }

}
