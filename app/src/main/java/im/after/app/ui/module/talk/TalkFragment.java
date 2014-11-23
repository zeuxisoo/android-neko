package im.after.app.ui.module.talk;

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
import im.after.app.ui.base.BaseFragment;
import im.after.app.ui.listener.RecyclerViewItemClickListener;

public class TalkFragment extends BaseFragment {

    private static final String TAG = TalkFragment.class.getSimpleName();

    private static final int REQUEST_CODE_COMPOSE = 1;
    private static final int REQUEST_CODE_EDIT    = 2;

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButtonFragmentTalk;

    private TalkItemFragmentAdapter talkItemFragmentAdapter;
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

        this.talkItemFragmentAdapter = new TalkItemFragmentAdapter();

        this.recyclerViewFragmentTalk.setHasFixedSize(false);
        this.recyclerViewFragmentTalk.setAdapter(this.talkItemFragmentAdapter);
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

        if (resultCode == this.getActivity().RESULT_OK) {
            TalkItemBean talkItemBean;
            int talkItemBeanPosition;

            switch (requestCode) {
                case REQUEST_CODE_COMPOSE:
                    talkItemBean = (TalkItemBean) data.getExtras().getSerializable("talkItemBean");

                    this.talkItemFragmentAdapter.prependTalkItemBean(talkItemBean);

                    ToastHelper.show(this.getActivity(), R.string.talk_fragment_compose_talk_created);
                    break;
                case REQUEST_CODE_EDIT:
                    talkItemBean = (TalkItemBean) data.getExtras().getSerializable("talkItemBean");
                    talkItemBeanPosition = data.getExtras().getInt("talkItemBeanPosition");

                    this.talkItemFragmentAdapter.setTalkItemBean(talkItemBeanPosition, talkItemBean);

                    ToastHelper.show(this.getActivity(), R.string.talk_fragment_compose_talk_updated);
                default:
                    break;
            }
        }
    }

    private void setCreateTalkEvent() {
        this.floatingActionButtonFragmentTalk.setOnClickListener((View v) -> {
            Intent intent = new Intent(this.getActivity(), TalkCreateActivity.class);
            intent.putExtra("type", TalkCreateActivity.TYPE_TALK);

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
            this.talkItemFragmentAdapter.clearTalkItemBeans();

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

                if (!pageIsLoading && pageHasNext && talkItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    pageIsLoading = true;

                    currentPageNo = currentPageNo + 1;

                    ToastHelper.show(
                        getActivity(),
                        String.format(getActivity().getString(R.string.talk_fragment_current_loading_page), currentPageNo)
                    );

                    requestTalkPage(currentPageNo);
                }

                if (!showingNoMore && !pageHasNext && talkItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
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
                        this.talkItemFragmentAdapter.addTalkItemBeans(talkBean.getItems());
                    }else{
                        this.talkItemFragmentAdapter.setTalkItemBeans(talkBean.getItems());
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
        dialogTalkItem.add(new TalkDialogItemBean(R.drawable.ic_edit_white, locale(R.string.talk_fragment_dialog_item_edit)));
        dialogTalkItem.add(new TalkDialogItemBean(R.drawable.ic_delete_white, locale(R.string.talk_fragment_dialog_item_delete)));

        TalkItemDialogAdapter talkItemDialogAdapter = new TalkItemDialogAdapter(this.getActivity(), R.layout.dialog_talk_item, dialogTalkItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setAdapter(talkItemDialogAdapter, (DialogInterface dialog, int which) -> {
            this.clickOnOptionsMenuItem(dialog, which, position);
        });
        builder.show();
    }

    public void clickOnOptionsMenuItem(DialogInterface dialog, int which, int position) {
        TalkItemBean talkItemBean = this.talkItemFragmentAdapter.getTalkItem(position);

        switch(which) {
            case 0: // edit
                Log.d(TAG, "Edit > " + talkItemBean.getContent());

                Intent intent = new Intent(this.getActivity(), TalkEditActivity.class);
                intent.putExtra("type", TalkCreateActivity.TYPE_TALK);
                intent.putExtra("talkItemBean", talkItemBean);
                intent.putExtra("talkItemBeanPosition", position);

                this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case 1: // delete
                this.sweetDialogHelper.confirm(
                    locale(R.string.talk_fragment_confirm_delete_title),
                    locale(R.string.talk_fragment_confirm_delete_content),
                    locale(R.string.talk_fragment_confirm_delete_yes),
                    () -> {
                        TalkAPI talkAPI = new TalkAPI(this.getActivity());

                        talkAPI.delete(
                            talkItemBean.getId(),
                            (JSONObject response) -> {
                                try {
                                    String message = response.getString("message");
                                    int status     = response.getInt("status");

                                    if (status == 200) {
                                        this.talkItemFragmentAdapter.removeTalkItemBean(position);

                                        ToastHelper.show(this.getActivity(), message);
                                    }else{
                                        sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_delete_talk_error), message));
                                    }
                                }catch(Exception e) {
                                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_delete_talk_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            },
                            (JSONObject response) -> {
                                try {
                                    JSONObject errorObject = response.getJSONObject("error");
                                    String message = errorObject.getString("message");

                                    sweetDialogHelper.alertError("Oops", message);
                                }catch(Exception e) {
                                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_delete_talk_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            }
                        );
                    }
                );
                break;
        }
    }

}
