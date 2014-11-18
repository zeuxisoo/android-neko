package im.after.app.ui.adapter;

import android.support.v4.app.Fragment;

public class BaseFragment extends Fragment {

    protected String locale(int stringId) {
        return this.getResources().getString(stringId);
    }

}
