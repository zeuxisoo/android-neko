package im.after.app.base;

public abstract class BasePresenter {

    abstract public void attachView(BaseView view);

    abstract public void detachView();

}
