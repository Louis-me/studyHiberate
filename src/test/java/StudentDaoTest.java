import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import xyz.shi.dao.StudentDao;
import xyz.shi.domain.Student;
import xyz.shi.domain.Grade;
import xyz.shi.entity.QueryResult;

import java.util.List;

public class StudentDaoTest {
    private  StudentDao studentDao = new StudentDao();

    @Test
    public void saveTest() {
        Grade grade = new Grade();
        grade.setName("三年级");

        Student student = new Student();
        student.setName("王大伟");
        //设置学生的班级
        student.setGrade(grade);

        Student student2 = new Student();
        student2.setGrade(grade);
        student2.setName("小红");

        studentDao.save(student);
        studentDao.save(student2);
    }
    @Test
    public void findIdTest() {
       List<Object[]> students = studentDao.getById(12);
        for (Object[] result : students) {
            Student student = (Student) result[0];
            Grade grade = (Grade) result[1];
            System.out.println(student);
            System.out.println(grade);
        }
    }

    // 修改操作
    @Test
    public void updateTest() {
        Student student = new Student();
        student.setId(3);
        student.setName("郑122");
        studentDao.update(student);


    }

    // 删除操作---根据id进行删除
    @Test
    public void deleteTest() {
        studentDao.delete(2);
    }

    // 查询所有
    @Test
    public void findAllTest() {

        List<Object[]> list = studentDao.findAll();
        for (Object[] result : list) {
            int studentId = (int) result[0];
            String studentName = (String) result[1];
            int gradeId = (int) result[2];
            String gradeName = (String) result[3];

            Student student = new Student();
            student.setId(studentId);
            student.setName(studentName);

            Grade grade = new Grade();
            grade.setId(gradeId);
            grade.setName(gradeName);

            student.setGrade(grade);
            System.out.println(student);
            System.out.println(grade);

        }

    }
    @Test
    public void findAll1() {
        QueryResult result = studentDao.findAll(0, 5);
        System.out.println(result.getCount());
        List<Object[]> list  = result.getList();
        for (Object[] arr : list) {
            Student student = (Student) arr[0];
            System.out.println("Student: " + student.getName() + ", Grade: " + student.getGrade().getName());

        }

    }
}
