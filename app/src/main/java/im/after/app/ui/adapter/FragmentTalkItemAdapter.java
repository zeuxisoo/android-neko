package im.after.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.TalkItemBean;

public class FragmentTalkItemAdapter extends RecyclerView.Adapter<FragmentTalkItemAdapter.ViewHolder> {

    private ArrayList<TalkItemBean> talkItemBeans;

    public FragmentTalkItemAdapter() {
        this.talkItemBeans = new ArrayList<TalkItemBean>();
    }

    @Override
    public FragmentTalkItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_talk_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TalkItemBean talkItemBean = this.talkItemBeans.get(i);

        viewHolder.username.setText(talkItemBean.getUsername());
        viewHolder.content.setText(talkItemBean.getContent());
    }

    @Override
    public int getItemCount() {
        return this.talkItemBeans.size();
    }

    public void setTalkItemBeans(ArrayList<TalkItemBean> talkItemBeans) {
        this.talkItemBeans = talkItemBeans;
        this.notifyDataSetChanged();
    }

    public void addTalkBeans(ArrayList<TalkItemBean> talkItemBeans) {
        this.talkItemBeans.addAll(talkItemBeans);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView content;

        public ViewHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.textViewFragmentTalkItemUsername);
            this.content  = (TextView) view.findViewById(R.id.textViewFragmentTalkItemContent);
        }
    }
}
