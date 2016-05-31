package im.after.app.data.api.dashboard;

import im.after.app.data.api.dashboard.bean.DashboardBean;
import retrofit2.http.GET;
import retrofit2.http.Query;
import rx.Observable;

public interface DashboardService {

    @GET("all")
    Observable<DashboardBean> all(@Query("page") int page);

}
