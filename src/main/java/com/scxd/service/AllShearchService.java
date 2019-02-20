package com.scxd.service;

import com.scxd.beans.Response;

public interface AllShearchService {

    Response getAllData(String qyid,String fbwz,String bt,String tjcj,int pageNo, int pageSize) throws Exception;

}
