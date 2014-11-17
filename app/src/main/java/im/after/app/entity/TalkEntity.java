package im.after.app.entity;

public class TalkEntity extends BaseEntity {

    private String subject;

    public TalkEntity(String subject) {
        this.setSubject(subject);
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
