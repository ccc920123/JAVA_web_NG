package com.scxd.beans.database;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.scxd.toolkit.CustomDateDDSerializer;
import com.scxd.toolkit.CustomDateSerializer;

import java.util.Date;

/**
 * 动态管理
 */
public class SysDtgl {
    private String id;
    private String fbqyid;
    private String fbwz;
    private String bt;
    private String ngr;
    private Date fbsj;
    private Long shzt;
    private String lyqyid;
    private String btsp;
    private String dtlx;
    private String dtnr;
    private int tjcj;
    private  int istop;
    private  String topsort;
    private Date topdate;
    private String firstpic;
    private Long tszt;
    private String bz;

    public String getBz() {
        return bz;
    }

    public void setBz(String bz) {
        this.bz = bz;
    }

    public Long getTszt() {
        return tszt;
    }

    public void setTszt(Long tszt) {
        this.tszt = tszt;
    }

    public int getIstop() {
        return istop;
    }

    public void setIstop(int istop) {
        this.istop = istop;
    }

    public String getTopsort() {
        return topsort;
    }

    public void setTopsort(String topsort) {
        this.topsort = topsort;
    }
    @JsonSerialize(using = CustomDateDDSerializer.class)
    public Date getTopdate() {
        return topdate;
    }

    public void setTopdate(Date topdate) {
        this.topdate = topdate;
    }

    public String getFirstpic() {
        return firstpic;
    }

    public void setFirstpic(String firstpic) {
        this.firstpic = firstpic;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFbqyid() {
        return fbqyid;
    }

    public void setFbqyid(String fbqyid) {
        this.fbqyid = fbqyid;
    }

    public String getFbwz() {
        return fbwz;
    }

    public void setFbwz(String fbwz) {
        this.fbwz = fbwz;
    }


    public String getBt() {
        return bt;
    }

    public void setBt(String bt) {
        this.bt = bt;
    }

    public String getNgr() {
        return ngr;
    }

    public void setNgr(String ngr) {
        this.ngr = ngr;
    }

    public Long getShzt() {
        return shzt;
    }

    public void setShzt(Long shzt) {
        this.shzt = shzt;
    }

    public String getLyqyid() {
        return lyqyid;
    }

    public void setLyqyid(String lyqyid) {
        this.lyqyid = lyqyid;
    }
    @JsonSerialize(using = CustomDateSerializer.class)
    public Date getFbsj() {
        return fbsj;
    }

    public void setFbsj(Date fbsj) {
        this.fbsj = fbsj;
    }

    public String getBtsp() {
        return btsp;
    }

    public void setBtsp(String btsp) {
        this.btsp = btsp;
    }

    public String getDtlx() {
        return dtlx;
    }

    public void setDtlx(String dtlx) {
        this.dtlx = dtlx;
    }

    public String getDtnr() {
        return dtnr;
    }

    public void setDtnr(String dtnr) {
        this.dtnr = dtnr;
    }

    public int getTjcj() {
        return tjcj;
    }

    public void setTjcj(int tjcj) {
        this.tjcj = tjcj;
    }


}
