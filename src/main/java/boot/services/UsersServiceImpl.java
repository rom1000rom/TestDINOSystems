package boot.services;


import boot.entities.PhoneBook;
import boot.entities.PhoneRecord;
import boot.entities.User;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


/**Класс-реализация интерфейса UsersService для работы с пользователями
 *  и их телефонными книжками.
 @author Артемьев Р.А.
 @version 02.12.2019 */
@Service
public class UsersServiceImpl implements UsersService
{
    /**Список пользователей (владельцев телефонных книжек)*/
    /*Поскольку задание не предусматривает использования баз данных,
    * DAO-слоя в приложении нет, и данные храняться в оперативной памяти*/
    private List<User> usersList = new ArrayList<>();


    /**Метод возвращает список всех пользователей, экземпляров класса User.
     @return список объектов класса User*/
    @Override
    public List<User> getAllUsers()
    {
        return usersList;
    }

    /**Метод добавляет нового пользователя.
     @param newUser объект представляющий пользователя, которого надо добавить
     @return id нового пользователя, или null если в параметре null*/
    @Override
    public Long addUser(User newUser)
    {
        if(newUser == null)
        {
            return null;
        }
        usersList.add(newUser);
        return newUser.getUserId();
    }

    /**Метод возвращает объект класса User по его id.
     @param id id пользователя
     @return объект класса User, или Null если такового нет.*/
    @Override
    public User getUser(Long id)
    {
        List<User> userList = usersList.stream()//Ищем пользователя с указанным id
            .filter(s -> s.getUserId() == id).collect(Collectors.toList());
        if(userList.size() == 0)//Если пользователь с указанным id не найден
        {
            return null;
        }
        return userList.get(0);
    }

    /**Метод удаляет объект класса User представляющий пользователя по его id.
    @param id id пользователя
    @return id удалённого пользователя или null, если в параметре null,
    или пользователя с таким id не существует*/
    @Override
    public Long deleteUser(Long id)
    {
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == id).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }
        usersList.remove(userList.get(0));
        return id;
    }

    /**Метод редактирует информацию о пользователе.
     * @param user - объект пользователя которым нужно обновить существующий
     * @return id номер отредактированного пользователя или null, если в параметр null
     * или пользователя с таким id не существует*/
    @Override
    public Long updateUser(User user) {
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == user.getUserId()).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }
        usersList.set(usersList.indexOf(userList.get(0)), user);
        return user.getUserId();
    }

    /**Метод возвращает список пользователей по части их имени.
     @param partName часть имени пользователя
     @return список объектов класса User*/
    @Override
    public List<User> getUsersByName(String partName)
    {
        return usersList.stream()
                .filter(s -> s.getUserName().contains(partName)).collect(Collectors.toList());
    }

    /**Метод для получения списка всех записей в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно получить записи.
     @return список записей, объектов класса PhoneRecord, или null
    если пользователя с указанным id не существует*/
    @Override
    public List<PhoneRecord> getUserAllPhoneRecords(Long userId)
    {
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }
        return userList.get(0).getPhoneBook().getListPhoneRecords();
    }

    /**Метод добавляет новую запись в телефонную книжку пользователя.
     @param userId id пользователя, которому в телефонную книжку
     нужно добавить запись.
     @param newPhoneRecord объект представляющий запись, которую надо добавить в телефонную книжку.
     @return id новой телефонной записи, или null если в параметре null или
     пользователя с таким id не существует*/
    @Override
    public Long addUserPhoneRecord(Long userId, PhoneRecord newPhoneRecord)
    {
        if(newPhoneRecord == null)
        {
            return null;
        }
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }
        return userList.get(0).getPhoneBook().addPhoneRecord(newPhoneRecord);
    }

    /**Метод для получения записи по её id в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно получить запись.
     @param id id записи которую нужно получить.
     @return запись в телефонной книжке, объект класса PhoneRecord или null если
     пользователя или записи с таким id не существует*/
    @Override
    public PhoneRecord getUserPhoneRecord(Long userId, Long id)
    {
        List<User> userList = usersList.stream()//Ищем пользователя с указанным id
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)//Если пользователь с указанным id не найден
        {
            return null;
        }
        //Ищем запись с указанным id
        List<PhoneRecord> recordList = userList.get(0).getPhoneBook().getListPhoneRecords()
                .stream().filter(s -> s.getPhoneRecordId() == id).collect(Collectors.toList());
        if(recordList.size() == 0)//Если запись с указанным id не найдена
        {
            return null;
        }

        return recordList.get(0);
    }

    /**Метод для удаления записи по её id в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно удалить запись.
     @param id id записи которую нужно удалить.
     @return id удалённой записи или null, если  пользователя или
     записи с таким id не существует*/
    @Override
    public Long deleteUserPhoneRecord(Long userId, Long id)
    {
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }

        List<PhoneRecord> recordList = userList.get(0).getPhoneBook().getListPhoneRecords()
                .stream().filter(s -> s.getPhoneRecordId() == id).collect(Collectors.toList());
        if(recordList.size() == 0)
        {
            return null;
        }

        return userList.get(0).getPhoneBook().deletePhoneRecord(recordList.get(0));
    }

    /**Метод для редактирования записи в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно отредактировать запись.
     @param phoneRecord объект записи, которым нужно обновить существующую запись.
     @return id номер отредактированной записи или null, если пользователя с таким id
     или записи не существует*/
    @Override
    public Long updateUserPhoneRecord(Long userId, PhoneRecord phoneRecord)
    {
        List<User> userList = usersList.stream()//Ищем пользователя с указанным id
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)//Если пользователь с указанным id не найден
        {
            return null;
        }
        List<PhoneRecord> recordList = userList.get(0).getPhoneBook().getListPhoneRecords()
                .stream().filter(s -> s.getPhoneRecordId() == phoneRecord.getPhoneRecordId())
                .collect(Collectors.toList());
        if(recordList.size() == 0)
        {
            return null;
        }

        return userList.get(0).getPhoneBook().updatePhoneRecord(phoneRecord);
    }

    /**Метод для получения записей в телефонной книжке пользователя
     по номеру телефона.
     @param userId id пользователя, у которого нужно найти записи.
     @param phoneNumber номер телефона.
     @return список записей в телефонной книжке пользователя с указанным номером телефона
     или null, если  пользователя с таким id не существует*/
    @Override
    public List<PhoneRecord> getUserPhoneRecordsByPhoneNumber(Long userId, String phoneNumber)
    {
        List<User> userList = usersList.stream()
                .filter(s -> s.getUserId() == userId).collect(Collectors.toList());
        if(userList.size() == 0)
        {
            return null;
        }
        //Фильтруем все записи в телефонной книжке пользователя по номеру телефона
        return userList.get(0).getPhoneBook().getListPhoneRecords()
                .stream().filter(s -> s.getPhoneNumber().equals(phoneNumber))
                .collect(Collectors.toList());
    }


}
