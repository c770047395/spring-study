import com.cp.dao.UserMapper;
import com.cp.pojo.User;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

public class MyTest {
    @Test
    public void test1(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        List<User> users = userMapper.selectUser();
        for (User user : users) {
            System.out.println(user);
        }
    }

    @Test
    public void addUser(){
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        UserMapper userMapper = context.getBean("userMapper", UserMapper.class);
        userMapper.addUser(new User(8,"1000","123"));
        userMapper.deleteUser(8);
    }
}
