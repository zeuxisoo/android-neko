package im.after.neko.contract.login;

public interface LoginContract {

    void loadAccountAndPasswordByRememberMe();

    void doLogin(String account, String password, boolean isRememberMe);

}
