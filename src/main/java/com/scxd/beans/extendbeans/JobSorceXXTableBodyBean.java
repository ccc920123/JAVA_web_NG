package com.scxd.beans.extendbeans;

import com.scxd.beans.database.SysJobRankDetailBean;

import java.util.List;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 16:55 2018/12/19
 * @Modified By:
 */
public class JobSorceXXTableBodyBean {
    String qyid;
    String qyname;
    List<SysJobRankDetailBean> sysJobRankDetailBeans;
    private float zf;
    private int pm;

    public float getZf() {
        return zf;
    }

    public void setZf(float zf) {
        this.zf = zf;
    }

    public int getPm() {
        return pm;
    }

    public void setPm(int pm) {
        this.pm = pm;
    }

    public String getQyid() {
        return qyid;
    }

    public void setQyid(String qyid) {
        this.qyid = qyid;
    }

    public String getQyname() {
        return qyname;
    }

    public void setQyname(String qyname) {
        this.qyname = qyname;
    }

    public List<SysJobRankDetailBean> getSysJobRankDetailBeans() {
        return sysJobRankDetailBeans;
    }

    public void setSysJobRankDetailBeans(List<SysJobRankDetailBean> sysJobRankDetailBeans) {
        this.sysJobRankDetailBeans = sysJobRankDetailBeans;
    }

}
