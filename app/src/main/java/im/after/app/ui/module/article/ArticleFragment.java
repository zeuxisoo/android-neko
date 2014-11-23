package im.after.app.ui.module.article;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.melnykov.fab.FloatingActionButton;

import org.json.JSONObject;

import java.util.ArrayList;

import im.after.app.R;
import im.after.app.api.ArticleAPI;
import im.after.app.entity.bean.ArticleBean;
import im.after.app.entity.bean.ArticleDialogItemBean;
import im.after.app.entity.bean.ArticleItemBean;
import im.after.app.helper.SweetDialogHelper;
import im.after.app.helper.ToastHelper;
import im.after.app.ui.base.BaseFragment;
import im.after.app.ui.listener.RecyclerViewItemClickListener;

public class ArticleFragment extends BaseFragment {

    private static final String TAG = ArticleFragment.class.getSimpleName();

    private static final int REQUEST_CODE_COMPOSE = 1;
    private static final int REQUEST_CODE_EDIT    = 2;

    private SwipeRefreshLayout swipeRefreshLayoutFragmentArticle;
    private RecyclerView recyclerViewFragmentArticle;
    private LinearLayoutManager linearLayoutManager;
    private FloatingActionButton floatingActionButtonFragmentArticle;

    private ArticleItemFragmentAdapter articleItemFragmentAdapter;
    private SweetDialogHelper sweetDialogHelper;
    private Handler handler;

    private int currentPageNo     = 1;
    private boolean pageHasNext   = false;
    private boolean pageIsLoading = false;
    private boolean showingNoMore = false;

    interface RequestPageFinishedCallBack {
        void onFinish();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View viewFragmentArticle = inflater.inflate(R.layout.fragment_article, container, false);

        this.swipeRefreshLayoutFragmentArticle   = (SwipeRefreshLayout) viewFragmentArticle.findViewById(R.id.swipeRefreshLayoutFragmentArticle);
        this.recyclerViewFragmentArticle         = (RecyclerView) viewFragmentArticle.findViewById(R.id.recyclerViewFragmentArticle);
        this.floatingActionButtonFragmentArticle = (FloatingActionButton) viewFragmentArticle.findViewById(R.id.floatingActionButtonFragmentArticle);

        return viewFragmentArticle;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // Set base object
        this.sweetDialogHelper = new SweetDialogHelper(this.getActivity());
        this.handler  = new Handler();

        // Set recycler view is not fixed size, layout manager and adapter
        this.linearLayoutManager = new LinearLayoutManager(this.getActivity());
        this.linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        this.articleItemFragmentAdapter = new ArticleItemFragmentAdapter();

        this.recyclerViewFragmentArticle.setHasFixedSize(false);
        this.recyclerViewFragmentArticle.setAdapter(this.articleItemFragmentAdapter);
        this.recyclerViewFragmentArticle.setItemAnimator(new DefaultItemAnimator());
        this.recyclerViewFragmentArticle.setLayoutManager(this.linearLayoutManager);

        // Attach float button to recyler view
        this.floatingActionButtonFragmentArticle.attachToRecyclerView(this.recyclerViewFragmentArticle);

