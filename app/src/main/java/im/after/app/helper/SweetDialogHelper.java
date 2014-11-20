package im.after.app.helper;

import android.content.Context;
import android.os.Handler;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class SweetDialogHelper {

    private Context context;

    public interface ConfirmCallback {
        void onFinish();
    }

    public SweetDialogHelper(Context context) {
        this.context = context;
    }

    public void alertSuccess(String title, String content) {
        new SweetAlertDialog(this.context, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setContentText(content).show();
    }

    public void alertError(String title, String content) {
        new SweetAlertDialog(this.context, SweetAlertDialog.ERROR_TYPE).setTitleText(title).setContentText(content).show();
    }

    public void confirm(String title, String content, String confirmText, ConfirmCallback callbackYes) {
        new SweetAlertDialog(this.context, SweetAlertDialog.WARNING_TYPE)
            .setTitleText(title)
            .setContentText(content)
            .setConfirmText(confirmText)
            .setConfirmClickListener((SweetAlertDialog sDialog) -> {
                sDialog.dismiss();

                // Fix windowLeak exception when call activity.finish().
                // Because the super dismiss overwritten and added 120s animation.
                // So activity.finish will run first than dialog dismiss.
                // Base on this, let delay the callback 200s, make sure dialog.dismiss called first
                new Handler().postDelayed(callbackYes::onFinish, 200);
            })
            .showCancelButton(true)
            .show();
    }

}
