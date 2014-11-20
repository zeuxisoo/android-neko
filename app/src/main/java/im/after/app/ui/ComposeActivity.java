package im.after.app.ui;

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

public class ComposeActivity extends BaseActivity {

    private static final String TAG = ComposeActivity.class.getSimpleName();

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

    private SweetDialogHelper sweetDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_compose);

        this.imageViewComposeUserAvatar = (ImageView) this.findViewById(R.id.imageViewComposeUserAvatar);
        this.textViewComposeUsername    = (TextView) this.findViewById(R.id.textViewComposeUsername);
        this.imageButtonComposeArticle  = (ImageButton) this.findViewById(R.id.imageButtonComposeArticle);
        this.imageButtonComposeMemo     = (ImageButton) this.findViewById(R.id.imageButtonComposeMemo);
        this.imageButtonComposeTalk     = (ImageButton) this.findViewById(R.id.imageButtonComposeTalk);
        this.editTextComposeText        = (EditText) this.findViewById(R.id.editTextComposeText);
        this.imageButtonComposeCancel   = (ImageButton) this.findViewById(R.id.imageButtonComposeCancel);
        this.imageButtonComposeClear    = (ImageButton) this.findViewById(R.id.imageButtonComposeClear);

        this.sweetDialogHelper = new SweetDialogHelper(this);

        this.setUserInfo();
        this.underlineSelectedType();
        this.setCancelEvent();
        this.setClearEvent();
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
                this.sweetDialogHelper.confirm("Cancel confirm", "Are you sure want to cancel it ?", "Yes", this::finish);
            }else{
                this.finish();
            }
        });
    }

    private void setClearEvent() {
        this.imageButtonComposeClear.setOnClickListener((View v) -> {
            if (this.editTextComposeText.length() > 0) {
                this.sweetDialogHelper.confirm("Clear confirm", "Are you sure want to clear the text ?", "Yes", () -> editTextComposeText.setText(""));
            }else{
                editTextComposeText.setText("");
            }
        });
    }

}
