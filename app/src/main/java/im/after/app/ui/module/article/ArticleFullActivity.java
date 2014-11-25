package im.after.app.ui.module.article;

import android.os.Bundle;
import android.widget.TextView;

import im.after.app.R;
import im.after.app.entity.bean.ArticleItemBean;
import im.after.app.ui.base.BaseActivity;
import in.uncod.android.bypass.Bypass;

public class ArticleFullActivity extends BaseActivity {

    TextView textViewArticleFullTitle;
    TextView webViewArticleFullContent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        this.setContentView(R.layout.activity_article_full);

        this.textViewArticleFullTitle  = (TextView) this.findViewById(R.id.textViewArticleFullTitle);
        this.webViewArticleFullContent = (TextView) this.findViewById(R.id.textViewArticleFullContent);

        ArticleItemBean articleItembean = (ArticleItemBean) this.getIntent().getSerializableExtra("articleItemBean");

        Bypass bypass = new Bypass(this.getApplicationContext());
        CharSequence markdown = bypass.markdownToSpannable(articleItembean.getContent());

        this.textViewArticleFullTitle.setText(articleItembean.getTitle());
        this.webViewArticleFullContent.setText(markdown);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        this.finish();
        this.overridePendingTransition(R.anim.zoom_in, R.anim.slide_down_exit);
    }
}
