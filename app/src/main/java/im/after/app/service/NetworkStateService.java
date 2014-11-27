package im.after.app.service;

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;

import im.after.app.R;
import im.after.app.helper.NetworkHelper;
import im.after.app.helper.ToastHelper;

public class NetworkStateService extends Service {

    private static final String TAG = NetworkStateService.class.getSimpleName();

    private NetworkStateServiceReceiver networkStateServiceReceiver;

    @Override
    public void onCreate() {
        super.onCreate();

        this.networkStateServiceReceiver = new NetworkStateServiceReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.setPriority(1000);

        this.registerReceiver(this.networkStateServiceReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(this.networkStateServiceReceiver);
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    class NetworkStateServiceReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();

            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                if (!NetworkHelper.isConnected(context)) {
                    ToastHelper.show(context, R.string.service_network_state_not_connected);
                }
            }
        }

    }

}
