package com.scxd.beans.database;

import java.util.Date;

/**
 * 会议管理的参会单位
 */
public class NetRuralDtglHuiyDanw {
    private String bm;
    private String pbm;
    private String danwCode;
    private String sfdm;
    private String csbj;
    private Date gxsj;
    private Long isdel;
    private Date deltime;
    private String bbh;

    public String getBm() {
        return bm;
    }

    public void setBm(String bm) {
        this.bm = bm;
    }

    public String getPbm() {
        return pbm;
    }

    public void setPbm(String pbm) {
        this.pbm = pbm;
    }

    public String getDanwCode() {
        return danwCode;
    }

    public void setDanwCode(String danwCode) {
        this.danwCode = danwCode;
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

    public Long getIsdel() {
        return isdel;
    }

    public void setIsdel(Long isdel) {
        this.isdel = isdel;
    }

    public String getBbh() {
        return bbh;
    }

    public void setBbh(String bbh) {
        this.bbh = bbh;
    }

    public Date getGxsj() {
        return gxsj;
    }

    public void setGxsj(Date gxsj) {
        this.gxsj = gxsj;
    }

    public Date getDeltime() {
        return deltime;
    }

    public void setDeltime(Date deltime) {
        this.deltime = deltime;
    }

}
