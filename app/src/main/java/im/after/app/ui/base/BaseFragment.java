package im.after.app.ui.base;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import im.after.app.AppContext;

public class BaseFragment extends Fragment {

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

}
