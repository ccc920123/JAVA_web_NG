package com.scxd.usertest;

import com.scxd.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * 用户操作接口的测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {

    @Autowired
    private UserService userService;

    @Test
    public void testUserLogin() {
       try{
           Map map = new HashMap<>();
           map.put("account","tom");
           map.put("password","tom");
           System.out.println( userService.userLogin(map));
       }catch (Exception e){
          e.printStackTrace();
       }
    }

    @Test
    public void testGetArea(){
        try{
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    @Test
    public void testImgPath(){
      String  img = "<p><img src=\"http://localhost:8080/ncweb/getPhoto?id=f4d03354-dfc1-4f02-8999-fc34a05adf3b\" title=\"\" alt=\"timg.jpg\" width=\"615\" height=\"457\"/></p><p>随便加sss</p>";
      int start = img.indexOf("<img");
      int end = img.indexOf(">",start);
      String im = img.substring(start,end - 1);
      System.out.println(start);
      System.out.println(end);
      System.out.println(im);
    }
}
