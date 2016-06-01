package im.after.app.view.dashboard;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.widget.EditText;

import com.afollestad.materialdialogs.DialogAction;
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
        if (this.mEditTextDashboardCreateContent.length() > 0) {
            this.mMaterialDialogHelper.confirm(
                this,
                this.getString(R.string.dialog_confirm_shared_cancel_title),
                this.getString(R.string.dialog_confirm_dashboard_create_cancel_content),
                this.getString(R.string.dialog_confirm_shared_cancel_yes),
                this.getString(R.string.dialog_confirm_shared_cancel_no),
                (MaterialDialog dialog, DialogAction which) -> {
                    dialog.dismiss();

                    this.close();
                },
                (MaterialDialog dialog, DialogAction which) -> dialog.dismiss()
            );
        }else{
            this.close();
        }
    }

    @OnClick(R.id.imageButtonDashboardCreateClear)
    public void onClear() {
        if (this.mEditTextDashboardCreateContent.length() > 0) {
            this.mMaterialDialogHelper.confirm(
                this,
                this.getString(R.string.dialog_confirm_shared_cancel_title),
                this.getString(R.string.dialog_confirm_dashboard_create_clear_content),
                this.getString(R.string.dialog_confirm_shared_cancel_yes),
                this.getString(R.string.dialog_confirm_shared_cancel_no),
                (MaterialDialog dialog, DialogAction which) -> {
                    dialog.dismiss();

                    this.mEditTextDashboardCreateSubject.setText("");
                    this.mEditTextDashboardCreateContent.setText("");
                },
                (MaterialDialog dialog, DialogAction which) -> dialog.dismiss()
            );
        }else{
            this.close();
        }
    }

    @OnClick(R.id.imageButtonDashboardCreateCopy)
    public void onCopy() {
        ClipData clipData = ClipData.newPlainText(TAG, this.mEditTextDashboardCreateContent.getText());

        ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(CLIPBOARD_SERVICE);
        clipboardManager.setPrimaryClip(clipData);

        this.mToastHelper.showShortly(this, this.getString(R.string.toast_dashboard_create_copy_success));
    }

    @OnClick(R.id.imageButtonDashboardCreateSend)
    public void onSend() {
        Logger.d("Send");
    }

    private void close() {
        this.finish();
    }

}
