import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.junit.Test;
import xyz.shi.dao.PersonDao;
import xyz.shi.domain.Person;
import xyz.shi.domain.IdCard;
import xyz.shi.entity.QueryResult;

import java.util.List;

public class PersonDaoTest {
   private PersonDao personDao = new PersonDao();

    @Test
    public void saveTest() {
        // 先创建idcard
        IdCard idCard = new IdCard();
        idCard.setCardNo("1111111");

        // 创建persion
        Person person = new Person();
        person.setName("王大伟");

        person.setIdCard(idCard);
        personDao.save(person);
    }
    @Test
    public void findIdTest() {
        personDao.getById(16);
    }

    // 修改操作
    @Test
    public void updateTest() {

        Person person = new Person();
        person.setId(16);
        person.setName("郑1");
        personDao.update(person);

    }

    // 删除操作---根据id进行删除
    @Test
    public void deleteTest() {
        personDao.delete(19);
    }

    // 查询所有User
    @Test
    public void findAllTest() {
        List<Person> list = personDao.findAll();
        for (Person user : list) {
            System.out.println(user);
        }

    }
    @Test
    public void findAll1() {
        QueryResult result = personDao.findAll(0, 5);
        System.out.println(result.getCount());
        for (Object o : result.getList()) {
            System.out.println(o);
        }
    }
}
