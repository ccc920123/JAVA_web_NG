package com.scxd.beans;

import java.util.List;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:51 2018/8/22
 * @Modified By:
 */
public class ListTotal {
    private List list;
    private int total;

    public ListTotal() {
    }

    public ListTotal(List list, int total) {
        this.list = list;
        this.total = total;
    }

    public List getList() {
        return list;
    }

    public void setList(List list) {
        this.list = list;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
