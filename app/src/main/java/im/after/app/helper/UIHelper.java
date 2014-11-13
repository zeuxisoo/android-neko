package im.after.app.helper;

import android.content.Context;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class UIHelper {

    private Context context;

    public UIHelper(Context context) {
        this.context = context;
    }

    public void alertSuccess(String title, String content) {
        new SweetAlertDialog(this.context, SweetAlertDialog.SUCCESS_TYPE).setTitleText(title).setContentText(content).show();
    }

    public void alertError(String title, String content) {
        new SweetAlertDialog(this.context, SweetAlertDialog.ERROR_TYPE).setTitleText(title).setContentText(content).show();
    }

}
