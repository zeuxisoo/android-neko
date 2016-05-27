package im.after.app.data.api;

import com.raizlabs.android.dbflow.sql.language.SQLite;

import java.io.IOException;

import im.after.app.data.db.model.TokenModel;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthorizationInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        // Find token from database
        TokenModel tokenModel = SQLite.select().from(TokenModel.class).querySingle();
        String token = tokenModel != null ? tokenModel.getToken() : "";

        // Add authorization header
        Request.Builder requestBuilder = chain.request()
                .newBuilder()
                .addHeader("Authorization", String.format("bearer  %s", token));

        Request request = requestBuilder.build();

        return chain.proceed(request);
    }

}
