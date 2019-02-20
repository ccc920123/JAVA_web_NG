package com.scxd.service;

import com.scxd.beans.Response;
import com.scxd.beans.database.AssessCompInfoBean; /**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:58 2018/12/7
 * @Modified By:
 */
public interface CompreAssessService {
    Response saveCompreAssess(AssessCompInfoBean assessCompInfoBean);
}
