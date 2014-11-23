package im.after.app.ui.module.article;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.bean.ArticleDialogItemBean;

public class ArticleItemDialogAdapter extends ArrayAdapter<ArticleDialogItemBean> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<ArticleDialogItemBean> articleDialogItemBeans;
    private int resourceId;

    public ArticleItemDialogAdapter(Context context, int resourceId, ArrayList<ArticleDialogItemBean> articleDialogItemBeans) {
        super(context, resourceId, articleDialogItemBeans);

        this.context                = context;
        this.resourceId             = resourceId;
        this.articleDialogItemBeans = articleDialogItemBeans;
        this.layoutInflater         = ((Activity) this.context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewTag viewTag;

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(this.resourceId, parent, false);

            viewTag = new ViewTag();
            viewTag.icon = (ImageView) convertView.findViewById(R.id.imageViewDialogArticleIcon);
            viewTag.name = (TextView) convertView.findViewById(R.id.textViewDialogArticleItemName);

            convertView.setTag(viewTag);
        }else{
            viewTag = (ViewTag) convertView.getTag();
        }

        ArticleDialogItemBean articleDialogItemBean = (ArticleDialogItemBean) this.articleDialogItemBeans.get(position);

        viewTag.icon.setImageResource(articleDialogItemBean.getIcon());
        viewTag.name.setText(String.valueOf(articleDialogItemBean.getName()));

        return convertView;
    }

    private static class ViewTag {
        public ImageView icon;
        public TextView name;
    }
}
