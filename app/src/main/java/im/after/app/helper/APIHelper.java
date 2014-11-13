package im.after.app.helper;

public class APIHelper {

    public static final String APIServer = "http://10.0.1.10:5000/api";

    public static String Url(String path) {
        return APIServer + path;
    }

    public static String signInUrl() {
        return Url("/main/signin");
    }

}
