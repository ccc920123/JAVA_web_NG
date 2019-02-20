package com.scxd.beans.database;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scxd.toolkit.CustomDateSerializer;

import java.util.Date;

public class SysJobRank {
    private String pmmc;
    private String pmqy;
    private String kssj;
    private String jssj;
    private String cjsj;
    private Long state;
    private String id;
    private String pmsp;
    private String qyname;//排名区域名称
    private int khlx;

    public int getKhlx() {
        return khlx;
    }

    public void setKhlx(int khlx) {
        this.khlx = khlx;
    }

    public String getPmmc() {
        return pmmc;
    }

    public void setPmmc(String pmmc) {
        this.pmmc = pmmc;
    }

    public String getPmqy() {
        return pmqy;
    }

    public void setPmqy(String pmqy) {
        this.pmqy = pmqy;
    }

    public String getKssj() {
        return kssj;
    }

    public void setKssj(String kssj) {
        this.kssj = kssj;
    }

    public String getJssj() {
        return jssj;
    }

    public void setJssj(String jssj) {
        this.jssj = jssj;
    }

    public String getCjsj() {
        return cjsj;
    }

    public void setCjsj(String cjsj) {
        this.cjsj = cjsj;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPmsp() {
        return pmsp;
    }

    public void setPmsp(String pmsp) {
        this.pmsp = pmsp;
    }

    public String getQyname() {
        return qyname;
    }

    public void setQyname(String qyname) {
        this.qyname = qyname;
    }
}
