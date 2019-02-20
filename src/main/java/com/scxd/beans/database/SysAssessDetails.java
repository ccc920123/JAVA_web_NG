package com.scxd.beans.database;

/**
 *  考核详情
 */
public class SysAssessDetails {

    private String id;
    private String bt;
    private String khsj;
    private String xqid;
    private String khdx;
    private String qyname;

    public String getQyname() {
        return qyname;
    }

    public void setQyname(String qyname) {
        this.qyname = qyname;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getKhsj() {
        return khsj;
    }

    public void setKhsj(String khsj) {
        this.khsj = khsj;
    }

    public String getXqid() {
        return xqid;
    }

    public void setXqid(String xqid) {
        this.xqid = xqid;
    }

    public String getKhdx() {
        return khdx;
    }

    public void setKhdx(String khdx) {
        this.khdx = khdx;
    }

    public String getKhdf() {
        return khdf;
    }

    public void setKhdf(String khdf) {
        this.khdf = khdf;
    }

    private String khdf;

}
