package im.after.app.ui.module.talk;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.TalkItemBean;

public class TalkItemFragmentAdapter extends RecyclerView.Adapter<TalkItemFragmentAdapter.ViewHolder> {

    private ArrayList<TalkItemBean> talkItemBeans;

    public TalkItemFragmentAdapter() {
        this.talkItemBeans = new ArrayList<TalkItemBean>();
    }

    @Override
    public TalkItemFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_talk_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TalkItemBean talkItemBean = this.talkItemBeans.get(i);

        viewHolder.username.setText(talkItemBean.getUsername());
        viewHolder.content.setText(talkItemBean.getContent());
        viewHolder.createAt.setText(talkItemBean.getCreateAtHumanTime());
    }

    @Override
    public int getItemCount() {
        return this.talkItemBeans.size();
    }

    public TalkItemBean getTalkItem(int position) {
        return this.talkItemBeans.get(position);
    }

    public void setTalkItemBeans(ArrayList<TalkItemBean> talkItemBeans) {
        this.talkItemBeans = talkItemBeans;
        this.notifyDataSetChanged();
    }

    public void addTalkItemBeans(ArrayList<TalkItemBean> talkItemBeans) {
        this.talkItemBeans.addAll(talkItemBeans);
        this.notifyDataSetChanged();
    }

    public void clearTalkItemBeans() {
        this.talkItemBeans.clear();
        this.notifyDataSetChanged();
    }

    public void prependTalkItemBean(TalkItemBean talkItemBean) {
        this.talkItemBeans.add(0, talkItemBean);
        this.notifyDataSetChanged();
    }

    public void removeTalkItemBean(int position) {
        this.talkItemBeans.remove(position);
        this.notifyDataSetChanged();
    }

    public void setTalkItemBean(int position, TalkItemBean talkItemBean) {
        this.talkItemBeans.set(position, talkItemBean);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView content;
        public TextView createAt;

        public ViewHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.textViewFragmentTalkItemUsername);
            this.content  = (TextView) view.findViewById(R.id.textViewFragmentTalkItemContent);
            this.createAt = (TextView) view.findViewById(R.id.textViewFragmentTalkItemCreateAt);
        }
    }
}