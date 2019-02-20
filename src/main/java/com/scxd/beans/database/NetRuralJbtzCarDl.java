package com.scxd.beans.database;

import java.util.Date;

public class NetRuralJbtzCarDl {
    private String bm;
    private String jdcBm;
    private String dlBm;
    private Date addtime;
    private String adduser;
    private Long isdel;
    private Date deltime;
    private String sfdm;
    private String csbj;
    private Date gxsj;

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getJdcBm() {
        return jdcBm;
    }

    public void setJdcBm(String jdcBm) {
        this.jdcBm = jdcBm;
    }

    public String getDlBm() {
        return dlBm;
    }

    public void setDlBm(String dlBm) {
        this.dlBm = dlBm;
    }

    public String getAdduser() {
        return adduser;
    }

    public void setAdduser(String adduser) {
        this.adduser = adduser;
    }

    public Long getIsdel() {
        return isdel;
    }

    public void setIsdel(Long isdel) {
        this.isdel = isdel;
    }

    public String getSfdm() {
        return sfdm;
    }

    public void setSfdm(String sfdm) {
        this.sfdm = sfdm;
    }

    public String getCsbj() {
        return csbj;
    }

    public void setCsbj(String csbj) {
        this.csbj = csbj;
    }

    public Date getAddtime() {
        return addtime;
    }

    public void setAddtime(Date addtime) {
        this.addtime = addtime;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }
}
