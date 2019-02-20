package com.scxd.service.impl;

import com.scxd.beans.Response;
import com.scxd.beans.database.AssessCompInfoBean;
import com.scxd.beans.database.AssessInfoBean;
import com.scxd.beans.database.SysArea;
import com.scxd.beans.database.SysJobRank;
import com.scxd.dao.AreaDao;
import com.scxd.dao.CompreAssessDao;
import com.scxd.service.CompreAssessService;
import com.scxd.toolkit.DateUtil;
import com.scxd.toolkit.UtilClass;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.UUID;

/**
 * @Auther:陈攀
 * @Description:
 * @Date:Created in 10:58 2018/12/7
 * @Modified By:
 */
@Service
public class CompreAssessServiceImpl implements CompreAssessService {
    @Autowired
    CompreAssessDao compreAssessDao;
    @Autowired
    AreaDao areadao;

    @Autowired
    private HttpSession session;

    @Override
    public Response saveCompreAssess(AssessCompInfoBean assessCompInfoBean) {

        assessCompInfoBean.setKhqy(new UtilClass().getUserQyid(session));

        String khqy = assessCompInfoBean.getKhqy();

        if (khqy == null || "".equals(khqy)) {
            return new Response().failure("未选择地区");
        }

        SysArea sysArea = areadao.getSysAreaBy(khqy);
        int xzjb = 0;
        if (sysArea != null) {
            xzjb = sysArea.getXzjb();
        } else {
            return new Response().failure("未找到当前行政区域信息");
        }
//        Date dqkhsj = assessCompInfoBean.getDqkhsj();
        int khlx = assessCompInfoBean.getKhlx();
        AssessCompInfoBean compInfoBean = compreAssessDao.justIsExist(khqy, khlx, assessCompInfoBean.getKhkssj(), assessCompInfoBean.getKhjssj());
        if (compInfoBean != null) {
            return new Response().failure("已经存在当前区域的当前考核");
        }
        List<SysJobRank> sysJobRanks = compreAssessDao.justJobRankIsExist(khqy, assessCompInfoBean.getKhkssj(), assessCompInfoBean.getKhjssj());
        if (sysJobRanks == null || sysJobRanks.size() == 0) {
            return new Response().failure("不存在当前区域的工作排名");
        }
        AssessInfoBean assessInfoBean = compreAssessDao.justAssessInfoIsExist(khqy, assessCompInfoBean.getKhkssj(), assessCompInfoBean.getKhjssj());
        if (assessInfoBean == null) {
            return new Response().failure("不存在当前区域的考核排名");
        }
        String jobrankid = sysJobRanks.get(0).getId();
        String assessInfoid = assessInfoBean.getId();
        String uuid = UUID.randomUUID().toString();
        int zhkh = compreAssessDao.getCompreScore(khqy, xzjb, uuid, jobrankid, assessInfoid, assessCompInfoBean.getGzpmzb(), assessCompInfoBean.getNgkhzb());
        if (zhkh > 0) {
            assessCompInfoBean.setId(uuid);
            String bt = "";
            String khsj = DateUtil.getStringYYYYMMDDByDate(assessCompInfoBean.getKhkssj()) + "~" + DateUtil.getStringYYYYMMDDByDate(assessCompInfoBean.getKhjssj());
            assessCompInfoBean.setKhsj(khsj);
            String khlxStr = "月度";
            bt = sysArea.getQyname() + khlxStr + "工作综合考核(" + (DateUtil.getYYMM(assessCompInfoBean.getKhkssj())) + ")";
            if (khlx == 2) {
                khlxStr = "季度";
                bt = sysArea.getQyname() + khlxStr + "工作综合考核(" + (DateUtil.getYYMM(assessCompInfoBean.getKhkssj())) + "-" + (DateUtil.getYYMM2(assessCompInfoBean.getKhjssj())) + ")";
            } else if (khlx == 3) {
                khlxStr = "年度";
                bt = sysArea.getQyname() + khlxStr + "工作综合考核(" + (DateUtil.getYY(assessCompInfoBean.getKhkssj())) + ")";
            }
//            String bt = sysArea.getQyname() + khlxStr + "工作综合考核(" + (DateUtil.getYYMM(assessCompInfoBean.getKhkssj())) + ")";
            assessCompInfoBean.setBt(bt);
            int khinfo = compreAssessDao.insertInfo(assessCompInfoBean);
            if (khinfo > 0) {
                return new Response().success();
            }
        } else {
            return new Response().failure("综合考核得分失败");
        }
        return new Response().failure("综合考核失败");
    }
}
