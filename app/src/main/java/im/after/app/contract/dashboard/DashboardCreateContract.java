package im.after.app.contract.dashboard;

public interface DashboardCreateContract {

    void doCancel(String content);

    void doClear(String content);

    void doCopy(String tag, String content);

    void doSend(String subject, String content);

}
