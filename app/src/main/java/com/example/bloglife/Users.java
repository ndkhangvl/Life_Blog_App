package com.example.bloglife;

public class Users {
    protected String uemail;
    protected String passwd;
    protected String uname;
    protected String uaddress;
    protected String uid;

    public  Users() {
    }

    public Users(String uemail, String uname, String uaddress) {
        this.uemail = uemail;
        this.uname = uname;
        this.uaddress = uaddress;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getPasswd() {
        return passwd;
    }

    public void setPasswd(String passwd) {
        this.passwd = passwd;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUaddress() {
        return uaddress;
    }

    public void setUaddress(String uaddress) {
        this.uaddress = uaddress;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
