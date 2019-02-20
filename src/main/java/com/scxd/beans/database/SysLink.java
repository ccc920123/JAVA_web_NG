package com.scxd.beans.database;
/*
**友情链接数据
 */
public class SysLink {

    private String id;
    private String bt;
    private String lj;
    private String qyid;

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

    public String getLj() {
        if(lj.contains("http://")||lj.contains("https://"))
        {

            return lj;
        }     else
        {
            return "http://"+lj;
        }

    }

    public void setLj(String lj) {
        this.lj = lj;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }
}
