package com.scxd.beans.database;


import java.util.Date;

public class SysAddArea {
    private String qyid;
    private String qyname;
    private String sjqyid;
    private Date cjsj;
    private String cjr;
    private int xzjb;
    private String qysp;
    private String qyjj;

    public SysAddArea(String qyid, String qyname, String sjqyid, Date cjsj, String cjr, int xzjb, String qysp, String qyjj) {
        this.qyid = qyid;
        this.qyname = qyname;
        this.sjqyid = sjqyid;
        this.cjsj = cjsj;
        this.cjr = cjr;
        this.xzjb = xzjb;
        this.qysp = qysp;
        this.qyjj = qyjj;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getQyname() {
        return qyname;
    }

    public void setQyname(String qyname) {
        this.qyname = qyname;
    }

    public String getSjqyid() {
        return sjqyid;
    }

    public void setSjqyid(String sjqyid) {
        this.sjqyid = sjqyid;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getCjr() {
        return cjr;
    }

    public void setCjr(String cjr) {
        this.cjr = cjr;
    }

    public int getXzjb() {
        return xzjb;
    }

    public void setXzjb(int xzjb) {
        this.xzjb = xzjb;
    }

    public String getQysp() {
        return qysp;
    }

    public void setQysp(String qysp) {
        this.qysp = qysp;
    }

    public String getQyjj() {
        return qyjj;
    }

    public void setQyjj(String qyjj) {
        this.qyjj = qyjj;
    }

}
