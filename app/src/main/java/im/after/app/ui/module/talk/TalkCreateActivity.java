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

public class TalkCreateActivity extends BaseComposeActivity {

    private static final String TAG = TalkCreateActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_talk_create);

        this.imageViewUserAvatar = (ImageView) this.findViewById(R.id.imageViewComposeUserAvatar);
        this.textViewUsername    = (TextView) this.findViewById(R.id.textViewComposeUsername);
        this.imageButtonArticle  = (ImageButton) this.findViewById(R.id.imageButtonComposeArticle);
        this.imageButtonMemo     = (ImageButton) this.findViewById(R.id.imageButtonComposeMemo);
        this.imageButtonTalk     = (ImageButton) this.findViewById(R.id.imageButtonComposeTalk);
        this.editTextContent     = (EditText) this.findViewById(R.id.editTextComposeText);
        this.imageButtonCancel   = (ImageButton) this.findViewById(R.id.imageButtonComposeCancel);
        this.imageButtonClear    = (ImageButton) this.findViewById(R.id.imageButtonComposeClear);
        this.imageButtonCopy     = (ImageButton) this.findViewById(R.id.imageButtonComposeCopy);
        this.imageButtonSend     = (ImageButton) this.findViewById(R.id.imageButtonComposeSend);
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

                talkAPI.create(
                    new HashMap<String, String>() {{
                        put("content", editTextContent.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            TalkItemBean talkItemBean = objectMapper.readValue(response.toString(), TalkItemBean.class);

                            Intent intent = new Intent(this, TalkFragment.class);
                            intent.putExtra("talkItemBean", talkItemBean);

                            this.setResult(RESULT_OK, intent);
                            this.close();
                        }catch(Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_create_activity_create_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        }catch(Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.talk_create_activity_create_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
