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


public class LoginActivity extends BaseActivity {

    private static final String TAG  = "LoginActivity";

    EditText editTextAccount;
    EditText editTextPassword;
    ActionProcessButton buttonLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);

        this.editTextAccount = (EditText) this.findViewById(R.id.editTextAccount);
        this.editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        this.buttonLogin = (ActionProcessButton) this.findViewById(R.id.buttonLogin);
        this.buttonLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        this.buttonLogin.setOnClickListener((View v) -> {
            setAllControlsEnabled(false);
            doLogin();
        });
    }

    private void setAllControlsEnabled(boolean status) {
        editTextAccount.setEnabled(status);
        editTextPassword.setEnabled(status);
        buttonLogin.setEnabled(status);
    }

    private void doLogin() {
        // Start progress animation (Start at 0 will not show animation)
        buttonLogin.setProgress(1);

        // Call sign in api
        MainAPI mainAPI = new MainAPI(this);

        mainAPI.signIn(new HashMap<String, String>() {{
            put("account", editTextAccount.getText().toString());
            put("password", editTextPassword.getText().toString());
            put("permanent", "1");
        }}, (JSONObject response) -> {
            try {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                uiHelper.alertError("Oops", String.format(locale(R.string.login_activity_login_error), "doSignIn::JSONSuccessListener"));
            } finally {
                buttonLogin.setProgress(0);
                setAllControlsEnabled(true);
            }
        }, (JSONObject response) -> {
            try {
                String message = response.getJSONObject("error").getString("message");

                uiHelper.alertError("Oops", message);
            }catch(Exception e) {
                uiHelper.alertError("Oops", String.format(locale(R.string.login_activity_login_error), "doSignIn::JSONFailureListener"));
            }finally{
                buttonLogin.setProgress(0);
                setAllControlsEnabled(true);
            }
        });

    }

}
