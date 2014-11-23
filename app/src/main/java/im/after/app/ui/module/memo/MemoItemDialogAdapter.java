package im.after.app.ui.module.talk;

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
    private ArrayList<MemoDialogItemBean> talkDialogItemBeans;
    private int resourceId;

    public MemoItemDialogAdapter(Context context, int resourceId, ArrayList<MemoDialogItemBean> talkDialogItemBeans) {
        super(context, resourceId, talkDialogItemBeans);

        this.context             = context;
        this.resourceId          = resourceId;
        this.talkDialogItemBeans = talkDialogItemBeans;
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

        MemoDialogItemBean talkDialogItemBean = (MemoDialogItemBean) this.talkDialogItemBeans.get(position);

        viewTag.icon.setImageResource(talkDialogItemBean.getIcon());
        viewTag.name.setText(String.valueOf(talkDialogItemBean.getName()));

        return convertView;
    }

    private static class ViewTag {
        public ImageView icon;
        public TextView name;
    }
}
