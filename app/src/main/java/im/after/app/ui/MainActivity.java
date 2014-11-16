package im.after.app.ui;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import im.after.app.R;
import im.after.app.entity.UserEntity;
import im.after.app.helper.UIHelper;

public class MainActivity extends ActionBarActivity {

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private TextView textViewAccountUsername;
    private TextView textViewAccountEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        // Set toolbar
        this.toolbar = (Toolbar) this.findViewById(R.id.toolbar);
        this.toolbar.inflateMenu(R.menu.menu_main);

        this.setSupportActionBar(this.toolbar);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Set drawer layout
        this.drawerLayout = (DrawerLayout) this.findViewById(R.id.drawerLayout);

        this.actionBarDrawerToggle = new ActionBarDrawerToggle(this, this.drawerLayout, this.toolbar, R.string.main_activity_open_drawer_text, R.string.main_activity_close_drawer_text);
        this.actionBarDrawerToggle.syncState();

        this.drawerLayout.setDrawerListener(this.actionBarDrawerToggle);

        // Set base control
        this.textViewAccountUsername = (TextView) this.findViewById(R.id.textViewAccountUsername);
        this.textViewAccountEmail    = (TextView) this.findViewById(R.id.textViewAccountEmail);

        // Set user info
        this.setUserInfo();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        this.getMenuInflater().inflate(R.menu.menu_main, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_settings) {
            new UIHelper(this).alertSuccess("Master", "I am settings");
        }

        return super.onOptionsItemSelected(item);
    }

    private void setUserInfo() {
        UserEntity userEntity = (UserEntity) this.getIntent().getSerializableExtra("userEntity");

        if (userEntity != null) {
            this.textViewAccountUsername.setText(userEntity.getUsername());
            this.textViewAccountEmail.setText(userEntity.getEmail());
        }
    }
}
