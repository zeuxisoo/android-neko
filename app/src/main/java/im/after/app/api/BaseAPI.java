package im.after.app.api;

import android.content.Context;

import com.loopj.android.http.RequestParams;

import java.util.HashMap;

import im.after.app.helper.HttpHelper;

public class BaseAPI {

//    private static final String APIServer = "http://10.0.1.10:5000/api";
    private static final String APIServer = "https://www.after.im/api";


    protected Context context;
    protected HttpHelper httpHelper;

    public BaseAPI(Context context) {
        this.context    = context;
        this.httpHelper = new HttpHelper(this.context);
    }

    public String request(String path) {
        return APIServer + path;
    }

    public RequestParams params(HashMap<String, String> hashParams) {
        return new RequestParams(hashParams);
    }

    public RequestParams params(String key, String value) {
        return new RequestParams(key, value);
    }

}
