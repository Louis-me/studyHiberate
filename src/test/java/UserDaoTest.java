
import org.junit.Test;
import xyz.shi.dao.UserDao;
import xyz.shi.domain.User;
import xyz.shi.entity.QueryResult;

import java.util.List;

public class UserDaoTest {

    private UserDao userDao = new UserDao();

    @Test
    public void save() {
        User user = new User();
        user.setName("2222");
        user.setPassword("11");
        userDao.save(user);
    }

    @Test
    public void update() {
        User byId = userDao.getById(10);
        byId.setName("456");
        userDao.update(byId);
    }

    @Test
    public void delete() {
        userDao.delete(3);
    }

    @Test
    public void getById() {
        System.out.println(userDao.getById(10));
    }

    @Test
    public void findAll() {
        List<User> list = userDao.findAll();
        for (User user : list) {
            System.out.println(user);
        }
    }

    @Test
    public void findAll1() {
        QueryResult result = userDao.findAll(0, 5);
        System.out.println(result.getCount());
        for (Object o : result.getList()) {
            System.out.println(o);
        }
    }
}
