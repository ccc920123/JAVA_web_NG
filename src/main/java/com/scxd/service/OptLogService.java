package com.scxd.service;

import com.scxd.beans.Response;
import com.scxd.beans.database.SysOpLogBean;

import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:37 2018/12/26
 * @Modified By:
 */
public interface OptLogService {
    void saveLog(SysOpLogBean optLog);

    Response getOperationList(Map map);

    Response queryOperationDetail(String id);
}
