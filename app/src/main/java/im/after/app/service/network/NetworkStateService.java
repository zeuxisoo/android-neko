package im.after.app.service.network;

import android.app.Service;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.os.IBinder;
import android.support.annotation.Nullable;

public class NetworkStateService extends Service {

    private NetworkStateServiceReceiver mNetworkStateServiceReceiver;

    // Register receiver and unregister receiver
    @Override
    public void onCreate() {
        super.onCreate();

        this.mNetworkStateServiceReceiver = new NetworkStateServiceReceiver();

        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        intentFilter.setPriority(1000);

        this.registerReceiver(this.mNetworkStateServiceReceiver, intentFilter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        this.unregisterReceiver(this.mNetworkStateServiceReceiver);
    }

    // Implementation for Service
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

}
