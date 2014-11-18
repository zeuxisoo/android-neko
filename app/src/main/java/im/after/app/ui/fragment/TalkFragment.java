package im.after.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.TalkBean;
import im.after.app.ui.adapter.FragmentTalkItemAdapter;

public class TalkFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewFragmentTalk = inflater.inflate(R.layout.fragment_talk, container, false);

        //
        this.swipeRefreshLayoutFragmentTalk = (SwipeRefreshLayout) viewFragmentTalk.findViewById(R.id.swipeRefreshLayoutFragmentTalk);
        this.recyclerViewFragmentTalk       = (RecyclerView) viewFragmentTalk.findViewById(R.id.recyclerViewFragmentTalk);

        //
        this.recyclerViewFragmentTalk.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.recyclerViewFragmentTalk.setItemAnimator(new DefaultItemAnimator());

        //
        ArrayList<TalkBean> talkBeans = new ArrayList<>();
        talkBeans.add(0, new TalkBean("Apple"));
        talkBeans.add(1, new TalkBean("Banana"));

        this.recyclerViewFragmentTalk.setAdapter(new FragmentTalkItemAdapter(talkBeans));

        return viewFragmentTalk;
    }
}
