package com.scxd.beans.database;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 14:36 2018/11/22
 * @Modified By:
 */
public class SysFile {
    private String id;
    private String localpath;
    private String path;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLocalpath() {
        return localpath;
    }

    public void setLocalpath(String localpath) {
        this.localpath = localpath;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
