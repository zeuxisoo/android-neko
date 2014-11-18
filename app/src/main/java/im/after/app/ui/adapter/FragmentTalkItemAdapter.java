package im.after.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.TalkBean;

public class FragmentTalkItemAdapter extends RecyclerView.Adapter<FragmentTalkItemAdapter.ViewHolder> {

    private ArrayList<TalkBean> talkBeans;

    public FragmentTalkItemAdapter(ArrayList<TalkBean> talkBean) {
        this.talkBeans = talkBean;
    }

    @Override
    public FragmentTalkItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_talk_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TalkBean talkBean = this.talkBeans.get(i);

        viewHolder.subject.setText(talkBean.getSubject());
    }

    @Override
    public int getItemCount() {
        return this.talkBeans.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject;

        public ViewHolder(View view) {
            super(view);

            this.subject = (TextView) view.findViewById(R.id.textViewFragmentTalkItemSubject);
        }

    }
}
