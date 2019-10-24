import com.cp.dao.UserDaoMysqlImpl;
import com.cp.dao.UserDaoOracleImpl;
import com.cp.service.UserService;
import com.cp.service.UserServiceImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {

        //用户实际调用的是业务层，dao层不用接触
//        UserService userService = new UserServiceImpl();
//
//        ((UserServiceImpl) userService).setUserDao(new UserDaoOracleImpl());
//        userService.getUser();
        //获取ApplicationContext,拿到容器
        ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

        //需要什么就get什么
        UserService userService =(UserService) context.getBean("userServiceImpl");
        userService.getUser();
    }
}
