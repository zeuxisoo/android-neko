package im.after.app.helper;

import android.content.Context;

import com.afollestad.materialdialogs.MaterialDialog;

public class MaterialDialogHelper {

    public void confirm(Context context, String title, String content, String yesText, String noText, MaterialDialog.SingleButtonCallback yesCallback, MaterialDialog.SingleButtonCallback noCallback) {
        new MaterialDialog.Builder(context)
                .title(title)
                .content(content)
                .positiveText(yesText)
                .negativeText(noText)
                .onPositive(yesCallback)
                .onNegative(noCallback)
                .show();
    }

}
