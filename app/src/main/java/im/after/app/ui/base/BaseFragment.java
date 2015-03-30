package im.after.app.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.analytics.GoogleAnalytics;

import im.after.app.AppContext;

public class BaseFragment extends Fragment {

    protected TextView textViewEmptyText;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ((AppContext) this.getActivity().getApplication()).getTracker(AppContext.AnalyticsTrackerName.APP_TRACKER);
    }

    @Override
    public void onStart() {
        super.onStart();

        GoogleAnalytics.getInstance(this.getActivity()).reportActivityStart(this.getActivity());
    }

    @Override
    public void onStop() {
        super.onStop();

        GoogleAnalytics.getInstance(this.getActivity()).reportActivityStop(this.getActivity());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        ((AppContext) this.getActivity().getApplicationContext()).setLocale();

        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);

        ((AppContext) this.getActivity().getApplicationContext()).setLocale();
    }

    protected String locale(int stringId) {
        return this.getResources().getString(stringId);
    }

    public void setEmptyView(int state) {
        this.textViewEmptyText.setVisibility(state);
    }

}
