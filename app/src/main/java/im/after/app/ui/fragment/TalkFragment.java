package im.after.app.ui.fragment;

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

import im.after.app.R;
import im.after.app.api.TalkAPI;
import im.after.app.entity.bean.TalkBean;
import im.after.app.helper.ToastHelper;
import im.after.app.helper.UIHelper;
import im.after.app.ui.ComposeActivity;
import im.after.app.ui.adapter.BaseFragment;
import im.after.app.ui.adapter.FragmentTalkItemAdapter;

public class TalkFragment extends BaseFragment {

    private static final String TAG = TalkFragment.class.getSimpleName();

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButtonFragmentTalk;

    private FragmentTalkItemAdapter fragmentTalkItemAdapter;
    private UIHelper uiHelper;
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

        // Set UIHelper
        this.uiHelper = new UIHelper(this.getActivity());
        this.handler  = new Handler();

        return viewFragmentTalk;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set UIHelper
        this.uiHelper = new UIHelper(this.getActivity());
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
        this.requestTalkPage(this.currentPageNo);
    }

    private void setCreateTalkEvent() {
        this.floatingActionButtonFragmentTalk.setOnClickListener((View v) -> {
            Intent intent = new Intent(this.getActivity(), ComposeActivity.class);
            intent.putExtra("type", ComposeActivity.TYPE_TALK);

            this.startActivity(intent);
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
                    uiHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_request_page_error), "loadTalkPage::JSONSuccessListener"));
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
                        uiHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_not_found_page_number), pageNo));
                    }else{
                        uiHelper.alertError("Oops", message);
                    }
                }catch(Exception e) {
                    uiHelper.alertError("Oops", String.format(locale(R.string.talk_fragment_request_page_error), "loadTalkPage::JSONFailureListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            }
        );
    }

}
