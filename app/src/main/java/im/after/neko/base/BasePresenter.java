package im.after.neko.base;

public abstract class BasePresenter {

    abstract public void attachView(BaseView view);

    abstract public void detachView();

}
