package im.after.app.view.dashboard;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.Toolbar;

import javax.inject.Inject;

import butterknife.BindView;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.presenter.dashboard.DashboardPresenter;

public class DashboardActivity extends BaseActivity {

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

}
