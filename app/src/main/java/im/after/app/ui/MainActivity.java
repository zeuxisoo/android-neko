package im.after.app.ui;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.viewpagerindicator.TabPageIndicator;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.entity.UserEntity;
import im.after.app.helper.UIHelper;
import im.after.app.ui.adapter.SectionsPagerAdapter;

public class MainActivity extends BaseActionBarActivity {

    private static final String TAG  = "MainActivity";

    private static final int DRAWER_MENU_ITEM_ARTICLE   = 0;
    private static final int DRAWER_MENU_ITEM_MEMO      = 1;
    private static final int DRAWER_MENU_ITEM_TALK      = 2;
    private static final int DRAWER_MENU_ITEM_SETTINGS  = 3;
    private static final int DRAWER_MENU_ITEM_SEPARATOR = -1;
    private static final int DRAWER_MENU_ITEM_ICON_RESOURCES[] = new int[] {
        R.drawable.include_drawer_menu_item_icon_article,
        R.drawable.include_drawer_menu_item_icon_memo,
        R.drawable.include_drawer_menu_item_icon_talk,
        R.drawable.include_drawer_menu_item_icon_settings,
    };
    private static final int DRAWER_MENU_ITEM_SUBJECT_TEXTS[] = new int[] {
        R.string.main_activity_drawer_menu_item_subject_article_text,
        R.string.main_activity_drawer_menu_item_subject_memo_text,
        R.string.main_activity_drawer_menu_item_subject_talk_text,
        R.string.main_activity_drawer_menu_item_subject_settings_text
    };

    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    private ActionBarDrawerToggle actionBarDrawerToggle;

    private TextView textViewAccountUsername;
    private TextView textViewAccountEmail;
    private ImageView imageViewUserAvatar;
    private ViewPager viewPagerMainNavigation;
    private TabPageIndicator pageIndicatorMainNavigation;

    private ViewGroup linearLayoutDrawerMenuItemList;
    private View drawerMenuItemViews[];

    private ArrayList<Integer> drawerMenuItems = new ArrayList<Integer>();
    private Handler handler;
    private UIHelper uiHelper;

