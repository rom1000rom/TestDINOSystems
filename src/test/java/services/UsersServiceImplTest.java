package services;


import boot.entities.PhoneRecord;
import boot.entities.User;
import boot.services.UsersService;
import boot.services.UsersServiceImpl;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;

import java.util.ArrayList;
import java.util.List;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNull;


/**Класс содержит юнит-тесты к методам класса UsersServiceImpl
 @author Артемьев Р.А.
 @version 03.12.2019 */
public class UsersServiceImplTest
{
    private UsersService testedObject;

    @Before
    public void setUp() {
        testedObject = new UsersServiceImpl();
    }

    @Test
    public void testGetAllUsers()
    {
        User user1 = new User("TEST1");
        User user2 = new User("TEST2");
        List<User> expected = new ArrayList<>();
        expected.add(user1);
        expected.add(user2);

        testedObject.addUser(user1);
        testedObject.addUser(user2);
        assertEquals(expected, testedObject.getAllUsers());
    }

    @Test
    public void testAddUserIsNull()
    {
        assertNull(testedObject.addUser(null));
    }

    @Test
    public void testAddUser()
    {
        User expected = new User("TEST");

        Long id = testedObject.addUser(expected);
        assertEquals(expected, testedObject.getUser(id));
    }

    @Test
    public void testGetUser()
    {
        User user1 = new User("TEST1");
        User user2 = new User("TEST2");

        testedObject.addUser(user1);
        testedObject.addUser(user2);
        assertEquals(user2, testedObject.getUser(user2.getUserId()));
    }

    @Test
    public void testGetUserNotExist()
    {
        assertNull(testedObject.getUser(3L));
    }

    @Test
    public void testDeleteUser()
    {
        User user1 = new User("TEST1");
        User user2 = new User("TEST2");

        testedObject.addUser(user1);
        testedObject.addUser(user2);
        testedObject.deleteUser(user2.getUserId());

        assertNull(testedObject.getUser(user2.getUserId()));
    }

    @Test
    public void testUpdateUser()
    {
        User expected = new User("TEST1");
        testedObject.addUser(expected);

        expected.setUserName("TEST2");
        testedObject.updateUser(expected);

        assertEquals(expected, testedObject.getUser(expected.getUserId()));
    }

    @Test
    public void testGetUsersByName()
    {
        User user1 = new User("TEST1");
        User user2 = new User("TEST2");
        testedObject.addUser(user1);
        testedObject.addUser(user2);

        assertEquals(user2, testedObject.getUsersByName("T2").get(0));
    }

    @Test
    public void testGetUserAllPhoneRecords()
    {
        User user = new User("TEST1");
        Long id = user.getUserId();
        testedObject.addUser(user);

        PhoneRecord record1 = new PhoneRecord("TEST", "TEST");
        PhoneRecord record2 = new PhoneRecord("TEST2", "TEST2");

        record1.setPhoneRecordId(testedObject.addUserPhoneRecord(id, record1));
        record2.setPhoneRecordId(testedObject.addUserPhoneRecord(id, record2));

        List<PhoneRecord> expected = new ArrayList<>();
        expected.add(record1);
        expected.add(record2);

        assertEquals(expected, testedObject.getUserAllPhoneRecords(id));
    }

    @Test
    public void testGetUserAllPhoneRecordsNotExist()
    {
        assertNull(testedObject.getUserAllPhoneRecords(0L));
    }

    @Test
    public void testAddUserPhoneRecord()
    {
        User user = new User("TEST");
        PhoneRecord expected = new PhoneRecord("TEST", "TEST");

        Long userId = testedObject.addUser(user);
        Long id = testedObject.addUserPhoneRecord(userId, expected);

        assertEquals(expected, testedObject.getUserPhoneRecord(userId, id));
    }

    @Test
    public void testAddUserPhoneRecordNotExist()
    {
        User user = new User("TEST");
        Long userId = testedObject.addUser(user);

        assertNull(testedObject.getUserPhoneRecord(userId, 0L));
    }

    @Test
    public void testGetUserPhoneRecord()
    {
        User user = new User("TEST1");
        Long userId = user.getUserId();
        testedObject.addUser(user);

        PhoneRecord record1 = new PhoneRecord("TEST", "TEST");
        PhoneRecord record2 = new PhoneRecord("TEST2", "TEST2");

        record1.setPhoneRecordId(testedObject.addUserPhoneRecord(userId, record1));
        record2.setPhoneRecordId(testedObject.addUserPhoneRecord(userId, record2));

        assertEquals(record2, testedObject.getUserPhoneRecord(userId, record2.getPhoneRecordId()));
    }

    @Test
    public void testGetUserPhoneRecordNotExist()
    {
        User user = new User("TEST1");
        Long userId = user.getUserId();
        Long id = 0L;
        testedObject.addUser(user);

        assertNull(testedObject.getUserPhoneRecord(userId, id));
    }

    @Test
    public void testDeleteUserPhoneRecord()
    {
        User user = new User("TEST1");
        Long userId = user.getUserId();
        testedObject.addUser(user);

        PhoneRecord record = new PhoneRecord("TEST", "TEST");
        record.setPhoneRecordId(testedObject.addUserPhoneRecord(userId, record));

        assertEquals(record, testedObject.getUserPhoneRecord(userId, record.getPhoneRecordId()));
        testedObject.deleteUserPhoneRecord(userId, record.getPhoneRecordId());
        assertNull(testedObject.getUserPhoneRecord(userId, record.getPhoneRecordId()));
    }

    @Test
    public void testGetUserPhoneRecordsByPhoneNumber()
    {
        User user = new User("TEST1");
        Long userId = user.getUserId();
        testedObject.addUser(user);

        PhoneRecord record1 = new PhoneRecord("TEST", "TEST");
        PhoneRecord record2 = new PhoneRecord("TEST2", "TEST_NUMBER");

        record1.setPhoneRecordId(testedObject.addUserPhoneRecord(userId, record1));
        record2.setPhoneRecordId(testedObject.addUserPhoneRecord(userId, record2));

        List<PhoneRecord> expected = new ArrayList<>();
        expected.add(record2);

        assertEquals(expected, testedObject.getUserPhoneRecordsByPhoneNumber(
                userId, "TEST_NUMBER"));
    }

}
