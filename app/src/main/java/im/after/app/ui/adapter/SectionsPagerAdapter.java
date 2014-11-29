package im.after.app.ui.adapter;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import im.after.app.R;
import im.after.app.ui.module.article.ArticleFragment;
import im.after.app.ui.module.memo.MemoFragment;
import im.after.app.ui.module.talk.TalkFragment;

public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    private static final String TAG = SectionsPagerAdapter.class.getSimpleName();

    private static final Fragment PAGE_FRAGMENTS[] = new Fragment[] {
        new ArticleFragment(),
        new MemoFragment(),
        new TalkFragment(),
    };
    private static final int PAGE_TITLES[] = new int[] {
        R.string.main_activity_drawer_menu_item_subject_article_text,
        R.string.main_activity_drawer_menu_item_subject_memo_text,
        R.string.main_activity_drawer_menu_item_subject_talk_text,
    };

    private Context context;

    public SectionsPagerAdapter(Context context, FragmentManager fragmentManager) {
        super(fragmentManager);

        this.context = context;
    }

    @Override
    public int getCount() {
        return this.PAGE_TITLES.length;
    }

    @Override
    public Fragment getItem(int i) {
        if (i >= 0 && i < PAGE_TITLES.length) {
            return PAGE_FRAGMENTS[i];
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        if (position >= 0 && position < PAGE_TITLES.length) {
            return this.context.getString(PAGE_TITLES[position]).toUpperCase();
        }
        return null;
    }
}
