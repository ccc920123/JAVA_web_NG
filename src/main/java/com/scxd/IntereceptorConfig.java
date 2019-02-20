package com.scxd;


import com.scxd.toolkit.LoginInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ResourceUtils;
import org.springframework.web.servlet.config.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 拦截器配置
 */
@Configuration
//@EnableWebMvc
public class IntereceptorConfig implements WebMvcConfigurer {


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> pathPatterns = new ArrayList<>();
        pathPatterns.add("/area/areaIntro");
        pathPatterns.add("/area/getregion");
        pathPatterns.add("/area/getAreaID");
        pathPatterns.add("/dtgl/pictureNews");
        pathPatterns.add("/dtgl/getNewsByFBWZ");
        pathPatterns.add("/dtgl/newsRank");
        pathPatterns.add("/dtgl/getNews");
        pathPatterns.add("/dtgl/getNewsInfo");
        pathPatterns.add("/dtgl/newsRankMore");
        pathPatterns.add("/job/jobrank");
        pathPatterns.add("/job/rankList");
        pathPatterns.add("/job/rankInfo");
        pathPatterns.add("/user/login");
        pathPatterns.add("/static/**");
        pathPatterns.add("/getPhoto");
        pathPatterns.add("/savePhoto");
        pathPatterns.add("/link/getLink");
        pathPatterns.add("/allshearch/getAll");
        pathPatterns.add("/baseData/getDrvStatisData");
        pathPatterns.add("/baseData/getVehStatisData");
        pathPatterns.add("/baseData/getRoadStatisData");
        pathPatterns.add("/baseData/getTableLYStatisData");
        pathPatterns.add("/baseData/getTableLZStatisData");
        pathPatterns.add("/assess/assessrank");
        pathPatterns.add("/assess/getComplexList");
        pathPatterns.add("/assess/getAssessComplexMessage");
        pathPatterns.add("/assess/getAssessComplexDetail");

        InterceptorRegistration registration = registry.addInterceptor(new LoginInterceptor());
        registration.addPathPatterns("/**");
        registration.excludePathPatterns(pathPatterns);
    }

}
