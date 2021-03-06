package im.after.app.view.dashboard;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import javax.inject.Inject;

import butterknife.BindView;
import butterknife.ButterKnife;
import im.after.app.R;
import im.after.app.data.api.dashboard.bean.DashboardItemBean;

public class DashboardAdapter extends RecyclerView.Adapter<DashboardAdapter.ViewHolder> {

    private ArrayList<DashboardItemBean> dashboardBeanArrayList;

    @Inject
    public DashboardAdapter() {
        this.dashboardBeanArrayList = new ArrayList<>();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.activity_dashboard_item, parent, false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        DashboardItemBean dashboardBean = this.dashboardBeanArrayList.get(position);

        // Handle default value
        String subject = dashboardBean.getSubject() == null ? "N/A" : dashboardBean.getSubject();

        // Set display value
        holder.mTextViewDashboardSubject.setText(subject);
        holder.mTextViewDashboardContent.setText(dashboardBean.getContent());
    }

    @Override
    public int getItemCount() {
        return this.dashboardBeanArrayList.size();
    }

    // Expose for lazy render
    public void bind(ArrayList<DashboardItemBean> dashboardBeanArrayList) {
        for(int i=0; i<dashboardBeanArrayList.size(); i++) {
            this.dashboardBeanArrayList.add(dashboardBeanArrayList.get(i));
            this.notifyItemInserted(i);
        }
    }

    public void clear() {
        this.notifyItemRangeRemoved(0, this.dashboardBeanArrayList.size());
        this.dashboardBeanArrayList.clear();
    }

    public void append(ArrayList<DashboardItemBean> dashboardBeanArrayList) {
        int currentListSize = this.dashboardBeanArrayList.size();

        for(int i=0; i<dashboardBeanArrayList.size(); i++) {
            this.dashboardBeanArrayList.add(dashboardBeanArrayList.get(i));
            this.notifyItemInserted(currentListSize + 1);
        }
    }

    public void prepend(DashboardItemBean dashboardItemBean) {
        this.dashboardBeanArrayList.add(0, dashboardItemBean);
        this.notifyItemInserted(0);
    }

    // View holder
    public static class ViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.textViewDashboardSubject)
        TextView mTextViewDashboardSubject;

        @BindView(R.id.textViewDashboardContent)
        TextView mTextViewDashboardContent;

        public ViewHolder(View itemView) {
            super(itemView);

            ButterKnife.bind(this, itemView);
        }
    }

}
