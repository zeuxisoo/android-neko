package im.after.app.presenter.dashboard;

import android.content.ClipData;
import android.content.ClipboardManager;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;

import java.io.IOException;

import javax.inject.Inject;

import im.after.app.R;
import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.contract.dashboard.DashboardCreateContract;
import im.after.app.data.api.ErrorBean;
import im.after.app.data.api.dashboard.bean.DashboardItemBean;
import im.after.app.data.api.dashboard.bean.UserBean;
import im.after.app.data.api.dashboard.bean.create.DashboardCreateBean;
import im.after.app.model.dashboard.DashboardCreateModel;
import im.after.app.view.dashboard.DashboardCreateActivity;
import retrofit2.adapter.rxjava.HttpException;

public class DashboardCreatePresenter extends BasePresenter implements DashboardCreateContract {

    private DashboardCreateActivity mDashboardCreateActivity;

    private DashboardCreateModel mDashboardCreateModel;

    private Gson mGson;

    @Inject
    public DashboardCreatePresenter(DashboardCreateModel dashboardCreateModel, Gson gson) {
        this.mDashboardCreateModel = dashboardCreateModel;
        this.mGson                 = gson;
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

    @Override
    public void doSend(String subject, String content) {
        if (content.isEmpty()) {
            this.mDashboardCreateActivity.showAlertDialog(
                R.string.dialog_alert_shared_title,
                R.string.dialog_alert_dashboard_create_empty_content,
                R.string.dialog_alert_shared_ok,
                (MaterialDialog dialog, DialogAction which) -> dialog.dismiss()
            );
        }else{
            this.mDashboardCreateModel.createDashboard("text", subject, content, this::handleDoSendSuccess, this::handleDoSendError);
        }
    }

    // Subscribe handler for onRefresh method
    private void handleDoSendSuccess(DashboardCreateBean dashboardCreateBean) {
        DashboardItemBean dashboardItemBean = dashboardCreateBean.getDashboardItem();
        UserBean userBean = dashboardItemBean.getUser();

        if (!userBean.getName().isEmpty() && !dashboardItemBean.getContent().isEmpty()) {
            this.mDashboardCreateActivity.backToDashboardActivity(dashboardItemBean);
            this.mDashboardCreateActivity.showShortlyToast(R.string.toast_dashboard_create_send_success);
        }else{
            this.mDashboardCreateActivity.showShortlyToast(R.string.toast_dashboard_create_send_response_fail);
        }

        this.mDashboardCreateActivity.close();
    }

    private void handleDoSendError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            try {
                ErrorBean errorBean = this.mGson.fromJson(httpException.response().errorBody().string(), ErrorBean.class);

                this.mDashboardCreateActivity.showShortlyToast(
                    String.format("%s - %s", errorBean.getStatusCode(), errorBean.getMessage())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throwable.printStackTrace();
        }
    }

}
