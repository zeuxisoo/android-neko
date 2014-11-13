package im.after.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.apache.http.Header;
import org.json.JSONException;
import org.json.JSONObject;

import im.after.app.R;
import im.after.app.helper.APIHelper;
import im.after.app.helper.HttpHelper;
import im.after.app.helper.UIHelper;


public class LoginActivity extends BaseActivity {

    private static final String TAG  = "LoginActivity";

    EditText editTextUsername;
    EditText editTextPassword;
    ActionProcessButton buttonSignIn;
    UIHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);

        this.uiHelper = new UIHelper(this);

        this.editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        this.editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        this.buttonSignIn = (ActionProcessButton) this.findViewById(R.id.buttonSignIn);
        this.buttonSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        this.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setAllControlsEnabled(false);
                doSignIn();
            }
        });
    }

    private void setAllControlsEnabled(boolean status) {
        editTextUsername.setEnabled(status);
        editTextPassword.setEnabled(status);
        buttonSignIn.setEnabled(status);
    }

    private void doSignIn() {
        // Start progress animation (Start at 0 will not show animation)
        buttonSignIn.setProgress(1);

        // Set post parameters
        RequestParams params = new RequestParams();
        params.put("account", editTextUsername.getText());
        params.put("password", editTextPassword.getText());
        params.put("permanent", true);

        // Make sign in request
        HttpHelper httpHelper = new HttpHelper(getApplicationContext());
        httpHelper.post(APIHelper.signInUrl(), params, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                try {
                    String username = response.getString("username");

                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(JSONException e) {
                    uiHelper.alertError("Oops", "Unknown sign in error (doSignIn::onSuccess)");
                }finally{
                    buttonSignIn.setProgress(0);
                    setAllControlsEnabled(true);
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                try {
                    String message = errorResponse.getJSONObject("error").getString("message");

                    uiHelper.alertError("Oops", message);
                }catch(JSONException e) {
                    uiHelper.alertError("Oops", "Unknown sign in error (doSignIn::onFailure)");
                }finally{
                    buttonSignIn.setProgress(0);
                    setAllControlsEnabled(true);
                }
            }
        });
    }

}
