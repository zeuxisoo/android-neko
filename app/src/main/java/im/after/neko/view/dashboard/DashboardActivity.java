package im.after.neko.view.dashboard;

import android.os.Bundle;

import javax.inject.Inject;

import im.after.neko.R;
import im.after.neko.base.BaseActivity;
import im.after.neko.presenter.dashboard.DashboardPresenter;

public class DashboardActivity extends BaseActivity {

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
    public boolean isApplyStatusBarTranslucency() {
        return true;
    }

    @Override
    public void initPresenter() {
        this.mDashboardPresenter.attachView(this);
    }

}
