package im.after.app.ui.module.memo;

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
import im.after.app.api.MemoAPI;
import im.after.app.entity.bean.MemoItemBean;
import im.after.app.ui.base.BaseComposeActivity;

public class MemoEditActivity extends BaseComposeActivity {

    private static final String TAG = MemoEditActivity.class.getSimpleName();

    private MemoItemBean memoItemBean;
    private int memoItemBeanPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_memo_edit);

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

        this.memoItemBean         = (MemoItemBean) this.getIntent().getSerializableExtra("memoItemBean");
        this.memoItemBeanPosition = this.getIntent().getExtras().getInt("memoItemBeanPosition");

        this.editTextContent.setText(this.memoItemBean.getContent());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void setSendEvent() {
        this.imageButtonSend.setOnClickListener((View v) -> {
            if (this.editTextContent.length() > 0) {
                MemoAPI memoAPI = new MemoAPI(this);

                memoAPI.update(
                    this.memoItemBean.getId(),
                    new HashMap<String, String>() {{
                        put("content", editTextContent.getText().toString());
                    }},
                    (JSONObject response) -> {
                        try {
                            ObjectMapper objectMapper = new ObjectMapper();

                            MemoItemBean memoItemBean = objectMapper.readValue(response.toString(), MemoItemBean.class);

                            Intent intent = new Intent(this, MemoFragment.class);
                            intent.putExtra("memoItemBean", memoItemBean);
                            intent.putExtra("memoItemBeanPosition", this.memoItemBeanPosition);

                            this.setResult(RESULT_OK, intent);
                            this.finish();
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_edit_activity_edit_error), "setSendEvent::JSONSuccessListener"));
                        }
                    },
                    (JSONObject response) -> {
                        try {
                            JSONObject errorObject = response.getJSONObject("error");
                            String message = errorObject.getString("message");

                            this.sweetDialogHelper.alertError("Oops", message);
                        } catch (Exception e) {
                            this.sweetDialogHelper.alertError("Oops", String.format(locale(R.string.memo_edit_activity_edit_error), "setSendEvent::JSONFailureListener"));
                        }
                    }
                );
            }
        });
    }

}
