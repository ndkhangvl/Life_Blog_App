package com.example.bloglife;

public class InfoBlog {
    String title;
    String content;
    String bdate;
    String iuser;
    String uname;
    String id;

    public InfoBlog() {

    }

    public InfoBlog(String uname,String title, String content, String bdate, String iuser) {
        this.uname = uname;
        this.title = title;
        this.content = content;
        this.bdate = bdate;
        this.iuser = iuser;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getBdate() {
        return bdate;
    }

    public void setBdate(String bdate) {
        this.bdate = bdate;
    }

    public String getIuser() {
        return iuser;
    }

    public void setIuser(String iuser) {
        this.iuser = iuser;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
