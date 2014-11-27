package im.after.app.ui.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.view.View;
import android.widget.TextView;

import im.after.app.R;
import im.after.app.helper.NetworkHelper;
import im.after.app.ui.MainActivity;

public class MainActivityReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();

        if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
            MainActivity mainActivity = (MainActivity) context;
            TextView textViewNetworkState = (TextView) mainActivity.findViewById(R.id.textViewMainActivityNetworkState);

            if (!NetworkHelper.isConnected(context)) {
                textViewNetworkState.setVisibility(View.VISIBLE);
                textViewNetworkState.setText(context.getString(R.string.service_network_state_not_connected));
            }else{
                textViewNetworkState.setVisibility(View.INVISIBLE);
            }
        }
    }

}
