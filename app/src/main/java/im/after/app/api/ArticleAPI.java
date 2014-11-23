package im.after.app.api;

import android.content.Context;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.apache.http.Header;
import org.json.JSONObject;

import java.util.HashMap;

import im.after.app.api.listener.JSONFailureListener;
import im.after.app.api.listener.JSONSuccessListener;

public class ArticleAPI extends BaseAPI {

    public ArticleAPI(Context context) {
        super(context);
    }

    public void page(int pageNo, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.get(this.request("/article"), this.params("page", String.valueOf(pageNo)), new JsonHttpResponseHandler() {
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

    public void create(HashMap<String, String> params, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.post(this.request("/article/create"), this.params(params), new JsonHttpResponseHandler() {
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

    public void delete(int talkId, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.get(this.request("/article/delete/" + talkId), new JsonHttpResponseHandler() {
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

    public void update(int talkId, HashMap<String, String> params, final JSONSuccessListener succssListener, final JSONFailureListener failureListener) {
        this.httpHelper.post(this.request("/article/update/" + talkId), this.params(params), new JsonHttpResponseHandler() {
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
