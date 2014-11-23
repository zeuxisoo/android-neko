package im.after.app.ui.module.talk;

import android.content.ClipData;
import android.content.ClipboardManager;
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
import im.after.app.helper.ToastHelper;
import im.after.app.ui.base.BaseActivity;

public class TalkCreateActivity extends BaseActivity {

    private static final String TAG = TalkCreateActivity.class.getSimpleName();

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_MEMO    = 2;
    public static final int TYPE_TALK    = 3;
    public static final int TYPE_INVALID = -1;

    private ImageView imageViewComposeUserAvatar;
    private TextView textViewComposeUsername;
    private ImageButton imageButtonComposeArticle;
    private ImageButton imageButtonComposeMemo;
    private ImageButton imageButtonComposeTalk;
    private EditText editTextComposeText;
    private ImageButton imageButtonComposeCancel;
    private ImageButton imageButtonComposeClear;
    private ImageButton imageButtonComposeCopy;
    private ImageButton imageButtonComposeSend;

    private SweetDialogHelper sweetDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_talk_compose);

        this.imageViewComposeUserAvatar = (ImageView) this.findViewById(R.id.imageViewComposeUserAvatar);
        this.textViewComposeUsername    = (TextView) this.findViewById(R.id.textViewComposeUsername);
        this.imageButtonComposeArticle  = (ImageButton) this.findViewById(R.id.imageButtonComposeArticle);
        this.imageButtonComposeMemo     = (ImageButton) this.findViewById(R.id.imageButtonComposeMemo);
        this.imageButtonComposeTalk     = (ImageButton) this.findViewById(R.id.imageButtonComposeTalk);
        this.editTextComposeText        = (EditText) this.findViewById(R.id.editTextComposeText);
        this.imageButtonComposeCancel   = (ImageButton) this.findViewById(R.id.imageButtonComposeCancel);
        this.imageButtonComposeClear    = (ImageButton) this.findViewById(R.id.imageButtonComposeClear);
        this.imageButtonComposeCopy     = (ImageButton) this.findViewById(R.id.imageButtonComposeCopy);
        this.imageButtonComposeSend     = (ImageButton) this.findViewById(R.id.imageButtonComposeSend);

        this.sweetDialogHelper = new SweetDialogHelper(this);

        this.setUserInfo();
        this.underlineSelectedType();
        this.setCancelEvent();
        this.setClearEvent();
        this.setCopyEvent();
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
            ).into(this.imageViewComposeUserAvatar);

            this.textViewComposeUsername.setText(userBean.getUsername());
            this.imageViewComposeUserAvatar.setBackgroundColor(Color.TRANSPARENT);
        }
    }

    private void underlineSelectedType() {
        switch(this.getIntent().getIntExtra("type", TYPE_INVALID)) {
            case TYPE_ARTICLE:
                this.setUnderline(this.imageButtonComposeArticle);
                break;
            case TYPE_MEMO:
                this.setUnderline(this.imageButtonComposeMemo);
                break;
            case TYPE_TALK:
                this.setUnderline(this.imageButtonComposeTalk);
                break;
        }
    }

    private void setUnderline(ImageButton button) {
        button.setBackgroundResource(R.drawable.compose_default_image_button_underline);
        button.invalidate();
    }

    private void setCancelEvent() {
        this.imageButtonComposeCancel.setOnClickListener((View v) -> {
            if (this.editTextComposeText.length() > 0) {
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
        this.imageButtonComposeClear.setOnClickListener((View v) -> {
            if (this.editTextComposeText.length() > 0) {
                this.sweetDialogHelper.confirm(
                    this.getString(R.string.compose_activity_clear_confirm_title),
                    this.getString(R.string.compose_activity_clear_confirm_text),
                    this.getString(R.string.compose_activity_clear_confirm_yes),
                    () -> editTextComposeText.setText("")
                );
            }else{
                editTextComposeText.setText("");
            }
        });
    }

    private void setCopyEvent() {
        this.imageButtonComposeCopy.setOnClickListener((View v) -> {
            ClipboardManager clipboardManager = (ClipboardManager) this.getSystemService(this.CLIPBOARD_SERVICE);
            ClipData clipData = ClipData.newPlainText(TAG, this.editTextComposeText.getText());

            clipboardManager.setPrimaryClip(clipData);

            ToastHelper.show(this, "Text Copied");
        });
    }

    private void setSendEvent() {
        this.imageButtonComposeSend.setOnClickListener((View v) -> {
            if (this.editTextComposeText.length() > 0) {
                TalkAPI talkAPI = new TalkAPI(this);

                talkAPI.create(
                    new HashMap<String, String>() {{
                        put("content", editTextComposeText.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            TalkItemBean talkItemBean = objectMapper.readValue(response.toString(), TalkItemBean.class);

                            Intent intent = new Intent(this, TalkFragment.class);
                            intent.putExtra("talkItemBean", talkItemBean);

                            this.setResult(RESULT_OK, intent);
                            this.finish();
                        }catch(Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(this.getString(R.string.compose_activity_create_talk_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        }catch(Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(this.getString(R.string.compose_activity_create_talk_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
