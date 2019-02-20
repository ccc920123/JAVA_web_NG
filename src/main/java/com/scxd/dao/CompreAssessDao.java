package com.scxd.dao;

import com.scxd.beans.database.AssessCompInfoBean;
import com.scxd.beans.database.AssessInfoBean;
import com.scxd.beans.database.SysJobRank;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:59 2018/12/7
 * @Modified By:
 */
@Repository
public interface CompreAssessDao  {
    AssessCompInfoBean justIsExist(@Param("khqy") String khqy, @Param("khlx")int khlx, @Param("khkssj") Date dqkhsj,  @Param("khjssj")Date dqkhsj1);

    List<SysJobRank> justJobRankIsExist(@Param("khqy")String khqy, @Param("khkssj")Date dqkhsj, @Param("khjssj")Date dqkhsj1);

    AssessInfoBean justAssessInfoIsExist(@Param("khqy")String khqy, @Param("khkssj")Date dqkhsj, @Param("khjssj")Date dqkhsj1);

    /**
     * 获取综合考核并插入得分表
     * @param khqy
     * @param xzjb
     * @param uuid
     * @param jobrankid
     * @param assessInfoid
     * @return
     */
    int getCompreScore(@Param("khqy")String khqy, @Param("xzjb")int xzjb, @Param("uuid")String uuid,
                       @Param("jobrankid") String jobrankid, @Param("assessInfoid")String assessInfoid,
                       @Param("gzzb")int gzzb , @Param("khzb")int khzb);

    int insertInfo(AssessCompInfoBean assessCompInfoBean);
}
