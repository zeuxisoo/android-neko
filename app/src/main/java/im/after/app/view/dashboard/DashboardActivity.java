package im.after.app.view.dashboard;

import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;

import javax.inject.Inject;

import butterknife.BindView;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.presenter.dashboard.DashboardPresenter;

public class DashboardActivity extends BaseActivity {

    @BindView(R.id.linearLayoutDashboards)
    LinearLayout mLinearLayoutDashboards;

    @BindView(R.id.toolbarWidget)
    Toolbar mToolbar;

    @Inject
    DashboardPresenter mDashboardPresenter;

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

}
