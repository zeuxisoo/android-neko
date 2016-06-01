package im.after.app.service.network;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;

import im.after.app.R;
import im.after.app.helper.NetworkHelper;
import im.after.app.helper.ToastHelper;

public class NetworkStateServiceReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            if (!NetworkHelper.isConnected(context)) {
                new ToastHelper().showShortly(context, R.string.toast_service_network_state_not_connected);
            }
        }
    }

}
