package xyz.shi.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import xyz.shi.domain.Person;
import xyz.shi.entity.QueryResult;
import xyz.shi.utils.HibernateUtils;

import java.util.List;

public class PersonDao {
    /*
     * 保存
     */
    public void save(Person person) {
        Session session = HibernateUtils.openSession();
        try {
            Transaction tx = session.beginTransaction(); // 开启事务
            session.save(person);
            tx.commit(); // 提交事务
        } catch (RuntimeException e) {
            session.getTransaction().rollback(); // 回滚事务
            throw e;
        } finally {
            session.close(); // 关闭session
        }
    }

    /*
     * 更新
     */
    public void update(Person person) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            session.update(person);// 操作

            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
     * 删除
     */
    public void delete(int id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            Object user = session.get(Person.class, id); // 要先获取到这个对象
            session.delete(user); // 删除的是实体对象

            tx.commit();
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
     * 根据id查询一个User数据
     */
    public Person getById(int id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Person person = (Person) session.get(Person.class, id);// 操作
            tx.commit();
            return person;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /*
     * 查询所有
     */
    public List<Person> findAll() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();

            // 方式一：使用HQL语句
            // 这里的FROM User 并不是表的名字，而是User.hbm.xml中的<class name="User" 这个name
            List<Person> list = session.createQuery("FROM Person").list(); // 使用HQL查询

            tx.commit();
            return list;
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }

    /**
     * 分页的查询数据列表
     * @param firstResult 从结果列表中的哪个索引开始取数据
     * @param maxResults 最多取多少条数据
     * @return 一页的数据列表
     */
    @SuppressWarnings("unchecked")
    public QueryResult findAll(int firstResult, int maxResults) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // 查询一页的数据列表
            // 方式一：
            // Query query = session.createQuery("FROM User");
            // query.setFirstResult(firstResult);
            // query.setMaxResults(maxResults);
            // List<User> list = query.list(); // 使用HQL查询

            // 方式二：方法链
            List<Person> list = session.createQuery( //
                            "FROM Person") //
                    .setFirstResult(firstResult) //
                    .setMaxResults(maxResults) //
                    .list();

            // 查询总记录数
            // session.createQuery("SELECT COUNT(*) FROM User").list().get(0);
            // Long count = (Long) session.createQuery("SELECT COUNT(*) FROM User").uniqueResult();
            Long count = (Long) session.createQuery( //
                            "SELECT COUNT(*) FROM Person") //
                    .uniqueResult();
            tx.commit();

            // 返回结果
            return new QueryResult(count.intValue(), list);
        } catch (RuntimeException e) {
            tx.rollback();
            throw e;
        } finally {
            session.close();
        }
    }
}
