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

import im.after.app.AppContext;
import im.after.app.R;
import im.after.app.api.MainAPI;
import im.after.app.entity.bean.UserBean;
import im.after.app.entity.table.LoginTable;
import im.after.app.helper.DaoHelper;
import im.after.app.helper.SweetDialogHelper;

public class LoginActivity extends BaseActivity {

    private static final String TAG  = LoginActivity.class.getSimpleName();

    private EditText editTextLoginAccount;
    private EditText editTextLoginPassword;
    private ActionProcessButton buttonLogin;
    private CheckBox checkboxSaveLoginInfo;

    private SweetDialogHelper sweetDialogHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_login);

        this.sweetDialogHelper = new SweetDialogHelper(this);

        this.editTextLoginAccount = (EditText) this.findViewById(R.id.editTextLoginAccount);
        this.editTextLoginPassword = (EditText) this.findViewById(R.id.editTextLoginPassword);

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
        editTextLoginAccount.setEnabled(status);
        editTextLoginPassword.setEnabled(status);
        checkboxSaveLoginInfo.setEnabled(status);
        buttonLogin.setEnabled(status);
    }

    private void setLoginInfo() {
        DaoHelper daoHelper = new DaoHelper<LoginTable>(this, LoginTable.class);
        LoginTable loginTable = (LoginTable) daoHelper.findFirst();

        if (loginTable != null) {
            this.editTextLoginAccount.setText(loginTable.getAccount());
            this.editTextLoginPassword.setText(loginTable.getPassword());
            this.checkboxSaveLoginInfo.setChecked(true);
        }
    }

    private void saveLoginInfo() {
        // Clear info first
        DaoHelper daoHelper = new DaoHelper<LoginTable>(this, LoginTable.class);
        daoHelper.deleteAll();

        // If save, create info again
        if (this.checkboxSaveLoginInfo.isChecked()) {
            String account = this.editTextLoginAccount.getText().toString().trim();
            String password = this.editTextLoginPassword.getText().toString().trim();

            LoginTable loginTable = new LoginTable();
            loginTable.setAccount(account);
            loginTable.setPassword(password);

            daoHelper.create(loginTable);
        }
    }

    private void doLogin() {
        // Start progress animation (Start at 0 will not show animation)
        buttonLogin.setProgress(1);

        // Call sign in api
        MainAPI mainAPI = new MainAPI(this);

        mainAPI.signIn(new HashMap<String, String>() {{
            put("account", editTextLoginAccount.getText().toString());
            put("password", editTextLoginPassword.getText().toString());
            put("permanent", "1");
        }}, (JSONObject response) -> {
            buttonLogin.setProgress(100);

            try {
                ObjectMapper objectMapper = new ObjectMapper();
                UserBean userBean = objectMapper.readValue(response.toString(), UserBean.class);

                AppContext appContext = (AppContext) this.getApplicationContext();
                appContext.setUserBean(userBean);

                startActivity(new Intent(appContext, MainActivity.class));

                finish();
            } catch (Exception e) {
                sweetDialogHelper.alertError("Oops", String.format(locale(R.string.login_activity_login_error), "doSignIn::JSONSuccessListener"));

                buttonLogin.setProgress(0);
                setAllControlsEnabled(true);
            }
        }, (JSONObject response) -> {
            try {
                String message = response.getJSONObject("error").getString("message");

                sweetDialogHelper.alertError("Oops", message);
            }catch(Exception e) {
                sweetDialogHelper.alertError("Oops", String.format(locale(R.string.login_activity_login_error), "doSignIn::JSONFailureListener"));
            }finally{
                buttonLogin.setProgress(0);
                setAllControlsEnabled(true);
            }
        });

    }

}
