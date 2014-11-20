package im.after.app.ui;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;

import im.after.app.AppContext;
import im.after.app.R;
import im.after.app.entity.bean.UserBean;

public class ComposeActivity extends BaseActivity {

    private static final String TAG = "ComposeActivity";

    public static final int TYPE_ARTICLE = 1;
    public static final int TYPE_MEMO    = 2;
    public static final int TYPE_TALK    = 3;

    private ImageView imageViewComposeUserAvatar;
    private TextView textViewComposeUsername;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_compose);

        this.imageViewComposeUserAvatar = (ImageView) this.findViewById(R.id.imageViewComposeUserAvatar);
        this.textViewComposeUsername    = (TextView) this.findViewById(R.id.textViewComposeUsername);

        this.setUserInfo();
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

}
