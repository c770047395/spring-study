import com.cp.pojo.User;
import com.cp.pojo.UserT;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class MyTest {
    public static void main(String[] args) {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        User user =(User) context.getBean("user");
        user.show();
        UserT userT2 = (UserT) context.getBean("userT2");
        userT2.show();
    }
}
