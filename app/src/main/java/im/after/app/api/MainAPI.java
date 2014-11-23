package im.after.app.api;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.api.listener.JSONFailureListener;
import im.after.app.api.listener.JSONSuccessListener;

public class MainAPI extends BaseAPI {

    public MainAPI(Context context) {
        super(context);
    }

    public void signIn(HashMap<String, String> params, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.post(this.request("/main/signin"), this.params(params), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                succssListener.onJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                failureListener.onErrorJSON(errorResponse);
            }
        });
    }

}
