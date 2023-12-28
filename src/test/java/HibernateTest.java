
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import xyz.shi.domain.User;
import java.util.List;

public class HibernateTest {

    // 保存一个User
    @Test
    public void saveUserTest() {
        // 创建一个User
        User c = new User();
        c.setName("叶子");
        c.setPassword("1111");

        // 使用Hibernate的API来完成将User信息保存到mysql数据库中的操作
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();
        // 操作
        session.save(c);
        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 根据id查询一个User对象
    @Test
    public void findUserByIdTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();

        // 根据业务来编写代码
        User c = session.get(User.class, 10);
//        User c = session.load(User.class, "62");

        System.out.println(c.getName());
        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 修改操作
    @Test
    public void updateUserTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();

        // 根据业务来编写代码
        User c = session.get(User.class, "62");
        c.setName("郑敏");
        session.update(c); // 修改操作

        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 删除操作---根据id进行删除
    @Test
    public void deleteUserTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();

        // 根据业务来编写代码
        User c = session.get(User.class, "62");
        session.delete(c); // 删除操作

        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

    // 查询所有User
    @Test
    public void findAllUserTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        // 开启事务
        session.beginTransaction();
        // 根据业务来编写代码
        Query query = session.createQuery("from User"); // HQL语句，它类似于SQL语句
        List<User> list = query.list();
        for (User user : list) {
            System.out.println(user);
        }
        // 事务提交
        session.getTransaction().commit();
        session.close();
        sessionFactory.close();
    }

}

