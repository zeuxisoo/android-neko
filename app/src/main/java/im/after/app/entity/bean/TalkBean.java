package im.after.app.entity.bean;

import im.after.app.entity.BaseEntity;

public class TalkBean extends BaseEntity {

    private String subject;

    public TalkBean(String subject) {
        this.setSubject(subject);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
