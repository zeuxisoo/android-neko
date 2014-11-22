package im.after.app.ui.module.talk;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.AppContext;
import im.after.app.R;
import im.after.app.api.TalkAPI;
import im.after.app.entity.bean.TalkItemBean;
import im.after.app.entity.bean.UserBean;
import im.after.app.helper.SweetDialogHelper;
import im.after.app.ui.base.BaseActivity;

public class TalkEditActivity extends BaseActivity {

    private static final String TAG = TalkEditActivity.class.getSimpleName();

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_MEMO    = 2;
    public static final int TYPE_TALK    = 3;
    public static final int TYPE_INVALID = -1;

    private ImageView imageViewEditUserAvatar;
    private TextView textViewEditUsername;
    private ImageButton imageButtonEditArticle;
    private ImageButton imageButtonEditMemo;
    private ImageButton imageButtonEditTalk;
    private EditText editTextEditContent;
    private ImageButton imageButtonEditCancel;
    private ImageButton imageButtonEditClear;
    private ImageButton imageButtonEditSend;

    private SweetDialogHelper sweetDialogHelper;

    private TalkItemBean talkItemBean;
    private int talkItemBeanPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_talk_edit);

        this.imageViewEditUserAvatar = (ImageView) this.findViewById(R.id.imageViewEditUserAvatar);
        this.textViewEditUsername    = (TextView) this.findViewById(R.id.textViewEditUsername);
        this.imageButtonEditArticle  = (ImageButton) this.findViewById(R.id.imageButtonEditArticle);
        this.imageButtonEditMemo     = (ImageButton) this.findViewById(R.id.imageButtonEditMemo);
        this.imageButtonEditTalk     = (ImageButton) this.findViewById(R.id.imageButtonEditTalk);
        this.editTextEditContent     = (EditText) this.findViewById(R.id.editTextEditContent);
        this.imageButtonEditCancel   = (ImageButton) this.findViewById(R.id.imageButtonEditCancel);
        this.imageButtonEditClear    = (ImageButton) this.findViewById(R.id.imageButtonEditClear);
        this.imageButtonEditSend     = (ImageButton) this.findViewById(R.id.imageButtonEditSend);

        this.sweetDialogHelper = new SweetDialogHelper(this);

        this.talkItemBean         = (TalkItemBean) this.getIntent().getSerializableExtra("talkItemBean");
        this.talkItemBeanPosition = this.getIntent().getExtras().getInt("talkItemBeanPosition");

        this.setUserInfo();
        this.setContentText();
        this.underlineSelectedType();
        this.setCancelEvent();
        this.setClearEvent();
        this.setSendEvent();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void setUserInfo() {
        UserBean userBean = ((AppContext) this.getApplicationContext()).getUserBean();

        if (userBean != null) {
            Picasso.with(this).load(userBean.getAvatar()).fit().transform(
                new RoundedTransformationBuilder().borderColor(R.color.compose_user_avatar_border).borderWidthDp(1).cornerRadiusDp(30).oval(false).build()
            ).into(this.imageViewEditUserAvatar);

            this.textViewEditUsername.setText(userBean.getUsername());
            this.imageViewEditUserAvatar.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void setContentText() {
        this.editTextEditContent.setText(this.talkItemBean.getContent());
    }

    private void underlineSelectedType() {
        switch(this.getIntent().getIntExtra("type", TYPE_INVALID)) {
            case TYPE_ARTICLE:
                this.setUnderline(this.imageButtonEditArticle);
                break;
            case TYPE_MEMO:
                this.setUnderline(this.imageButtonEditMemo);
                break;
            case TYPE_TALK:
                this.setUnderline(this.imageButtonEditTalk);
                break;
        }
    }

    private void setUnderline(ImageButton button) {
        button.setBackgroundResource(R.drawable.compose_default_image_button_underline);
        button.invalidate();
    }

    private void setCancelEvent() {
        this.imageButtonEditCancel.setOnClickListener((View v) -> {
            if (this.editTextEditContent.length() > 0) {
                this.sweetDialogHelper.confirm(
                    this.getString(R.string.compose_activity_cancel_confirm_title),
                    this.getString(R.string.compose_activity_cancel_confirm_text),
                    this.getString(R.string.compose_activity_cancel_confirm_yes),
                    this::finish
                );
            }else{
                this.finish();
            }
        });
    }

    private void setClearEvent() {
        this.imageButtonEditClear.setOnClickListener((View v) -> {
            if (this.editTextEditContent.length() > 0) {
                this.sweetDialogHelper.confirm(
                    this.getString(R.string.compose_activity_clear_confirm_title),
                    this.getString(R.string.compose_activity_clear_confirm_text),
                    this.getString(R.string.compose_activity_clear_confirm_yes),
                    () -> editTextEditContent.setText("")
                );
            }else{
                editTextEditContent.setText("");
            }
        });
    }

    private void setSendEvent() {
        this.imageButtonEditSend.setOnClickListener((View v) -> {
            if (this.editTextEditContent.length() > 0) {
                TalkAPI talkAPI = new TalkAPI(this);

                talkAPI.update(
                    this.talkItemBean.getId(),
                    new HashMap<String, String>() {{
                        put("content", editTextEditContent.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            TalkItemBean talkItemBean = objectMapper.readValue(response.toString(), TalkItemBean.class);

                            Intent intent = new Intent(this, TalkFragment.class);
                            intent.putExtra("talkItemBean", talkItemBean);
                            intent.putExtra("talkItemBeanPosition", this.talkItemBeanPosition);

                            this.setResult(RESULT_OK, intent);
                            this.finish();
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(this.getString(R.string.compose_activity_create_talk_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(this.getString(R.string.compose_activity_create_talk_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
