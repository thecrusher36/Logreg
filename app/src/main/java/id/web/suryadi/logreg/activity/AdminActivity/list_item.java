package id.web.suryadi.logreg.activity.AdminActivity;

/**
 * Created by Sandi Suryadi on 1/28/2017.
 * Web : suryadi.web.id
 * Email : sandi@suryadi.web.id
 */

public class list_item {
    private String name;
    private String post;
    private String datetime;
    private Integer approve;
    private String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getDatetime() {
        return datetime;
    }

    public void setDatetime(String datetime) {
        this.datetime = datetime;
    }

    public Integer getApprove() {
        return approve;
    }

    public void setApprove(Integer approve) {
        this.approve = approve;
    }
}
