package xyz.shi.dao;

import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import xyz.shi.domain.Grade;
import xyz.shi.domain.Person;
import xyz.shi.domain.Student;
import xyz.shi.entity.QueryResult;
import xyz.shi.utils.HibernateUtils;

import java.util.List;

public class StudentDao {
    public void save(Student student) {
        Session session = HibernateUtils.openSession();
        try {
            Transaction tx = session.beginTransaction(); // 开启事务
            session.save(student);
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
    public void update(Student student) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            Student student1 = session.get(Student.class, student.getId());
            // 设置学生信息
            student1.setName(student.getName());
            // 设置班级
            Grade grade = student1.getGrade();
            student.setGrade(grade);
            //我们修改了该对象的属性，并且不需要显式调用 session.saveOrUpdate(student)，Hibernate 会自动将修改后的对象同步到数据库。
//            session.update(student);// 操作
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

            Object user = session.get(Student.class, id); // 要先获取到这个对象
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
     * 根据id查询，返回对象为List object,然后直接转换到各自的实体类
     */
    public List<Object[]> getById(int id) {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            List<Object[]>  list =  session.createQuery(
                    "select s, g from Student s join s.grade g where s.id="+id).list();
            tx.commit();
            return list;
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
    public List<Object[]> findAll() {
        Session session = HibernateUtils.openSession();
        Transaction tx = null;
        try {
            tx = session.beginTransaction();
            // 注意这里的用法用的原生语句,要给别名不然报错
            List<Object[]> list = session.createSQLQuery("select s.id as student_id, s.name as student_name, g.id as grade_id, g.name as grade_name from student s join grade g on s.gid=g.id").list();
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
        List<Object[]> list = session.createQuery( //
                            "select s, g from Student s join s.grade g") //
                    .setFirstResult(firstResult) //
                    .setMaxResults(maxResults) //
                    .list();

            // 查询总记录数
            // session.createQuery("SELECT COUNT(*) FROM User").list().get(0);
            // Long count = (Long) session.createQuery("SELECT COUNT(*) FROM User").uniqueResult();
            Long count = (Long) session.createQuery( //
                            "SELECT COUNT(*) FROM Student") //
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
