package im.after.app.entity.bean;

public class TalkDialogItemBean {

    private int icon;
    private String name;

    public TalkDialogItemBean(int icon, String name) {
        this.setIcon(icon);
        this.setName(name);
    }

    public int getIcon() {
        return icon;
    }

    public void setIcon(int icon) {
        this.icon = icon;
    }

    public String getName() {
        return name;
    }

    public void setName(String text) {
        this.name = text;
    }

}
