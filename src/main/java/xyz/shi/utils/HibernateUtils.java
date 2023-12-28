package xyz.shi.utils;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtils {

    // SessionFactory全局只需要有一个就可以了，因为它的创建和销毁需要消耗大量的资源，初始化信息会比较多，并且它是线程安全的，可以在多线程的环境下使用它
    private static SessionFactory sessionFactory;

    static {
        // 初始化SessionFactory方式一：
        /*
        Configuration cfg = new Configuration(); // 代表配置文件的一个对象
        cfg.configure(); // 读取默认的配置文件(hibernate.cfg.xml)
        // cfg.configure("hibernate.cfg.xml"); // 读取指定位置的配置文件
        sessionFactory = cfg.buildSessionFactory();
        */

        // 初始化SessionFactory方式二：
        sessionFactory = new Configuration() //
                .configure() //
                .buildSessionFactory(); // 方法链
    }

    /**
     * 获取全局唯一的SessionFactory
     *
     * @return
     */
    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * 从全局唯一的SessionFactory中打开一个Session
     *
     * @return
     */
    public static Session openSession() {
        return sessionFactory.openSession();
    }

}
