package com.scxd.beans.extendbeans;

import com.scxd.beans.database.SysDtgl;

import java.util.List;

/**
 * 动态信息页面展示
 * 由于页面发布区域要找到上级区域
 */
public class DtInfo extends SysDtgl {
    private List<String> qyname;

    public List<String> getQyname() {
        return qyname;
    }

    public void setQyname(List<String> qyname) {
        this.qyname = qyname;
    }
}
