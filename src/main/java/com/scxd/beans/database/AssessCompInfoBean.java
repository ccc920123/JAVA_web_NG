package com.scxd.beans.database;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.scxd.toolkit.CustomJsonDateDeserializer;

import java.util.Date;


/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:04 2018/12/7
 * @Modified By:
 */
public class AssessCompInfoBean {
    private String id;
    private String khqy;
    private String khsj;
    private String bt;
    private Date scsj;
    private int khlx;
    private Date khkssj;
    private Date khjssj;
    private int gzpmzb;
    private int ngkhzb;
    private int state;

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }
    //    private  Date dqkhsj;

//    public Date getDqkhsj() {
//        return dqkhsj;
//    }
//
//    public void setDqkhsj(Date dqkhsj) {
//        this.dqkhsj = dqkhsj;
//    }

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

    public Date getKhkssj() {
        return khkssj;
    }
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setKhkssj(Date khkssj) {
        this.khkssj = khkssj;
    }

    public Date getKhjssj() {
        return khjssj;
    }
    @JsonDeserialize(using = CustomJsonDateDeserializer.class)
    public void setKhjssj(Date khjssj) {
        this.khjssj = khjssj;
    }

    public int getGzpmzb() {
        return gzpmzb;
    }

    public void setGzpmzb(int gzpmzb) {
        this.gzpmzb = gzpmzb;
    }

    public int getNgkhzb() {
        return ngkhzb;
    }

    public void setNgkhzb(int ngkhzb) {
        this.ngkhzb = ngkhzb;
    }

   
}
