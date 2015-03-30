package im.after.app.ui.observer;

import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import im.after.app.ui.base.BaseFragment;

public class FragmentEmptyViewObserver extends RecyclerView.AdapterDataObserver {

    private static final String TAG = FragmentEmptyViewObserver.class.getSimpleName();

    private Fragment fragment;
    private RecyclerView.Adapter adapter;

    public FragmentEmptyViewObserver(Fragment fragment, RecyclerView.Adapter adapter) {
        this.fragment = fragment;
        this.adapter  = adapter;
    }

    @Override
    public void onChanged() {
        super.onChanged();

        this.checkEmptyView();
    }

    @Override
    public void onItemRangeInserted(int positionStart, int itemCount) {
        super.onItemRangeInserted(positionStart, itemCount);

        this.checkEmptyView();
    }

    @Override
    public void onItemRangeRemoved(int positionStart, int itemCount) {
        super.onItemRangeRemoved(positionStart, itemCount);

        this.checkEmptyView();
    }

    private void checkEmptyView() {
        if (this.adapter.getItemCount() <= 0) {
            ((BaseFragment) this.fragment).setEmptyView(View.VISIBLE);
        }else{
            ((BaseFragment) this.fragment).setEmptyView(View.GONE);
        }
    }

}