    private int currentSelectedDrawerMenuItem = Integer.MIN_VALUE;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_main);

        // Set base object
        this.handler = new Handler();
        this.uiHelper = new UIHelper(this);

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
        this.textViewAccountUsername     = (TextView) this.findViewById(R.id.textViewAccountUsername);
        this.textViewAccountEmail        = (TextView) this.findViewById(R.id.textViewAccountEmail);
        this.imageViewUserAvatar         = (ImageView) this.findViewById(R.id.imageViewUserAvatar);
        this.viewPagerMainNavigation     = (ViewPager) this.findViewById(R.id.viewPagerMainNavigation);
        this.pageIndicatorMainNavigation = (TabPageIndicator) this.findViewById(R.id.pageIndicatorMainNavigation);

        // Set user info
        this.setUserInfo();
        this.setDrawerMenuItems();
        this.setViewPager();
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
            new UIHelper(this).alertSuccess("About", "Servant! Call me, Master :)");
        }

        return super.onOptionsItemSelected(item);
    }

    private int getCurrentSelectedDrawerMenuItem() {
        return this.currentSelectedDrawerMenuItem;
    }

    private void setCurrentSelectedDrawerMenuItem(int itemId) {
        this.currentSelectedDrawerMenuItem = itemId;
    }

    private void setUserInfo() {
        UserEntity userEntity = (UserEntity) this.getIntent().getSerializableExtra("userEntity");

        if (userEntity != null) {
            Picasso.with(this).load(userEntity.getAvatar()).fit().transform(
                new RoundedTransformationBuilder().borderColor(R.color.drawer_menu_user_avatar_border).borderWidthDp(1).cornerRadiusDp(30).oval(false).build()
            ).into(this.imageViewUserAvatar);

            this.textViewAccountUsername.setText(userEntity.getUsername());
            this.textViewAccountEmail.setText(userEntity.getEmail());
        }
    }

    private void setDrawerMenuItems() {
        this.drawerMenuItems.clear();

        this.drawerMenuItems.add(DRAWER_MENU_ITEM_ARTICLE);
        this.drawerMenuItems.add(DRAWER_MENU_ITEM_MEMO);
        this.drawerMenuItems.add(DRAWER_MENU_ITEM_TALK);
        this.drawerMenuItems.add(DRAWER_MENU_ITEM_SEPARATOR);
        this.drawerMenuItems.add(DRAWER_MENU_ITEM_SETTINGS);

        //
        this.linearLayoutDrawerMenuItemList = (ViewGroup) this.findViewById(R.id.linearLayoutDrawerMenuItemList);

        if (this.linearLayoutDrawerMenuItemList != null) {
            this.drawerMenuItemViews = new View[this.drawerMenuItems.size()];

            this.linearLayoutDrawerMenuItemList.removeAllViews();

            int i = 0;
            for (int itemId : this.drawerMenuItems) {
                this.drawerMenuItemViews[i] = this.createDrawerMenuItem(itemId, this.linearLayoutDrawerMenuItemList);
                this.linearLayoutDrawerMenuItemList.addView(this.drawerMenuItemViews[i]);
                ++i;
            }
        }
    }

    private void setViewPager() {
        this.viewPagerMainNavigation.setAdapter(new SectionsPagerAdapter(this, this.getSupportFragmentManager()));
        this.pageIndicatorMainNavigation.setViewPager(this.viewPagerMainNavigation);
    }

    private View createDrawerMenuItem(int itemId, ViewGroup drawerMenuItemList) {
        boolean selected = this.getCurrentSelectedDrawerMenuItem() == itemId;

        int resource;

        if (itemId == DRAWER_MENU_ITEM_SEPARATOR) {
            resource = R.layout.include_drawer_menu_separator;
        }else {
            resource = R.layout.include_drawer_menu_item;
        }

        View view = this.getLayoutInflater().inflate(resource, drawerMenuItemList, false);

        if (itemId == DRAWER_MENU_ITEM_SEPARATOR) {
            return view;
        }else {
            // Set icon and text for menu item
            ImageView imageViewDrawerMenuItemIcon = (ImageView) view.findViewById(R.id.imageViewDrawerMenuItemIcon);
            TextView textViewDrawMenuItemSubject  = (TextView) view.findViewById(R.id.textViewDrawMenuItemSubject);

            int iconResource = itemId >= 0 && itemId < DRAWER_MENU_ITEM_ICON_RESOURCES.length ? DRAWER_MENU_ITEM_ICON_RESOURCES[itemId] : 0;
            int subjectText  = itemId >= 0 && itemId < DRAWER_MENU_ITEM_SUBJECT_TEXTS.length ? DRAWER_MENU_ITEM_SUBJECT_TEXTS[itemId] : 0;

            imageViewDrawerMenuItemIcon.setVisibility(iconResource > 0 ? View.VISIBLE : View.GONE);

            if (iconResource > 0) {
                imageViewDrawerMenuItemIcon.setImageResource(iconResource);
            }

            textViewDrawMenuItemSubject.setText(this.getString(subjectText));

            renderSelectedDrawerMenuItemStyle(view, itemId, selected);

            // Set click listener
            view.setOnClickListener((View v) -> {
                this.onDrawerMenuItemClicked(itemId);
            });

            return view;
        }
    }

    private void onDrawerMenuItemClicked(int itemId) {
        // Make drawer menu closed before call item activity
        this.handler.postDelayed(() -> {
            openDrawerMenuItem(itemId);
        }, 300);

        this.setSelectedDrawerMenuItem(itemId);

        this.drawerLayout.closeDrawer(Gravity.START);
    }

    private void openDrawerMenuItem(int itemId) {
        switch (itemId) {
            case DRAWER_MENU_ITEM_ARTICLE:
                uiHelper.alertSuccess("Master", "I am article");
                break;
            case DRAWER_MENU_ITEM_MEMO:
                uiHelper.alertSuccess("Master", "I am memo");
                break;
            case DRAWER_MENU_ITEM_TALK:
                uiHelper.alertSuccess("Master", "I am talk");
                break;
            case DRAWER_MENU_ITEM_SETTINGS:
                uiHelper.alertSuccess("Master", "I am settings");
                break;
        }
    }

    private void setSelectedDrawerMenuItem(int itemId) {
        if (this.drawerMenuItemViews != null) {
            for(int i=0; i<this.drawerMenuItemViews.length; i++) {
                if (i < this.drawerMenuItems.size()) {
                    int currentItemId = this.drawerMenuItems.get(i);
                    this.renderSelectedDrawerMenuItemStyle(this.drawerMenuItemViews[i], currentItemId, currentItemId == itemId);
                }
            }
        }
    }

    private void renderSelectedDrawerMenuItemStyle(View drawMenuItemView, int itemId, boolean selected) {
        if (itemId != DRAWER_MENU_ITEM_SEPARATOR) {
            ImageView imageViewDrawerMenuItemIcon = (ImageView) drawMenuItemView.findViewById(R.id.imageViewDrawerMenuItemIcon);
            TextView textViewDrawMenuItemSubject  = (TextView) drawMenuItemView.findViewById(R.id.textViewDrawMenuItemSubject);

            imageViewDrawerMenuItemIcon.setColorFilter(selected ? getResources().getColor(R.color.drawer_menu_item_icon_tint_selected) : getResources().getColor(R.color.drawer_menu_item_icon_tint));
            textViewDrawMenuItemSubject.setTextColor(selected ? getResources().getColor(R.color.drawer_menu_item_text_selected) : getResources().getColor(R.color.drawer_menu_item_text));

            this.setCurrentSelectedDrawerMenuItem(itemId);
        }
    }
}