package im.after.app.ui.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.TalkEntity;

public class FragmentTalkItemAdapter extends RecyclerView.Adapter<FragmentTalkItemAdapter.ViewHolder> {

    private ArrayList<TalkEntity> talkEntities;

    public FragmentTalkItemAdapter(ArrayList<TalkEntity> talkEntities) {
        this.talkEntities = talkEntities;
    }

    @Override
    public FragmentTalkItemAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_talk_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        TalkEntity talkEntity = this.talkEntities.get(i);

        viewHolder.subject.setText(talkEntity.getSubject());
    }

    @Override
    public int getItemCount() {
        return this.talkEntities.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        public TextView subject;

        public ViewHolder(View view) {
            super(view);

            this.subject = (TextView) view.findViewById(R.id.textViewFragmentTalkItemSubject);
        }

    }
}
