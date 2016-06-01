package im.after.app.view.dashboard;

import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.afollestad.materialdialogs.MaterialDialog;
import com.orhanobut.logger.Logger;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.OnClick;
import im.after.app.R;
import im.after.app.base.BaseActivity;
import im.after.app.helper.MaterialDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.presenter.dashboard.DashboardCreatePresenter;

public class DashboardCreateActivity extends BaseActivity {

    private final static String TAG = DashboardCreateActivity.class.getSimpleName();

    @BindView(R.id.editTextDashboardCreateSubject)
    EditText mEditTextDashboardCreateSubject;

    @BindView(R.id.editTextDashboardCreateContent)
    EditText mEditTextDashboardCreateContent;

    @Inject
    DashboardCreatePresenter mDashboardCreatePresenter;

    @Inject
    MaterialDialogHelper mMaterialDialogHelper;

    @Inject
    ToastHelper mToastHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void onDestroy() {
        this.mDashboardCreatePresenter.detachView();

        super.onDestroy();
    }

    @Override
    public void initInjector() {
        this.mActivityComponent.inject(this);
    }

    @Override
    public void initContentView() {
        this.setContentView(R.layout.activity_dashboard_create);
    }

    @Override
    public void initViewAndListener() {

    }

    @Override
    public void initPresenter() {
        this.mDashboardCreatePresenter.attachView(this);
    }

    @Override
    public void initToolbar() {

    }

    @Override
    public boolean isApplyStatusBarTranslucency() {
        return false;
    }

    @OnClick(R.id.imageButtonDashboardCreateCancel)
    public void onCancel() {
        this.mDashboardCreatePresenter.doCancel(this.getContent());
    }

    @OnClick(R.id.imageButtonDashboardCreateClear)
    public void onClear() {
        this.mDashboardCreatePresenter.doClear(this.getContent());
    }

    @OnClick(R.id.imageButtonDashboardCreateCopy)
    public void onCopy() {
        this.mDashboardCreatePresenter.doCopy(TAG, this.getContent());
    }

    @OnClick(R.id.imageButtonDashboardCreateSend)
    public void onSend() {
        Logger.d("Send");
    }

    public String getContent() {
        return this.mEditTextDashboardCreateContent.getText().toString().trim();
    }

    public void showConfirmDialog(int titleResource, int contentResource, int yesResource, int noResource, MaterialDialog.SingleButtonCallback yesCallback, MaterialDialog.SingleButtonCallback noCallback) {
        this.mMaterialDialogHelper.confirm(
            this,
            this.getString(titleResource),
            this.getString(contentResource),
            this.getString(yesResource),
            this.getString(noResource),
            yesCallback,
            noCallback
        );
    }

    public void clearSubjectAndContent() {
        this.mEditTextDashboardCreateSubject.setText("");
        this.mEditTextDashboardCreateContent.setText("");
    }

    public ClipboardManager getClipboardManager() {
        return (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
    }

    public void showShortlyToast(int messageResource) {
        this.mToastHelper.showShortly(this, this.getString(messageResource));
    }

    public void close() {
        this.finish();
    }

}
