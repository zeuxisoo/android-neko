package im.after.app.entity.bean;

import java.util.ArrayList;

import im.after.app.entity.BaseEntity;

public class MemoBean extends BaseEntity {

    private int count;
    private ArrayList<MemoItemBean> items;
    private String next;
    private String prev;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public ArrayList<MemoItemBean> getItems() {
        return items;
    }

    public void setItems(ArrayList<MemoItemBean> items) {
        this.items = items;
    }

    public String getNext() {
        return next;
    }

    public void setNext(String next) {
        this.next = next;
    }

    public String getPrev() {
        return prev;
    }

    public void setPrev(String prev) {
        this.prev = prev;
    }
}
