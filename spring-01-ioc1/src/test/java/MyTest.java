import com.cp.dao.UserDaoMysqlImpl;
import com.cp.dao.UserDaoOracleImpl;
import com.cp.service.UserService;
import com.cp.service.UserServiceImpl;

public class MyTest {
    public static void main(String[] args) {

        //用户实际调用的是业务层，dao层不用接触
        UserService userService = new UserServiceImpl();

        ((UserServiceImpl) userService).setUserDao(new UserDaoOracleImpl());
        userService.getUser();
    }
}
