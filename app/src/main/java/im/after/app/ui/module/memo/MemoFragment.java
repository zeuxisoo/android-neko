package im.after.app.ui.module.memo;

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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.api.MemoAPI;
import im.after.app.entity.bean.MemoBean;
import im.after.app.entity.bean.MemoDialogItemBean;
import im.after.app.entity.bean.MemoItemBean;
import im.after.app.helper.SweetDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.ui.base.BaseFragment;
import im.after.app.ui.listener.RecyclerViewItemClickListener;

public class MemoFragment extends BaseFragment {

    private static final String TAG = MemoFragment.class.getSimpleName();

    private static final int REQUEST_CODE_COMPOSE = 1;
    private static final int REQUEST_CODE_EDIT    = 2;

    private SwipeRefreshLayout swipeRefreshLayoutFragmentMemo;
    private RecyclerView recyclerViewFragmentMemo;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButtonFragmentMemo;

    private MemoItemFragmentAdapter memoItemFragmentAdapter;
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
        View viewFragmentMemo = inflater.inflate(R.layout.fragment_memo, container, false);

        this.swipeRefreshLayoutFragmentMemo   = (SwipeRefreshLayout) viewFragmentMemo.findViewById(R.id.swipeRefreshLayoutFragmentMemo);
        this.recyclerViewFragmentMemo         = (RecyclerView) viewFragmentMemo.findViewById(R.id.recyclerViewFragmentMemo);
        this.floatingActionButtonFragmentMemo = (FloatingActionButton) viewFragmentMemo.findViewById(R.id.floatingActionButtonFragmentMemo);

        return viewFragmentMemo;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set base object
        this.sweetDialogHelper = new SweetDialogHelper(this.getActivity());
        this.handler  = new Handler();

        // Set recycler view is not fixed size, layout manager and adapter
        this.linearLayoutManager = new LinearLayoutManager(this.getActivity());
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.memoItemFragmentAdapter = new MemoItemFragmentAdapter();

        this.recyclerViewFragmentMemo.setHasFixedSize(false);
        this.recyclerViewFragmentMemo.setAdapter(this.memoItemFragmentAdapter);
        this.recyclerViewFragmentMemo.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewFragmentMemo.setLayoutManager(this.linearLayoutManager);

        // Attach float button to recyler view
        this.floatingActionButtonFragmentMemo.attachToRecyclerView(this.recyclerViewFragmentMemo);

