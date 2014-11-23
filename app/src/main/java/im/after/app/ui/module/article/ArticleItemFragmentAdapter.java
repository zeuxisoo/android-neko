package im.after.app.ui.module.article;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.ArticleItemBean;

public class ArticleItemFragmentAdapter extends RecyclerView.Adapter<ArticleItemFragmentAdapter.ViewHolder> {

    private ArrayList<ArticleItemBean> articleItemBeans;

    public ArticleItemFragmentAdapter() {
        this.articleItemBeans = new ArrayList<ArticleItemBean>();
    }

    @Override
    public ArticleItemFragmentAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        View fragmentTalkItemView = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.fragment_article_item, viewGroup, false);

        return new ViewHolder(fragmentTalkItemView);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        ArticleItemBean articleItemBean = this.articleItemBeans.get(i);

        viewHolder.username.setText(articleItemBean.getUsername());
        viewHolder.title.setText(articleItemBean.getTitle());
        viewHolder.createAt.setText(articleItemBean.getCreateAtHumanTime());
    }

    @Override
    public int getItemCount() {
        return this.articleItemBeans.size();
    }

    public ArticleItemBean getArticleItem(int position) {
        return this.articleItemBeans.get(position);
    }

    public void setArticleItemBeans(ArrayList<ArticleItemBean> articleItemBeans) {
        this.articleItemBeans = articleItemBeans;
        this.notifyDataSetChanged();
    }

    public void addArticleItemBeans(ArrayList<ArticleItemBean> articleItemBeans) {
        this.articleItemBeans.addAll(articleItemBeans);
        this.notifyDataSetChanged();
    }

    public void clearArticleItemBeans() {
        this.articleItemBeans.clear();
        this.notifyDataSetChanged();
    }

    public void prependArticleItemBean(ArticleItemBean articleItemBean) {
        this.articleItemBeans.add(0, articleItemBean);
        this.notifyDataSetChanged();
    }

    public void removeArticleItemBean(int position) {
        this.articleItemBeans.remove(position);
        this.notifyDataSetChanged();
    }

    public void setArticleItemBean(int position, ArticleItemBean articleItemBean) {
        this.articleItemBeans.set(position, articleItemBean);
        this.notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView username;
        public TextView title;
        public TextView createAt;

        public ViewHolder(View view) {
            super(view);

            this.username = (TextView) view.findViewById(R.id.textViewFragmentTalkItemUsername);
            this.title    = (TextView) view.findViewById(R.id.textViewFragmentTalkItemTitle);
            this.createAt = (TextView) view.findViewById(R.id.textViewFragmentTalkItemCreateAt);
        }
    }
}
