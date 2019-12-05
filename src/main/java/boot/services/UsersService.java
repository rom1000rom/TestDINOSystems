package boot.services;


import boot.entities.PhoneRecord;
import boot.entities.User;
import org.springframework.boot.Banner;


import java.util.List;

/**Интерфейс служит для определения серсисных функций по работе с пользователями
 *  и их телефонными книжками.
 @author Артемьев Р.А.
 @version 02.12.2019 */
public interface UsersService
{
    /**Метод возвращает список всех пользователей, экземпляров класса User.
     @return список объектов класса User*/
    List<User> getAllUsers();

    /**Метод добавляет нового пользователя.
     @param newUser объект представляющий пользователя, которого надо добавить
     @return id нового пользователя, или null если в параметре null*/
    Long addUser(User newUser);

    /**Метод возвращает объект класса User по его id.
     @param id id пользователя
     @return объект класса User, или Null если такового нет.*/
     User getUser(Long id);

    /**Метод удаляет объект класса User представляющий пользователя по его id.
     @param id id пользователя
     @return id удалённого пользователя или null, если в параметре null,
     или пользователя с таким id не существует*/
     Long deleteUser(Long id);

    /**Метод редактирует информацию о пользователе.
     * @param user - объект пользователя которым нужно обновить существующий
     * @return id номер отредактированного пользователя или null, если в параметр null
     * или пользователя с таким id не существует*/
     Long updateUser(User user);

    /**Метод возвращает список пользователей по части их имени.
     @param partName часть имени пользователя
     @return список объектов класса User*/
    List<User> getUsersByName(String partName);

    /**Метод для получения списка всех записей в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно получить записи.
     @return список записей, объектов класса PhoneRecord или null если
     пользователя с таким id не существует*/
    List<PhoneRecord> getUserAllPhoneRecords(Long userId);

    /**Метод добавляет новую запись в телефонную книжку пользователя.
     @param userId id пользователя, которому в телефонную книжку
     нужно добавить запись.
     @param newPhoneRecord объект представляющий запись, которую надо добавить в телефонную книжку.
     @return id новой телефонной записи, или null если в параметре null или
     пользователя с таким id не существует*/
    Long addUserPhoneRecord(Long userId, PhoneRecord newPhoneRecord);

    /**Метод для получения записи по её id в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно получить запись.
     @param id id записи которую нужно получить.
     @return запись в телефонной книжке, объект класса PhoneRecord или null если
     пользователя или записи с таким id не существует*/
    PhoneRecord getUserPhoneRecord(Long userId, Long id);

    /**Метод для удаления записи по её id в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно удалить запись.
     @param id id записи которую нужно удалить.
     @return id удалённой записи или null, если  пользователя или
     записи с таким id не существует*/
    Long deleteUserPhoneRecord(Long userId, Long id);

    /**Метод для редактирования записи в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно отредактировать запись.
     @param phoneRecord объект записи, которым нужно обновить существующую запись.
     @return id номер отредактированной записи или null, если пользователя с таким id
     или записи не существует*/
    Long updateUserPhoneRecord(Long userId, PhoneRecord phoneRecord);

    /**Метод для получения записей в телефонной книжке пользователя
     по номеру телефона.
    @param userId id пользователя, у которого нужно найти записи.
    @param phoneNumber номер телефона.
    @return список записей в телефонной книжке пользователя с указанным номером телефона
    или null, если  пользователя с таким id не существует*/
    List<PhoneRecord> getUserPhoneRecordsByPhoneNumber(Long userId, String phoneNumber);
}
