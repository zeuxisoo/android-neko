package im.after.app.view.dashboard;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import com.malinskiy.superrecyclerview.SuperRecyclerView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.data.api.dashboard.bean.DashboardBean;
import im.after.app.presenter.dashboard.DashboardPresenter;
import jp.wasabeef.recyclerview.animators.SlideInUpAnimator;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.linearLayoutDashboards)
    LinearLayout mLinearLayoutDashboards;

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
        this.mSuperRecyclerViewBoards.getRecyclerView().setItemAnimator(new SlideInUpAnimator());
        this.mSuperRecyclerViewBoards.setLayoutManager(linearLayoutManager);
        this.mSuperRecyclerViewBoards.setAdapter(this.mDashboardAdapter);

        this.mSuperRecyclerViewBoards.setRefreshingColorResources(android.R.color.holo_orange_light, android.R.color.holo_blue_light, android.R.color.holo_green_light, android.R.color.holo_red_light);
        this.mSuperRecyclerViewBoards.setRefreshListener(this.mDashboardPresenter::onRefresh);
        this.mSuperRecyclerViewBoards.setupMoreListener(this.mDashboardPresenter::onMore, 2);

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

    public void showSnackbar(String message) {
        super.showSnackbar(this.mLinearLayoutDashboards, message, Snackbar.LENGTH_LONG);
    }

    public void clearDashboardList() {
        this.mDashboardAdapter.clear();
    }

    public void renderDashboardList(ArrayList<DashboardBean> dashboardBeanArrayList) {
        this.mDashboardAdapter.bind(dashboardBeanArrayList);
    }

    public void stopRefreshAnimation() {
        this.mSuperRecyclerViewBoards.setRefreshing(false);
    }

    public void stopMoreAnimation() {
        this.mSuperRecyclerViewBoards.hideMoreProgress();
    }

}
