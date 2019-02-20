package com.scxd.beans;

/**
 * PDA请求后端提交的数据实体类
 */
public class PdaBeans {

    private String jkxlh;//接口序列号

    private String jkid;//接口ID

    private String sjc;//时间戳

    private String user;//用户

    private String wym;//唯一码

    private String querydoc;//查询参数

    private String writedoc;//写入参数

    public String getJkxlh() {
        return jkxlh;
    }

    public void setJkxlh(String jkxlh) {
        this.jkxlh = jkxlh;
    }

    public String getJkid() {
        return jkid;
    }

    public void setJkid(String jkid) {
        this.jkid = jkid;
    }

    public String getSjc() {
        return sjc;
    }

    public void setSjc(String sjc) {
        this.sjc = sjc;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getWym() {
        return wym;
    }

    public void setWym(String wym) {
        this.wym = wym;
    }

    public String getQuerydoc() {
        return querydoc;
    }

    public void setQuerydoc(String querydoc) {
        this.querydoc = querydoc;
    }

    public String getWritedoc() {
        return writedoc;
    }

    public void setWritedoc(String writedoc) {
        this.writedoc = writedoc;
    }
}
