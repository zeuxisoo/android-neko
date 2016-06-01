package im.after.app.helper;

import android.content.Context;
import android.widget.Toast;

public class ToastHelper {

    public void showShortly(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showShortly(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    public void showLongly(Context context, String message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

    public void showLongly(Context context, int message) {
        Toast.makeText(context, message, Toast.LENGTH_LONG).show();
    }

}
