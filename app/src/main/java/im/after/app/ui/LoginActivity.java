package im.after.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.R;
import im.after.app.api.MainAPI;
import im.after.app.api.listener.JSONFailureListener;
import im.after.app.api.listener.JSONSuccessListener;


public class LoginActivity extends BaseActivity {

    private static final String TAG  = "LoginActivity";

    EditText editTextAccount;
    EditText editTextPassword;
    ActionProcessButton buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);

        this.editTextAccount = (EditText) this.findViewById(R.id.editTextAccount);
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
        editTextAccount.setEnabled(status);
        editTextPassword.setEnabled(status);
        buttonSignIn.setEnabled(status);
    }

    private void doSignIn() {
        // Start progress animation (Start at 0 will not show animation)
        buttonSignIn.setProgress(1);

        // Call sign in api
        MainAPI mainAPI = new MainAPI(this);

        mainAPI.signIn(new HashMap<String, String>() {{
            put("account", editTextAccount.getText().toString());
            put("password", editTextPassword.getText().toString());
            put("permanent", "1");
        }}, new JSONSuccessListener() {
            @Override
            public void onJSON(JSONObject response) {
                try {
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    finish();
                }catch(Exception e) {
                    uiHelper.alertError("Oops", "Unknown sign in error (doSignIn::JSONSuccessListener)");
                }finally{
                    buttonSignIn.setProgress(0);
                    setAllControlsEnabled(true);
                }
            }
        }, new JSONFailureListener() {
            @Override
            public void onJSON(JSONObject response) {
                try {
                    String message = response.getJSONObject("error").getString("message");

                    uiHelper.alertError("Oops", message);
                }catch(Exception e) {
                    uiHelper.alertError("Oops", "Unknown sign in error (doSignIn::JSONFailureListener)");
                }finally{
                    buttonSignIn.setProgress(0);
                    setAllControlsEnabled(true);
                }
            }
        });

    }

}
