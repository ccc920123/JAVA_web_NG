package com.scxd.service;

import com.scxd.beans.Response;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 11:36 2018/10/22
 * @Modified By:
 */
public interface BaseDataService {
    Response getDrvStatisData(String qyid);

    Response getVehStatisData(String qyid);

    Response getRoadStatisData(String qyid);

    Response getTableLZStatisData(String qyid);

    Response getTableLYStatisData(String qyid);
}
