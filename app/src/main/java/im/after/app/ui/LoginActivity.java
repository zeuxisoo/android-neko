package im.after.app.ui;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.dd.processbutton.iml.ActionProcessButton;

import im.after.app.R;


public class LoginActivity extends BaseActivity {

    private static final String TAG  = "LoginActivity";

    EditText editTextUsername;
    EditText editTextPassword;
    ActionProcessButton buttonSignIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.getActionBar().hide();
        this.getActionBar().setDisplayUseLogoEnabled(true);
        this.getActionBar().setDisplayShowTitleEnabled(false);

        this.setContentView(R.layout.activity_login);

        this.editTextUsername = (EditText) this.findViewById(R.id.editTextUsername);
        this.editTextPassword = (EditText) this.findViewById(R.id.editTextPassword);

        this.buttonSignIn = (ActionProcessButton) this.findViewById(R.id.buttonSignIn);
        this.buttonSignIn.setMode(ActionProcessButton.Mode.ENDLESS);
        this.buttonSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                buttonSignIn.setEnabled(false);
                editTextUsername.setEnabled(false);
                editTextPassword.setEnabled(false);

                // Start progress animation (Start at 0 will not show animation)
                buttonSignIn.setProgress(1);
            }
        });
    }

}
