package com.scxd.controller;

import com.scxd.beans.PdaBeans;
import com.scxd.beans.Response;
import com.scxd.toolkit.PdaVerify;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * PDA控制器
 */
@RestController
@RequestMapping("/pda")
public class PdaCtrl {

    /**
     * PDA 用户服务接口
     */
    @RequestMapping(value = "/pda/user")
    public Response pdaUserService(@RequestBody PdaBeans pdaBeans){
        try{
            boolean verify = PdaVerify.verify(pdaBeans);//PDA参数验证

            if (verify){
                switch(pdaBeans.getJkid()){
                    case "Q01":break;
                    case "Q02":break;
                    default:return new Response().failure("接口ID有误或调取路径有误");
                }
            }else {
                return new Response().failure("PDA验证未通过");
            }
        }catch (Exception e){
            e.printStackTrace();
            return new Response().failure("接口抛异常");
        }

        return null;
    }
}
