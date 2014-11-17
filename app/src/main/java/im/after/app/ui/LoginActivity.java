package im.after.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.R;
import im.after.app.api.MainAPI;
import im.after.app.entity.LoginEntity;
import im.after.app.entity.UserEntity;
import im.after.app.helper.DaoHelper;
import im.after.app.helper.UIHelper;

public class LoginActivity extends BaseActivity {

    private static final String TAG  = "LoginActivity";

    private EditText editTextAccount;
    private EditText editTextPassword;
    private ActionProcessButton buttonLogin;
    private CheckBox checkboxSaveLoginInfo;

    private UIHelper uiHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);

        this.uiHelper = new UIHelper(this);

        this.editTextAccount = (EditText) this.findViewById(R.id.editTextAccount);
        this.editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        this.checkboxSaveLoginInfo = (CheckBox) this.findViewById(R.id.checkboxSaveLoginInfo);

        this.buttonLogin = (ActionProcessButton) this.findViewById(R.id.buttonLogin);
        this.buttonLogin.setMode(ActionProcessButton.Mode.ENDLESS);
        this.buttonLogin.setOnClickListener((View v) -> {
            setAllControlsEnabled(false);
            saveLoginInfo();
            doLogin();
        });

        this.setLoginInfo();
    }

    private void setAllControlsEnabled(boolean status) {
        editTextAccount.setEnabled(status);
        editTextPassword.setEnabled(status);
        checkboxSaveLoginInfo.setEnabled(status);
        buttonLogin.setEnabled(status);
    }

    private void setLoginInfo() {
        DaoHelper daoHelper = new DaoHelper<LoginEntity>(this, LoginEntity.class);
        LoginEntity loginEntity = (LoginEntity) daoHelper.findFirst();

        if (loginEntity != null) {
            this.editTextAccount.setText(loginEntity.getAccount());
            this.editTextPassword.setText(loginEntity.getPassword());
            this.checkboxSaveLoginInfo.setChecked(true);
        }
    }

    private void saveLoginInfo() {
        // Clear info first
        DaoHelper daoHelper = new DaoHelper<LoginEntity>(this, LoginEntity.class);
        daoHelper.deleteAll();

        // If save, create info again
        if (this.checkboxSaveLoginInfo.isChecked()) {
            String account = this.editTextAccount.getText().toString().trim();
            String password = this.editTextPassword.getText().toString().trim();

            LoginEntity loginEntity = new LoginEntity();
            loginEntity.setAccount(account);
            loginEntity.setPassword(password);

            daoHelper.create(loginEntity);
        }
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
            buttonLogin.setProgress(100);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                UserEntity userEntity = objectMapper.readValue(response.toString(), UserEntity.class);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("userEntity", userEntity);
                startActivity(intent);
                finish();
            } catch (Exception e) {
                uiHelper.alertError("Oops", String.format(locale(R.string.login_activity_login_error), "doSignIn::JSONSuccessListener"));

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
