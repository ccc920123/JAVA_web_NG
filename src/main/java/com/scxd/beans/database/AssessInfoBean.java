package com.scxd.beans.database;

import java.util.Date;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 16:10 2018/11/22
 * @Modified By:
 */
public class AssessInfoBean {
    private String id;
    private String khqy;
    private String khsj;
    private String bt;
    private Date scsj;
    private int khlx;
    private Date kssj;
    private Date jssj;

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

    public String getKhsj() {
        return khsj;
    }

    public void setKhsj(String khsj) {
        this.khsj = khsj;
    }
    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public Date getScsj() {
        return scsj;
    }

    public void setScsj(Date scsj) {
        this.scsj = scsj;
    }

    public int getKhlx() {
        return khlx;
    }

    public void setKhlx(int khlx) {
        this.khlx = khlx;
    }

    public Date getKssj() {
        return kssj;
    }

    public void setKssj(Date kssj) {
        this.kssj = kssj;
    }

    public Date getJssj() {
        return jssj;
    }

    public void setJssj(Date jssj) {
        this.jssj = jssj;
    }
}
