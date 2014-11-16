package im.after.app.helper;

import android.util.Log;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class EncryptHelper {

    private static final String TAG = "EncryptHelper";

    public static String md5(String text) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(text.getBytes(), 0, text.length());

            return new BigInteger(1, messageDigest.digest()).toString(16);
        }catch(NoSuchAlgorithmException e) {
            Log.d(TAG, "Error in EncryptHelper::md5");
        }
        return "";
    }

}
