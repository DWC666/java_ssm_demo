package junit.test;

import com.dwc.crowdfunding.bean.User;
import com.dwc.crowdfunding.manager.service.UserService;
import com.dwc.crowdfunding.util.MD5Util;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Test1 {
    public static void main(String[] args) {
        ApplicationContext ioc = new ClassPathXmlApplicationContext("spring/spring-*.xml");
        UserService userService = ioc.getBean(UserService.class);
        for(int i=0; i<100; i++){
            User user = new User();
            user.setUsername("user" + i);
            user.setEmail("dwc@qq.com");
            user.setLoginacct("user" + i);
            user.setUserpswd(MD5Util.digest("123"));
            user.setCreatetime("2019-12-22 17:03:00");
            userService.saveUser(user);
        }
    }

}
