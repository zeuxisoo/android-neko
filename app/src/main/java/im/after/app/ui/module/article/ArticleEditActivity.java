package im.after.app.ui.module.article;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.R;
import im.after.app.api.ArticleAPI;
import im.after.app.entity.bean.ArticleItemBean;
import im.after.app.ui.base.BaseComposeActivity;

public class ArticleEditActivity extends BaseComposeActivity {

    private static final String TAG = ArticleEditActivity.class.getSimpleName();

    private EditText editTextTitle;

    private ArticleItemBean articleItemBean;
    private int articleItemBeanPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_article_edit);

        this.imageViewUserAvatar = (ImageView) this.findViewById(R.id.imageViewEditUserAvatar);
        this.textViewUsername    = (TextView) this.findViewById(R.id.textViewEditUsername);
        this.imageButtonArticle  = (ImageButton) this.findViewById(R.id.imageButtonEditArticle);
        this.imageButtonMemo     = (ImageButton) this.findViewById(R.id.imageButtonEditMemo);
        this.imageButtonTalk     = (ImageButton) this.findViewById(R.id.imageButtonEditTalk);
        this.editTextContent     = (EditText) this.findViewById(R.id.editTextEditContent);
        this.imageButtonCancel   = (ImageButton) this.findViewById(R.id.imageButtonEditCancel);
        this.imageButtonClear    = (ImageButton) this.findViewById(R.id.imageButtonEditClear);
        this.imageButtonCopy     = (ImageButton) this.findViewById(R.id.imageButtonEditCopy);
        this.imageButtonSend     = (ImageButton) this.findViewById(R.id.imageButtonEditSend);

        this.editTextTitle = (EditText) this.findViewById(R.id.editTextComposeTitle);

        this.articleItemBean         = (ArticleItemBean) this.getIntent().getSerializableExtra("articleItemBean");
        this.articleItemBeanPosition = this.getIntent().getExtras().getInt("articleItemBeanPosition");

        this.editTextTitle.setText(this.articleItemBean.getTitle());
        this.editTextContent.setText(this.articleItemBean.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setSendEvent() {
        this.imageButtonSend.setOnClickListener((View v) -> {
            if (this.editTextContent.length() > 0) {
                ArticleAPI articleAPI = new ArticleAPI(this);

                articleAPI.update(
                    this.articleItemBean.getId(),
                    new HashMap<String, String>() {{
                        put("title", editTextTitle.getText().toString());
                        put("content", editTextContent.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            ArticleItemBean articleItemBean = objectMapper.readValue(response.toString(), ArticleItemBean.class);

                            Intent intent = new Intent(this, ArticleFragment.class);
                            intent.putExtra("articleItemBean", articleItemBean);
                            intent.putExtra("articleItemBeanPosition", this.articleItemBeanPosition);

                            this.setResult(RESULT_OK, intent);
                            this.finish();
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_edit_activity_edit_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_edit_activity_edit_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
