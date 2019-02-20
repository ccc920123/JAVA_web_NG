package com.scxd.beans.database;
import java.util.Date;

public class SysRole {
    private String id;
    private String name;
    private String pmsp;
    private int rolelevel;
    private long state;
    private String czryzh;
    private Date czsj;

    public String getPmsp() {
        return pmsp;
    }

    public void setPmsp(String pmsp) {
        this.pmsp = pmsp;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getRolelevel() {
        return rolelevel;
    }

    public void setRolelevel(int rolelevel) {
        this.rolelevel = rolelevel;
    }

    public long getState() {
        return state;
    }

    public void setState(long state) {
        this.state = state;
    }

    public String getCzryzh() {
        return czryzh;
    }

    public void setCzryzh(String czryzh) {
        this.czryzh = czryzh;
    }

    public Date getCzsj() {
        return czsj;
    }

    public void setCzsj(Date czsj) {
        this.czsj = czsj;
    }
}
