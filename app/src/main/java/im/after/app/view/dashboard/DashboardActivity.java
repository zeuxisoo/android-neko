package im.after.app.view.dashboard;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.animation.OvershootInterpolator;
import android.widget.LinearLayout;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.data.api.dashboard.bean.DashboardItemBean;
import im.after.app.presenter.dashboard.DashboardPresenter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DashboardActivity extends BaseActivity {

    private static final int REQUEST_CODE_CREATE = 1;

    @BindView(R.id.coordinatorLayoutDashboards)
    CoordinatorLayout mCoordinatorLayoutDashboards;

    @BindView(R.id.toolbarWidget)
    Toolbar mToolbar;

    @BindView(R.id.superRecyclerViewBoards)
    SuperRecyclerView mSuperRecyclerViewBoards;

    @Inject
    DashboardPresenter mDashboardPresenter;

    @Inject
    DashboardAdapter mDashboardAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DashboardCreateActivity.RESULT_OK) {
            switch(resultCode) {
                case REQUEST_CODE_CREATE:
                    DashboardItemBean dashboardItemBean = (DashboardItemBean) data.getExtras().getSerializable(DashboardCreateActivity.EXTRA_DATA_DASHBOARD_ITEM);

                    if (dashboardItemBean != null) {
                        this.mDashboardPresenter.appendDashboardItemBean(dashboardItemBean);
                    }
                    break;
            }
        }
    }

    @Override
    protected void onDestroy() {
        this.mDashboardPresenter.detachView();

        super.onDestroy();
    }

    @Override
    public void initInjector() {
        this.mActivityComponent.inject(this);
    }

    @Override
    public void initContentView() {
        this.setContentView(R.layout.activity_dashboard);
    }

    @Override
    public void initViewAndListener() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(LinearLayout.VERTICAL);

        this.mSuperRecyclerViewBoards.getRecyclerView().setHasFixedSize(false);
        this.mSuperRecyclerViewBoards.getRecyclerView().setItemAnimator(new SlideInUpAnimator(new OvershootInterpolator(1f)));
        this.mSuperRecyclerViewBoards.setLayoutManager(linearLayoutManager);
        this.mSuperRecyclerViewBoards.setAdapter(this.mDashboardAdapter);

        this.mSuperRecyclerViewBoards.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        this.mSuperRecyclerViewBoards.setRefreshListener(this.mDashboardPresenter::onRefresh);
        this.mSuperRecyclerViewBoards.setupMoreListener(this.mDashboardPresenter::onMore, 8);

        this.mDashboardPresenter.loadDashboards(1);
    }

    @Override
    public void initPresenter() {
        this.mDashboardPresenter.attachView(this);
    }

    @Override
    public void initToolbar() {
        this.setSupportActionBar(this.mToolbar);

        ActionBar actionBar = this.getSupportActionBar();

        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowHomeEnabled(true);
        }
    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @OnClick(R.id.floatingActionButtonCreateBoard)
    public void createBoard() {
        Intent intent = new Intent(this, DashboardCreateActivity.class);

        this.startActivityForResult(intent, REQUEST_CODE_CREATE);
    }

    public void showSnackbar(String message) {
        super.showSnackbar(this.mCoordinatorLayoutDashboards, message, Snackbar.LENGTH_LONG);
    }

    public void clearDashboardList() {
        this.mDashboardAdapter.clear();
    }

    public void renderDashboardList(ArrayList<DashboardItemBean> dashboardBeanArrayList) {
        this.mDashboardAdapter.bind(dashboardBeanArrayList);
    }

    public void renderMoreDashboardList(ArrayList<DashboardItemBean> dashboardBeanArrayList) {
        this.mDashboardAdapter.append(dashboardBeanArrayList);
    }

    public void stopRefreshAnimation() {
        this.mSuperRecyclerViewBoards.setRefreshing(false);
    }

    public void stopMoreAnimation() {
        this.mSuperRecyclerViewBoards.hideMoreProgress();
    }

    public void appendDashboardItem(DashboardItemBean dashboardItemBean) {
        this.mDashboardAdapter.prepend(dashboardItemBean);
        this.mSuperRecyclerViewBoards.getRecyclerView().scrollToPosition(0);
    }

}
