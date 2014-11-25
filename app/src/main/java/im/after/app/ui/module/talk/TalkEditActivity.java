package im.after.app.ui.module.talk;

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
import im.after.app.api.TalkAPI;
import im.after.app.entity.bean.TalkItemBean;
import im.after.app.ui.base.BaseComposeActivity;

public class TalkEditActivity extends BaseComposeActivity {

    private static final String TAG = TalkEditActivity.class.getSimpleName();

    private TalkItemBean talkItemBean;
    private int talkItemBeanPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_talk_edit);

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

        this.talkItemBean         = (TalkItemBean) this.getIntent().getSerializableExtra("talkItemBean");
        this.talkItemBeanPosition = this.getIntent().getExtras().getInt("talkItemBeanPosition");

        this.editTextContent.setText(this.talkItemBean.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setSendEvent() {
        this.imageButtonSend.setOnClickListener((View v) -> {
            if (this.editTextContent.length() > 0) {
                TalkAPI talkAPI = new TalkAPI(this);

                talkAPI.update(
                    this.talkItemBean.getId(),
                    new HashMap<String, String>() {{
                        put("content", editTextContent.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            TalkItemBean talkItemBean = objectMapper.readValue(response.toString(), TalkItemBean.class);

                            Intent intent = new Intent(this, TalkFragment.class);
                            intent.putExtra("talkItemBean", talkItemBean);
                            intent.putExtra("talkItemBeanPosition", this.talkItemBeanPosition);

                            this.setResult(RESULT_OK, intent);
                            this.close();
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_edit_activity_edit_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_edit_activity_edit_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
