package im.after.app.ui.module.memo;

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
import im.after.app.entity.bean.MemoDialogItemBean;

public class MemoItemDialogAdapter extends ArrayAdapter<MemoDialogItemBean> {

    private Context context;
    private LayoutInflater layoutInflater;
    private ArrayList<MemoDialogItemBean> memoDialogItemBeans;
    private int resourceId;

    public MemoItemDialogAdapter(Context context, int resourceId, ArrayList<MemoDialogItemBean> memoDialogItemBeans) {
        super(context, resourceId, memoDialogItemBeans);

        this.context             = context;
        this.resourceId          = resourceId;
        this.memoDialogItemBeans = memoDialogItemBeans;
        this.layoutInflater      = ((Activity) this.context).getLayoutInflater();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewTag viewTag;

        if (convertView == null) {
            convertView = this.layoutInflater.inflate(this.resourceId, parent, false);

            viewTag = new ViewTag();
            viewTag.icon = (ImageView) convertView.findViewById(R.id.imageViewDialogMemoIcon);
            viewTag.name = (TextView) convertView.findViewById(R.id.textViewDialogMemoItemName);

            convertView.setTag(viewTag);
        }else{
            viewTag = (ViewTag) convertView.getTag();
        }

        MemoDialogItemBean memoDialogItemBean = (MemoDialogItemBean) this.memoDialogItemBeans.get(position);

        viewTag.icon.setImageResource(memoDialogItemBean.getIcon());
        viewTag.name.setText(String.valueOf(memoDialogItemBean.getName()));

        return convertView;
    }

    private static class ViewTag {
        public ImageView icon;
        public TextView name;
    }
}
