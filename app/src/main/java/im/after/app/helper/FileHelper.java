package im.after.app.helper;

import java.io.File;

import im.after.app.AppConfig;

public class FileHelper {

    private static final String baseDataPath = android.os.Environment.getExternalStorageDirectory().getAbsolutePath() + "/Android/data/im.after.app";
    private static final String storagePath = baseDataPath + "/storage";

    public static String databaseFilePath() {
        String databaseFilePath = storagePath + "/" + AppConfig.Database.FILENAME;

        File file = new File(storagePath);

        if (!file.exists()) {
            file.mkdirs();
        }

        return databaseFilePath;
    }

}
