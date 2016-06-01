package im.after.app.presenter.dashboard;

import android.content.ClipData;
import android.content.ClipboardManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;

import javax.inject.Inject;

import im.after.app.R;
import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.contract.dashboard.DashboardCreateContract;
import im.after.app.view.dashboard.DashboardCreateActivity;

public class DashboardCreatePresenter extends BasePresenter implements DashboardCreateContract {

    private DashboardCreateActivity mDashboardCreateActivity;

    @Inject
    public DashboardCreatePresenter() {
    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mDashboardCreateActivity = (DashboardCreateActivity) view;
    }

    @Override
    public void detachView() {
        this.mDashboardCreateActivity = null;
    }

    // Implementation for DashboardCreateContract
    public void doCancel(String content) {
        if (!content.isEmpty()) {
            this.mDashboardCreateActivity.showConfirmDialog(
                R.string.dialog_confirm_shared_cancel_title,
                R.string.dialog_confirm_dashboard_create_cancel_content,
                R.string.dialog_confirm_shared_cancel_yes,
                R.string.dialog_confirm_shared_cancel_no,
                (MaterialDialog dialog, DialogAction which) -> {
                    dialog.dismiss();

                    this.mDashboardCreateActivity.close();
                },
                (MaterialDialog dialog, DialogAction which) -> dialog.dismiss()
            );
        }else{
            this.mDashboardCreateActivity.close();
        }
    }

    @Override
    public void doClear(String content) {
        if (!content.isEmpty()) {
            this.mDashboardCreateActivity.showConfirmDialog(
                R.string.dialog_confirm_shared_cancel_title,
                R.string.dialog_confirm_dashboard_create_clear_content,
                R.string.dialog_confirm_shared_cancel_yes,
                R.string.dialog_confirm_shared_cancel_no,
                (MaterialDialog dialog, DialogAction which) -> {
                    dialog.dismiss();

                    this.mDashboardCreateActivity.clearSubjectAndContent();
                },
                (MaterialDialog dialog, DialogAction which) -> dialog.dismiss()
            );
        }else{
            this.mDashboardCreateActivity.close();
        }
    }

    @Override
    public void doCopy(String tag, String content) {
        ClipData clipData = ClipData.newPlainText(tag, content);

        ClipboardManager clipboardManager = this.mDashboardCreateActivity.getClipboardManager();
        clipboardManager.setPrimaryClip(clipData);

        this.mDashboardCreateActivity.showShortlyToast(R.string.toast_dashboard_create_copy_success);
    }

}
