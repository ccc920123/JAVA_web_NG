package com.scxd.beans.database;

public class SysMenu {
    private long id;
    private String name;
    private Long pId;
    private Long mlevel;
    private Long state;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getpId() {
        return pId;
    }

    public void setpId(Long pId) {
        this.pId = pId;
    }

    public Long getMlevel() {
        return mlevel;
    }

    public void setMlevel(Long mlevel) {
        this.mlevel = mlevel;
    }

    public Long getState() {
        return state;
    }

    public void setState(Long state) {
        this.state = state;
    }
}
