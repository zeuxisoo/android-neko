package im.after.app.data.api.dashboard.bean.all;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaginationBean {

    @SerializedName("count")
    @Expose
    private int count;

    @SerializedName("current_page")
    @Expose
    private int currentPage;

    @SerializedName("link")
    @Expose
    private PaginationLinkBean link;

    @SerializedName("per_page")
    @Expose
    private int perPage;

    @SerializedName("total")
    @Expose
    private int total;

    @SerializedName("total_pages")
    @Expose
    private int totalPages;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public PaginationLinkBean getLink() {
        return link;
    }

    public void setLink(PaginationLinkBean link) {
        this.link = link;
    }

    public int getPerPage() {
        return perPage;
    }

    public void setPerPage(int perPage) {
        this.perPage = perPage;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

}
