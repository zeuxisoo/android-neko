package im.after.app.presenter.dashboard;

import com.google.gson.Gson;
import com.orhanobut.logger.Logger;

import java.io.IOException;

import javax.inject.Inject;

import im.after.app.base.BasePresenter;
import im.after.app.base.BaseView;
import im.after.app.contract.dashboard.DashboardContract;
import im.after.app.data.api.ErrorBean;
import im.after.app.data.api.dashboard.bean.DashboardsBean;
import im.after.app.model.dashboard.DashboardModel;
import im.after.app.view.dashboard.DashboardActivity;
import retrofit2.adapter.rxjava.HttpException;

public class DashboardPresenter extends BasePresenter implements DashboardContract {

    private DashboardActivity mDashboardActivity;

    private DashboardModel mDashboardModel;

    private Gson mGson;

    @Inject
    public DashboardPresenter(DashboardModel dashboardModel, Gson gson) {
        this.mDashboardModel = dashboardModel;
        this.mGson           = gson;
    }

    // Implementation for BasePresenter
    @Override
    public void attachView(BaseView view) {
        this.mDashboardActivity = (DashboardActivity) view;
    }

    @Override
    public void detachView() {
        this.mDashboardActivity = null;
    }

    // Implementation for DashboardContract
    @Override
    public void loadDashboards(int page) {
        this.mDashboardModel.loadDashboards(1, this::handleLoadDashboardsSuccess, this::handleLoadDashboardsError);
    }

    @Override
    public void onRefresh() {
        this.mDashboardModel.loadDashboards(1, this::handleOnRefreshSuccess, this::handleOnRefreshError);

        this.mDashboardActivity.stopRefreshAnimation();
    }

    @Override
    public void onMore(int overallItemsCount, int itemsBeforeMore, int maxLastVisiblePosition) {
        Logger.d("onMore");

        this.mDashboardActivity.stopMoreAnimation();
    }

    // Subscribe handler for loadDashboards method
    private void handleLoadDashboardsSuccess(DashboardsBean dashboardsBean) {
        this.mDashboardActivity.renderDashboardList(dashboardsBean.getDashboardList());
    }

    private void handleLoadDashboardsError(Throwable throwable) {
        if (throwable instanceof HttpException) {
            HttpException httpException = (HttpException) throwable;

            try {
                ErrorBean errorBean = this.mGson.fromJson(httpException.response().errorBody().string(), ErrorBean.class);

                this.mDashboardActivity.showSnackbar(
                        String.format("%s - %s", errorBean.getStatusCode(), errorBean.getMessage())
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }else{
            throwable.printStackTrace();
        }
    }

    // Subscribe handler for onRefresh method
    private void handleOnRefreshSuccess(DashboardsBean dashboardsBean) {
        this.mDashboardActivity.clearDashboardList();
        this.mDashboardActivity.renderDashboardList(dashboardsBean.getDashboardList());
    }

    private void handleOnRefreshError(Throwable throwable) {
        this.handleLoadDashboardsError(throwable);
    }

}
