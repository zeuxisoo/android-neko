package im.after.app.helper;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

public class HttpHelper {

    private static AsyncHttpClient client = new AsyncHttpClient();

    public HttpHelper(Context context) {
        this.client.setCookieStore(new PersistentCookieStore(context));
    }

    public void get(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        this.client.get(url, params, responseHandler);
    }

    public void post(String url, RequestParams params, JsonHttpResponseHandler responseHandler) {
        this.client.post(url, params, responseHandler);
    }

}
