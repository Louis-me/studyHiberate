import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import xyz.shi.dao.StudentDao;
import xyz.shi.domain.Grade;
import xyz.shi.domain.Student;
import xyz.shi.domain.Course;
import xyz.shi.entity.QueryResult;
import xyz.shi.utils.HibernateUtils;

import java.util.List;
import java.util.Set;

public class StudentDao2Test {

    @Test
    public void saveTest() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        //新建学生和班级信息
        Grade grade = new Grade();
        grade.setName("三年级");
        Student student = new Student();
        student.setName("选课学生1");
        student.setGrade(grade);
        Student student2 = new Student();
        student2.setName("选课学生2");
        student2.setGrade(grade);
        grade.getStudents().add(student);
        grade.getStudents().add(student2);

        //新建三个课程
        Course course = new Course();
        course.setName("Java");
        Course course2 = new Course();
        course2.setName("PHP");
        Course course3 = new Course();
        course3.setName("C++");



        //保存操作
        session.save(student);
        session.save(student2);
        session.save(grade);
        session.save(course);
        session.save(course2);
        session.save(course3);

        //学生选课的关系相互关联
        course.getStudents().add(student);
        student.getCourses().add(course);

        course2.getStudents().add(student2);
        student2.getCourses().add(course2);

        course3.getStudents().add(student2);
        student2.getCourses().add(course3);

        //提交事务
        transaction.commit();
        //释放资源
        session.close();

    }
    @Test
    public void findIdTest() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
        String hql = "SELECT s, c, g FROM Student s " +
                "JOIN s.courses c " +
                "JOIN s.grade g where s.name = :name";
        Query query = session.createQuery(hql);
        query.setParameter("name", "选课学生2");

        List<Object[]>  students = query.list();
        for (Object[] result : students) {
            Student student = (Student) result[0];
            Course course = (Course) result[1];
            Grade grade = (Grade) result[2];
            System.out.println(student);
            System.out.println(course);
            System.out.println(grade);
        }

        //提交事务
        transaction.commit();
        //释放资源
        session.close();

    }

    // 修改操作
    @Test
    public void updateTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        Transaction tx = session.getTransaction();
        if (!tx.isActive()) {
            tx.begin();
        }

        Student student = session.get(Student.class, 42);
        student.setName("我是谁1");
        // 设置学生的班级
        Grade grade = session.get(Grade.class, 2);
        student.setGrade(grade);

        Course course = session.get(Course.class, 33);

        // 先移除旧关联关系
        for (Course existingCourse : student.getCourses()) {
            existingCourse.getStudents().remove(student);
        }

        // 添加新关联关系
        student.getCourses().clear();
        student.getCourses().add(course);
        course.getStudents().add(student);


        // 保存实体对象到数据库中
        session.saveOrUpdate(student);
        session.saveOrUpdate(course);

        if (tx.isActive()) {
            tx.commit();
            session.close();
            sessionFactory.close();
        }
    }

    // 删除操作---根据id进行删除
    @Test
    public void deleteTest() {
        Configuration config = new Configuration().configure(); // Hibernate框架加载hibernate.cfg.xml文件
        SessionFactory sessionFactory = config.buildSessionFactory();
        Session session = sessionFactory.openSession(); // 相当于得到一个Connection
        Transaction tx = session.getTransaction();
        if (!tx.isActive()) {
            tx.begin();
        }


        Student student = session.get(Student.class, 40);
        Course course = session.get(Course.class, 31);

        // 移除关联关系
        student.getCourses().remove(course);
        course.getStudents().remove(student);
        // 删除学生信息
        session.delete(student);

        if (tx.isActive()) {
            tx.commit();
            session.close();
            sessionFactory.close();
        }
    }

    // 查询所有
    @Test
    public void findAllTest() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();
        // 根据业务来编写代码
        String hql = "SELECT s, c, g FROM Student s " +
                "JOIN s.courses c " +
                "JOIN s.grade g";
        Query query = session.createQuery(hql);
        List<Object[]>  students = query.list();
        for (Object[] result : students) {
            Student student = (Student) result[0];
            Course course = (Course) result[1];
            Grade grade = (Grade) result[2];
            System.out.println(student);
            System.out.println(course);
            System.out.println(grade);
        }
        //提交事务
        transaction.commit();
        //释放资源
        session.close();

    }
    @Test
    public void findAll1() {
        Session session = HibernateUtils.openSession();
        Transaction transaction = session.beginTransaction();

        String hql = "SELECT s, c, g FROM Student s " +
                "JOIN s.courses c " +
                "JOIN s.grade g";
        Query query = session.createQuery(hql);
        query.setFirstResult(0);
        query.setMaxResults(3);
        List<Object[]>  students = query.list();
        for (Object[] result : students) {
            Student student = (Student) result[0];
            Course course = (Course) result[1];
            Grade grade = (Grade) result[2];
            System.out.println(student);
            System.out.println(course);
            System.out.println(grade);
        }

        //提交事务
        transaction.commit();
        //释放资源
        session.close();

    }
}
