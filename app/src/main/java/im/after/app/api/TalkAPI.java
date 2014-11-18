package im.after.app.api;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import im.after.app.api.listener.JSONFailureListener;
import im.after.app.api.listener.JSONSuccessListener;

public class TalkAPI extends BaseAPI {

    public TalkAPI(Context context) {
        super(context);
    }

    public void page(int pageNo, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.get(this.request("/talk"), this.params("page", String.valueOf(pageNo)), new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                succssListener.onJSON(response);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                failureListener.onJSON(errorResponse);
            }
        });
    }

}
