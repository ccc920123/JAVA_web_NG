package com.scxd.beans.database;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 16:10 2018/11/22
 * @Modified By:
 */
public class AssessLogBean {
    private String id;
    private String khqy;
    private int khlx;
    private String khdx;
    private int scjg;
    private Date cjsj;
    private String ycxx;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getKhqy() {
        return khqy;
    }

    public void setKhqy(String khqy) {
        this.khqy = khqy;
    }

    public int getKhlx() {
        return khlx;
    }

    public void setKhlx(int khlx) {
        this.khlx = khlx;
    }

    public String getKhdx() {
        return khdx;
    }

    public void setKhdx(String khdx) {
        this.khdx = khdx;
    }

    public int getScjg() {
        return scjg;
    }

    public void setScjg(int scjg) {
        this.scjg = scjg;
    }

    public Date getCjsj() {
        return cjsj;
    }

    public void setCjsj(Date cjsj) {
        this.cjsj = cjsj;
    }

    public String getYcxx() {
        return ycxx;
    }

    public void setYcxx(String ycxx) {
        this.ycxx = ycxx;
    }

}
