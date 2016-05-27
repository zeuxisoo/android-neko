package im.after.app.base;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public abstract class BaseApi<T> {

    private T mService;

    public BaseApi(OkHttpClient.Builder okHttpClientBuilder) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(this.getClient(okHttpClientBuilder))
                .baseUrl(this.getBaseUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        this.mService = this.getApiService(retrofit);
    }

    public T getService() {
        return mService;
    }

    abstract public String getBaseUrl();
    abstract public OkHttpClient getClient(OkHttpClient.Builder okHttpClientBuilder);
    abstract public T getApiService(Retrofit retrofit);

}
