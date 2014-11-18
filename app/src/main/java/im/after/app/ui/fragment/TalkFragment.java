package im.after.app.ui.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.api.TalkAPI;
import im.after.app.entity.bean.TalkBean;
import im.after.app.ui.adapter.FragmentTalkItemAdapter;

public class TalkFragment extends Fragment {

    private static final String TAG = "TalkFragment";

    private SwipeRefreshLayout swipeRefreshLayoutFragmentTalk;
    private RecyclerView recyclerViewFragmentTalk;

    private FragmentTalkItemAdapter fragmentTalkItemAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewFragmentTalk = inflater.inflate(R.layout.fragment_talk, container, false);

        this.swipeRefreshLayoutFragmentTalk = (SwipeRefreshLayout) viewFragmentTalk.findViewById(R.id.swipeRefreshLayoutFragmentTalk);
        this.recyclerViewFragmentTalk       = (RecyclerView) viewFragmentTalk.findViewById(R.id.recyclerViewFragmentTalk);

        return viewFragmentTalk;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //
        this.recyclerViewFragmentTalk.setHasFixedSize(false);
        this.recyclerViewFragmentTalk.setLayoutManager(new LinearLayoutManager(this.getActivity()));
        this.recyclerViewFragmentTalk.setItemAnimator(new DefaultItemAnimator());

        this.fragmentTalkItemAdapter = new FragmentTalkItemAdapter();
        this.recyclerViewFragmentTalk.setAdapter(this.fragmentTalkItemAdapter);

        //
        this.requestTalkPage(1);
    }

    private void requestTalkPage(int pageNo) {
        TalkAPI talkAPI = new TalkAPI(this.getActivity());
        Log.d(TAG, "> loadTalkPage start");
        talkAPI.page(
            pageNo,
            (JSONObject response) -> {
                try {
                    Log.d(TAG, "> loadTalkPage success");

                    String count    = response.getString("count");
                    String next     = response.getString("next");
                    String prev     = response.getString("prev");
                    JSONArray items = response.getJSONArray("items");

                    ObjectMapper objectMapper = new ObjectMapper();

                    ArrayList<TalkBean> talkBeans = objectMapper.readValue(
                        items.toString(),
                        objectMapper.getTypeFactory().constructCollectionType(ArrayList.class, TalkBean.class)
                    );

                    this.fragmentTalkItemAdapter.setTalkBeans(talkBeans);
                }catch(Exception e) {
                    Log.d(TAG, "Error in (loadTalkPage::JSONSuccessListener)", e);
                }
            },
            (JSONObject response) -> {
                Log.d(TAG, "> loadTalkPage fail");

                try {

                }catch(Exception e) {
                    Log.d(TAG, "Error in (loadTalkPage::JSONFailureListener)", e);
                }
            }
        );
    }

}
