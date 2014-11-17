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
import im.after.app.entity.TalkEntity;
import im.after.app.ui.adapter.FragmentTalkAdapter;

public class TalkFragment extends Fragment {

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;

    private FragmentTalkAdapter fragmentTalkAdapter;

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
        ArrayList<TalkEntity> talkEntities = new ArrayList<>();
        talkEntities.add(0, new TalkEntity("Apple"));
        talkEntities.add(1, new TalkEntity("Banana"));

        FragmentTalkAdapter fragmentTalkAdapter = new FragmentTalkAdapter(talkEntities);

        this.recyclerViewFragmentTalk.setAdapter(fragmentTalkAdapter);

        return viewFragmentTalk;
    }
}
