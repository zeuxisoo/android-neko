package im.after.app.ui.module.memo;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.MemoItemBean;

public class MemoItemFragmentAdapter extends RecyclerView.Adapter<MemoItemFragmentAdapter.ViewHolder> {

    private ArrayList<MemoItemBean> memoItemBeans;

    public MemoItemFragmentAdapter() {
        this.memoItemBeans = new ArrayList<MemoItemBean>();
    }

    @Override
    public MemoItemFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_talk_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        MemoItemBean memoItemBean = this.memoItemBeans.get(i);

        viewHolder.username.setText(memoItemBean.getUsername());
        viewHolder.content.setText(memoItemBean.getContent());
        viewHolder.createAt.setText(memoItemBean.getCreateAtHumanTime());
    }

    @Override
    public int getItemCount() {
        return this.memoItemBeans.size();
    }

    public MemoItemBean getMemoItem(int position) {
        return this.memoItemBeans.get(position);
    }

    public void setMemoItemBeans(ArrayList<MemoItemBean> memoItemBeans) {
        this.memoItemBeans = memoItemBeans;
        this.notifyDataSetChanged();
    }

    public void addMemoItemBeans(ArrayList<MemoItemBean> memoItemBeans) {
        this.memoItemBeans.addAll(memoItemBeans);
        this.notifyDataSetChanged();
    }

    public void clearMemoItemBeans() {
        this.memoItemBeans.clear();
        this.notifyDataSetChanged();
    }

    public void prependMemoItemBean(MemoItemBean memoItemBean) {
        this.memoItemBeans.add(0, memoItemBean);
        this.notifyDataSetChanged();
    }

    public void removeMemoItemBean(int position) {
        this.memoItemBeans.remove(position);
        this.notifyDataSetChanged();
    }

    public void setMemoItemBean(int position, MemoItemBean memoItemBean) {
        this.memoItemBeans.set(position, memoItemBean);
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