        // Set action
        this.setCreateMemoEvent();
        this.setPullDownEvent();
        this.setLoadMoreEvent();
        this.setClickItemEvent();
        this.requestMemoPage(this.currentPageNo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.getActivity().RESULT_OK) {
            MemoItemBean memoItemBean;
            int memoItemBeanPosition;

            switch (requestCode) {
                case REQUEST_CODE_COMPOSE:
                    memoItemBean = (MemoItemBean) data.getExtras().getSerializable("memoItemBean");

                    this.memoItemFragmentAdapter.prependMemoItemBean(memoItemBean);

                    ToastHelper.show(this.getActivity(), R.string.memo_fragment_compose_memo_created);
                    break;
                case REQUEST_CODE_EDIT:
                    memoItemBean = (MemoItemBean) data.getExtras().getSerializable("memoItemBean");
                    memoItemBeanPosition = data.getExtras().getInt("memoItemBeanPosition");

                    this.memoItemFragmentAdapter.setMemoItemBean(memoItemBeanPosition, memoItemBean);

                    ToastHelper.show(this.getActivity(), R.string.memo_fragment_compose_memo_updated);
                default:
                    break;
            }
        }
    }

    private void setCreateMemoEvent() {
        this.floatingActionButtonFragmentMemo.setOnClickListener((View v) -> {
            Intent intent = new Intent(this.getActivity(), MemoCreateActivity.class);
            intent.putExtra("type", MemoCreateActivity.TYPE_MEMO);

            this.startActivityForResult(intent, REQUEST_CODE_COMPOSE);
        });
    }

    private void setPullDownEvent() {
        this.swipeRefreshLayoutFragmentMemo.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        );
        this.swipeRefreshLayoutFragmentMemo.setOnRefreshListener(() -> {
            this.floatingActionButtonFragmentMemo.hide();
            this.swipeRefreshLayoutFragmentMemo.setRefreshing(true);
            this.memoItemFragmentAdapter.clearMemoItemBeans();

            this.requestMemoPage(1, () -> {
                this.currentPageNo = 1;
                this.floatingActionButtonFragmentMemo.show();
                this.swipeRefreshLayoutFragmentMemo.setRefreshing(false);
            });
        });
    }

    private void setLoadMoreEvent() {
        this.recyclerViewFragmentMemo.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!pageIsLoading && pageHasNext && memoItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    pageIsLoading = true;

                    currentPageNo = currentPageNo + 1;

                    ToastHelper.show(
                        getActivity(),
                        String.format(getActivity().getString(R.string.memo_fragment_current_loading_page), currentPageNo)
                    );

                    requestMemoPage(currentPageNo);
                }

                if (!showingNoMore && !pageHasNext && memoItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    showingNoMore = true;

                    ToastHelper.show(getActivity(), R.string.memo_fragment_no_more_page);

                    handler.postDelayed(() -> showingNoMore = false, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Fix swipe refresh layout and recycler view will cut the card view item
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewFragmentMemo.getLayoutManager();

                swipeRefreshLayoutFragmentMemo.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);

                // Hide the button when scroll down, show the button when scroll up
                if (dy > 0) {
                    floatingActionButtonFragmentMemo.hide();
                }else{
                    floatingActionButtonFragmentMemo.show();
                }
            }

        });
    }

    private void setClickItemEvent() {
        this.recyclerViewFragmentMemo.addOnItemTouchListener(new RecyclerViewItemClickListener(this.getActivity(), this.recyclerViewFragmentMemo, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showOptionsMenu(position);
            }
        }));
    }

    private void requestMemoPage(int pageNo) {
        this.requestMemoPage(pageNo, () -> {});
    }

    private void requestMemoPage(int pageNo, RequestPageFinishedCallBack callback) {
        this.pageIsLoading = true;

        MemoAPI memoAPI = new MemoAPI(this.getActivity());

        memoAPI.page(
            pageNo,
            (JSONObject response) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    MemoBean memoBean = objectMapper.readValue(response.toString(), MemoBean.class);

                    this.pageHasNext = (memoBean.getNext() != null);

                    if (pageNo > 1) {
                        this.memoItemFragmentAdapter.addMemoItemBeans(memoBean.getItems());
                    }else{
                        this.memoItemFragmentAdapter.setMemoItemBeans(memoBean.getItems());
                    }

                    callback.onFinish();
                }catch(Exception e) {
                    this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_request_page_error), "loadTalkPage::JSONSuccessListener"));
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
                        this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_not_found_page_number), pageNo));
                    }else{
                        this.sweetDialogHelper.alertError("Oops", message);
                    }
                }catch(Exception e) {
                    this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_request_page_error), "loadTalkPage::JSONFailureListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            }
        );
    }

    private void showOptionsMenu(int position) {
        ArrayList<MemoDialogItemBean> dialogMemoItem = new ArrayList<MemoDialogItemBean>();
        dialogMemoItem.add(new MemoDialogItemBean(R.drawable.ic_edit_white, locale(R.string.memo_fragment_dialog_item_edit)));
        dialogMemoItem.add(new MemoDialogItemBean(R.drawable.ic_delete_white, locale(R.string.memo_fragment_dialog_item_delete)));

        MemoItemDialogAdapter memoItemDialogAdapter = new MemoItemDialogAdapter(this.getActivity(), R.layout.dialog_memo_item, dialogMemoItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setAdapter(memoItemDialogAdapter, (DialogInterface dialog, int which) -> {
            this.clickOnOptionsMenuItem(dialog, which, position);
        });
        builder.show();
    }

    public void clickOnOptionsMenuItem(DialogInterface dialog, int which, int position) {
        MemoItemBean memoItemBean = this.memoItemFragmentAdapter.getMemoItem(position);

        switch(which) {
            case 0: // edit
                Intent intent = new Intent(this.getActivity(), MemoEditActivity.class);
                intent.putExtra("type", MemoCreateActivity.TYPE_MEMO);
                intent.putExtra("memoItemBean", memoItemBean);
                intent.putExtra("memoItemBeanPosition", position);

                this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case 1: // delete
                this.sweetDialogHelper.confirm(
                    locale(R.string.memo_fragment_confirm_delete_title),
                    locale(R.string.memo_fragment_confirm_delete_content),
                    locale(R.string.memo_fragment_confirm_delete_yes),
                    () -> {
                        MemoAPI memoAPI = new MemoAPI(this.getActivity());

                        memoAPI.delete(
                            memoItemBean.getId(),
                            (JSONObject response) -> {
                                try {
                                    String message = response.getString("message");
                                    int status     = response.getInt("status");

                                    if (status == 200) {
                                        this.memoItemFragmentAdapter.removeMemoItemBean(position);

                                        ToastHelper.show(this.getActivity(), message);
                                    }else{
                                        this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_delete_memo_error), message));
                                    }
                                }catch(Exception e) {
                                    this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_delete_memo_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            },
                            (JSONObject response) -> {
                                try {
                                    JSONObject errorObject = response.getJSONObject("error");
                                    String message = errorObject.getString("message");

                                    this.sweetDialogHelper.alertError("Oops", message);
                                }catch(Exception e) {
                                    this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_fragment_delete_memo_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            }
                        );
                    }
                );
                break;
        }
    }

}
