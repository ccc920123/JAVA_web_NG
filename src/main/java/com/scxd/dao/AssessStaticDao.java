package com.scxd.dao;

import com.scxd.beans.database.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 9:32 2018/11/23
 * @Modified By:
 */
@Repository
public interface AssessStaticDao {
    List<AssessConfigBean> getAllAssessConfigByCode(String code);

    int addAssessInfo(AssessInfoBean assessInfoBean);

    int addAssessInfoScore(AssessInfoScoreBean assessInfoScoreBean);

    int addAssessInfoDetail(AssessInfoDetailBean infoDetailBean);

    int addAssessLog(AssessLogBean logBean);

    String getQYNAME(@Param("qyid") String qyid);

    int addAssessInfoScores(@Param("khid")String qyid);

    String getInfoId(@Param("qyid")String dw_code,@Param("khlx") int khlx, @Param("khkssj")Date khkssj,@Param("khjssj") Date khjssj);

    int deleteCommonSorceData(@Param("khid")String khid);

    int deleteCommonDetailData(@Param("khid")String khid);

    int deleteCommonInfoData(@Param("khid")String khid);
}
