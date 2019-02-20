package com.scxd.dao;

import com.scxd.beans.database.SysJobRank;
import com.scxd.beans.database.SysJobRankConfigBean;
import com.scxd.beans.database.SysJobRankDetailBean;
import com.scxd.beans.database.SysJobScore;
import com.scxd.beans.extendbeans.JobSorceXXTableHeadBean;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 15:14 2018/12/13
 * @Modified By:
 */
@Repository
public interface JobRankConfigDao {
    List<SysJobRankConfigBean> getConfigByPMID(@Param("pmid") String pmid);
    int insertConfigBean(SysJobRankConfigBean sysConfig);
    int insertJobDetailBean(SysJobRankDetailBean sysJobRankDetailBean);

    /**
     * 根据小项删除小项分数
     * @param uuid
     * @return
     */
    int deleteDetail(@Param("uuid")String uuid);

    /**
     * 删除小项
     * @param uuid
     * @return
     */
    int deleteConfig(@Param("uuid")String uuid);

    int changeConfigBean(SysJobRankConfigBean sysConfig);

    int saveSorceTable(@Param("pmid")String uuid);

    int  getSorceTableZF(@Param("pmid")String uuid);

    List<SysJobRankDetailBean> getJobDetailMessage(@Param("pmid")String pmid, @Param("khqyid") String khqyid);

    List<SysJobScore> getXXSorceTable(@Param("pmid")String pmid,@Param("khqyid") String khqyid);

    List<JobSorceXXTableHeadBean> getXXForTableView(@Param("pmid")String id);

    int updateDetailByBean(SysJobRankDetailBean sysJobRankDetailBean);

    int deleteConfigByPmid(@Param("pmid")String id);

    int deleteDetailByPmid(@Param("pmid")String id);
}
