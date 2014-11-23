package im.after.app.ui.module.talk;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import im.after.app.AppContext;
import im.after.app.R;
import im.after.app.entity.bean.UserBean;
import im.after.app.helper.SweetDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.ui.base.BaseActivity;

abstract public class TalkBaseActivity extends BaseActivity {

    protected static final String TAG = TalkBaseActivity.class.getSimpleName();

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_MEMO    = 2;
    public static final int TYPE_TALK    = 3;
    public static final int TYPE_INVALID = -1;

    protected ImageView imageViewUserAvatar;
    protected TextView textViewUsername;
    protected ImageButton imageButtonArticle;
    protected ImageButton imageButtonMemo;
    protected ImageButton imageButtonTalk;
    protected EditText editTextContent;
    protected ImageButton imageButtonCancel;
    protected ImageButton imageButtonClear;
    protected ImageButton imageButtonCopy;
    protected ImageButton imageButtonSend;

    protected SweetDialogHelper sweetDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.sweetDialogHelper = new SweetDialogHelper(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        this.setUserInfo();
        this.underlineSelectedType();
        this.setCancelEvent();
        this.setClearEvent();
        this.setCopyEvent();
        this.setSendEvent();
    }

    protected void setUserInfo() {
        UserBean userBean = ((AppContext) this.getApplicationContext()).getUserBean();

        if (userBean != null) {
            Picasso.with(this).load(userBean.getAvatar()).fit().transform(
                new RoundedTransformationBuilder().borderColor(R.color.compose_user_avatar_border).borderWidthDp(1).cornerRadiusDp(30).oval(false).build()
            ).into(this.imageViewUserAvatar);

            this.textViewUsername.setText(userBean.getUsername());
            this.imageViewUserAvatar.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    protected void underlineSelectedType() {
        switch(this.getIntent().getIntExtra("type", TYPE_INVALID)) {
            case TYPE_ARTICLE:
                this.setUnderline(this.imageButtonArticle);
                break;
            case TYPE_MEMO:
                this.setUnderline(this.imageButtonMemo);
                break;
            case TYPE_TALK:
                this.setUnderline(this.imageButtonTalk);
                break;
        }
    }

    protected void setUnderline(ImageButton button) {
        button.setBackgroundResource(R.drawable.compose_default_image_button_underline);
        button.invalidate();
    }

    protected void setCancelEvent() {
        this.imageButtonCancel.setOnClickListener((View v) -> {
            if (this.editTextContent.length() > 0) {
                this.sweetDialogHelper.confirm(
                    locale(R.string.talk_base_activity_cancel_confirm_title),
                    locale(R.string.talk_base_activity_cancel_confirm_text),
                    locale(R.string.talk_base_activity_cancel_confirm_yes),
                    this::finish
                );
            }else{
                this.finish();
            }
        });
    }

    protected void setClearEvent() {
        this.imageButtonClear.setOnClickListener((View v) -> {
            if (this.editTextContent.length() > 0) {
                this.sweetDialogHelper.confirm(
                    locale(R.string.talk_base_activity_clear_confirm_title),
                    locale(R.string.talk_base_activity_clear_confirm_text),
                    locale(R.string.talk_base_activity_clear_confirm_yes),
                    () -> editTextContent.setText("")
                );
            }else{
                editTextContent.setText("");
            }
        });
    }

    protected void setCopyEvent() {
        this.imageButtonCopy.setOnClickListener((View v) -> {
            ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(this.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(TAG, this.editTextContent.getText());

            clipboardManager.setPrimaryClip(clipData);

            ToastHelper.show(this, locale(R.string.talk_fragment_copy_success));
        });
    }

    abstract protected void setSendEvent();

}
