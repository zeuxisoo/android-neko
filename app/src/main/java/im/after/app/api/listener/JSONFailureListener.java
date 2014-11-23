package im.after.app.api.listener;

import org.json.JSONObject;

public interface JSONFailureListener {
    void onErrorJSON(JSONObject response);
}
