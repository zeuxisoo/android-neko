package im.after.app.data.api.dashboard;

import im.after.app.data.api.dashboard.bean.all.DashboardAllBean;
import im.after.app.data.api.dashboard.bean.create.DashboardCreateBean;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;
import rx.Observable;

public interface DashboardService {

    @GET("all")
    Observable<DashboardAllBean> all(@Query("page") int page);

    @FormUrlEncoded
    @POST("create")
    Observable<DashboardCreateBean> create(@Field("kind") String kind, @Field("subject") String subject, @Field("content") String content);

}