        // Set action
        this.setCreateArticleEvent();
        this.setPullDownEvent();
        this.setLoadMoreEvent();
        this.setClickItemEvent();
        this.requestArticlePage(this.currentPageNo);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == this.getActivity().RESULT_OK) {
            ArticleItemBean articleItemBean;
            int articleItemBeanPosition;

            switch (requestCode) {
                case REQUEST_CODE_COMPOSE:
                    articleItemBean = (ArticleItemBean) data.getExtras().getSerializable("articleItemBean");

                    this.articleItemFragmentAdapter.prependArticleItemBean(articleItemBean);

                    ToastHelper.show(this.getActivity(), R.string.article_fragment_compose_article_created);
                    break;
                case REQUEST_CODE_EDIT:
                    articleItemBean = (ArticleItemBean) data.getExtras().getSerializable("articleItemBean");
                    articleItemBeanPosition = data.getExtras().getInt("articleItemBeanPosition");

                    this.articleItemFragmentAdapter.setArticleItemBean(articleItemBeanPosition, articleItemBean);

                    ToastHelper.show(this.getActivity(), R.string.article_fragment_compose_article_updated);
                default:
                    break;
            }
        }
    }

    private void setCreateArticleEvent() {
        this.floatingActionButtonFragmentArticle.setOnClickListener((View v) -> {
            Intent intent = new Intent(this.getActivity(), ArticleCreateActivity.class);
            intent.putExtra("type", ArticleCreateActivity.TYPE_ARTICLE);

            this.startActivityForResult(intent, REQUEST_CODE_COMPOSE);
        });
    }

    private void setPullDownEvent() {
        this.swipeRefreshLayoutFragmentArticle.setColorSchemeResources(
            android.R.color.holo_red_light,
            android.R.color.holo_blue_light,
            android.R.color.holo_green_light,
            android.R.color.holo_orange_light
        );
        this.swipeRefreshLayoutFragmentArticle.setOnRefreshListener(() -> {
            this.floatingActionButtonFragmentArticle.hide();
            this.swipeRefreshLayoutFragmentArticle.setRefreshing(true);
            this.articleItemFragmentAdapter.clearArticleItemBeans();

            this.requestArticlePage(1, () -> {
                this.currentPageNo = 1;
                this.floatingActionButtonFragmentArticle.show();
                this.swipeRefreshLayoutFragmentArticle.setRefreshing(false);
            });
        });
    }

    private void setLoadMoreEvent() {
        this.recyclerViewFragmentArticle.setOnScrollListener(new RecyclerView.OnScrollListener() {

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {
                super.onScrollStateChanged(recyclerView, newState);

                if (!pageIsLoading && pageHasNext && articleItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    pageIsLoading = true;

                    currentPageNo = currentPageNo + 1;

                    ToastHelper.show(
                        getActivity(),
                        String.format(getActivity().getString(R.string.article_fragment_current_loading_page), currentPageNo)
                    );

                    requestArticlePage(currentPageNo);
                }

                if (!showingNoMore && !pageHasNext && articleItemFragmentAdapter.getItemCount() ==  linearLayoutManager.findLastVisibleItemPosition() + 1) {
                    showingNoMore = true;

                    ToastHelper.show(getActivity(), R.string.article_fragment_no_more_page);

                    handler.postDelayed(() -> showingNoMore = false, 2000);
                }
            }

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);

                // Fix swipe refresh layout and recycler view will cut the card view item
                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerViewFragmentArticle.getLayoutManager();

                swipeRefreshLayoutFragmentArticle.setEnabled(layoutManager.findFirstCompletelyVisibleItemPosition() == 0);

                // Hide the button when scroll down, show the button when scroll up
                if (dy > 0) {
                    floatingActionButtonFragmentArticle.hide();
                }else{
                    floatingActionButtonFragmentArticle.show();
                }
            }

        });
    }

    private void setClickItemEvent() {
        this.recyclerViewFragmentArticle.addOnItemTouchListener(new RecyclerViewItemClickListener(this.getActivity(), this.recyclerViewFragmentArticle, new RecyclerViewItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                showFullArticle(position);
            }

            @Override
            public void onItemLongClick(View view, int position) {
                showOptionsMenu(position);
            }
        }));
    }

    private void requestArticlePage(int pageNo) {
        this.requestArticlePage(pageNo, () -> {});
    }

    private void requestArticlePage(int pageNo, RequestPageFinishedCallBack callback) {
        this.pageIsLoading = true;

        ArticleAPI articleAPI = new ArticleAPI(this.getActivity());

        articleAPI.page(
            pageNo,
            (JSONObject response) -> {
                try {
                    ObjectMapper objectMapper = new ObjectMapper();
                    ArticleBean articleBean = objectMapper.readValue(response.toString(), ArticleBean.class);

                    this.pageHasNext = (articleBean.getNext() != null);

                    if (pageNo > 1) {
                        this.articleItemFragmentAdapter.addArticleItemBeans(articleBean.getItems());
                    }else{
                        this.articleItemFragmentAdapter.setArticleItemBeans(articleBean.getItems());
                    }

                    callback.onFinish();
                }catch(Exception e) {
                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_request_page_error), "loadTalkPage::JSONSuccessListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            },
            (JSONObject response) -> {
                try {
                    JSONObject errorObject = response.getJSONObject("error");
                    String message = errorObject.getString("message");
                    int status     = errorObject.getInt("status");

                    if (status == 404) {
                        sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_not_found_page_number), pageNo));
                    }else{
                        sweetDialogHelper.alertError("Oops", message);
                    }
                }catch(Exception e) {
                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_request_page_error), "loadTalkPage::JSONFailureListener"));
                }finally{
                    this.pageIsLoading = false;
                }
            }
        );
    }

    private void showOptionsMenu(int position) {
        ArrayList<ArticleDialogItemBean> dialogArticleItem = new ArrayList<ArticleDialogItemBean>();
        dialogArticleItem.add(new ArticleDialogItemBean(R.drawable.ic_edit_white, locale(R.string.article_fragment_dialog_item_edit)));
        dialogArticleItem.add(new ArticleDialogItemBean(R.drawable.ic_delete_white, locale(R.string.article_fragment_dialog_item_delete)));

        ArticleItemDialogAdapter articleItemDialogAdapter = new ArticleItemDialogAdapter(this.getActivity(), R.layout.dialog_article_item, dialogArticleItem);

        AlertDialog.Builder builder = new AlertDialog.Builder(this.getActivity());
        builder.setCancelable(true);
        builder.setAdapter(articleItemDialogAdapter, (DialogInterface dialog, int which) -> {
            this.clickOnOptionsMenuItem(dialog, which, position);
        });
        builder.show();
    }

    public void clickOnOptionsMenuItem(DialogInterface dialog, int which, int position) {
        ArticleItemBean articleItemBean = this.articleItemFragmentAdapter.getArticleItem(position);

        switch(which) {
            case 0: // edit
                Intent intent = new Intent(this.getActivity(), ArticleEditActivity.class);
                intent.putExtra("type", ArticleCreateActivity.TYPE_ARTICLE);
                intent.putExtra("articleItemBean", articleItemBean);
                intent.putExtra("articleItemBeanPosition", position);

                this.startActivityForResult(intent, REQUEST_CODE_EDIT);
                break;
            case 1: // delete
                this.sweetDialogHelper.confirm(
                    locale(R.string.article_fragment_confirm_delete_title),
                    locale(R.string.article_fragment_confirm_delete_content),
                    locale(R.string.article_fragment_confirm_delete_yes),
                    () -> {
                        ArticleAPI articleAPI = new ArticleAPI(this.getActivity());

                        articleAPI.delete(
                            articleItemBean.getId(),
                            (JSONObject response) -> {
                                try {
                                    String message = response.getString("message");
                                    int status     = response.getInt("status");

                                    if (status == 200) {
                                        this.articleItemFragmentAdapter.removeArticleItemBean(position);

                                        ToastHelper.show(this.getActivity(), message);
                                    }else{
                                        sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_delete_article_error), message));
                                    }
                                }catch(Exception e) {
                                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_delete_article_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            },
                            (JSONObject response) -> {
                                try {
                                    JSONObject errorObject = response.getJSONObject("error");
                                    String message = errorObject.getString("message");

                                    sweetDialogHelper.alertError("Oops", message);
                                }catch(Exception e) {
                                    sweetDialogHelper.alertError("Oops", String.format(locale(R.string.article_fragment_delete_article_error), "clickOnOptionsMenuItem::JSONSuccessListener"));
                                }
                            }
                        );
                    }
                );
                break;
        }
    }

    private void showFullArticle(int position) {
        ArticleItemBean articleItemBean = this.articleItemFragmentAdapter.getArticleItem(position);

        Intent intent = new Intent(this.getActivity(), ArticleFullActivity.class);
        intent.putExtra("articleItemBean", articleItemBean);

        this.startActivity(intent);
    }

}