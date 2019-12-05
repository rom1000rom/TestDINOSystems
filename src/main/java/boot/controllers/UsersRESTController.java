package boot.controllers;


import boot.entities.PhoneRecord;
import boot.entities.User;
import boot.exceptions.EntityNotFoundException;
import boot.services.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.Banner;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

/**Класс представляет собой REST-контроллёр, содержащий методы для обработки
 * стандартных Http-запросов в отношении пользователей и их телефонных книжек.
 @author Артемьев Р.А.
 @version 02.12.2019 */
@RestController
@RequestMapping("/users")
public class UsersRESTController
{
    @Autowired
    private UsersService usersService;

    /**Метод обрабатывает GET-запросы и возвращает список всех пользователей.
     * @return список всех пользователей*/
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers()
    {
        List<User> users = usersService.getAllUsers();

        /*Возвращаем ResponseEntity<List<User>>, это более гибкий вариант, чем вернуть
        просто User, поскольку для ResponseEntity можно установить Http-статус ответа –
        ResponseEntity.ok() – это 200 или ResponseEntity.status(201).
        В методе body() передается возвращаемая сущность –  это User (либо список User).
        Автоматически она конвертируется в JSON благодаря тому, что у нас стоит аннотация
        @RestController. Для конвертации  Spring Boot использует библиотеку Jackson
         – она включена благодаря Maven-зависимости spring-boot-starter-web.*/
        return ResponseEntity.ok().body(users);
    }

    /**Метод обрабатывает POST-запросы и добавляет новых пользователей.
     * @param user объект представляющий пользователя которого нужно добавить
     * @return объект представляющий нового пользователя*/
    @PostMapping
    /*@RequestBody говорит, что параметр будет именно в теле запроса*/
    public ResponseEntity<User> addUser(@RequestBody User user)
    {
        usersService.addUser(user);
        return ResponseEntity.status(201).body(user);
    }

    /**Метод обрабатывает GET-запросы и возвращает объект пользователя по его id.
     * @param userId id пользователя
     * @return объект пользователя*/
    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId)
    {
        User user = usersService.getUser(userId);
        if (user == null)
            throw new EntityNotFoundException("Users id-" + userId + " not found.");
        return ResponseEntity.ok().body(user);
    }

    /**Метод обрабатывает DELETE-запросы и удаляет объект пользователя по его id.
     * @param userId id пользователя который будет удалён
     * @return объект удалённого пользователя или null, если в параметре null,
     * или пользователя с таким id не существует*/
    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Long userId)
    {
        User user = usersService.getUser(userId);
        if (user == null)
            throw new EntityNotFoundException("Users id-" + userId + " not found.");

        usersService.deleteUser(userId);
        return ResponseEntity.ok().body(user);
    }

    /**Метод обрабатывает PUT-запросы и редактирует объект пользователя.
     * @param user объект пользователя который будет отредактирован
     * @return объект отредактированного пользователя или null, если в параметре null,
     * или пользователя с таким id не существует*/
    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user)
    {
        Long id = usersService.updateUser(user);
        if (id == null)
            throw new EntityNotFoundException("Users id-" + id + " not found.");

        return ResponseEntity.ok().body(user);
    }

    /**Метод обрабатывает GET-запросы и возвращает список объектов пользователей
     *  по части их имени.
     * @param partName часть имени пользователя.
     * @return список объектов пользователей.*/
    @GetMapping("name")
    public ResponseEntity<List<User>> getUsersByName(@RequestParam String partName)
    {
        List<User> users = usersService.getUsersByName(partName);

        return ResponseEntity.ok().body(users);
    }

    /**Метод обрабатывает GET-запросы и возвращает список всех записей
     * в телефонной книжке пользователя.
     * @param userId id пользователя записи в телефонной книжке которого нужно получить.
     * @return список всех записей*/
    @GetMapping("phoneRecords/{userId}")
    public ResponseEntity<List<PhoneRecord>> getUserAllPhoneRecords(@PathVariable("userId") Long userId)
    {
        List<PhoneRecord> records = usersService.getUserAllPhoneRecords(userId);

        return ResponseEntity.ok().body(records);
    }

    /**Метод обрабатывает POST-запросы и добавляет новые записи в телефонную книжку пользователя.
     * @param userId id пользователя, запись в телефонную книжку которого нужно добавить.
     * @param phoneRecord объект записи которую нужно добавить.
     * @return объект представляющий новую запись в телефонной книжке.*/
    @PostMapping("phoneRecord/{userId}")
    /*@RequestBody говорит, что параметр будет именно в теле запроса*/
    public ResponseEntity<PhoneRecord> addUserPhoneRecord(@PathVariable("userId") Long userId,
                                               @RequestBody PhoneRecord phoneRecord)
    {
        Long id = usersService.addUserPhoneRecord(userId, phoneRecord);
        phoneRecord.setPhoneRecordId(id);
        return ResponseEntity.status(201).body(phoneRecord);
    }

    /**Метод обрабатывает GET-запросы и возвращает запись по её id
     * в телефонной книжке пользователя.
     * @param userId id пользователя, запись в телефонной книжке которого нужно получить.
     * @param id id записи которую нужно получить.
     * @return объект представляющий запись.*/
    @GetMapping("phoneRecord/{userId}")
    public ResponseEntity<PhoneRecord> getUserPhoneRecord(@PathVariable("userId") Long userId,
                                                          @RequestParam Long id)
    {
        PhoneRecord record = usersService.getUserPhoneRecord(userId, id);
        if (record == null)
            throw new EntityNotFoundException("Records id-" + id + " not found.");
        return ResponseEntity.ok().body(record);
    }

    /**Метод обрабатывает DELETE-запросы и удаляет запись по её id
     в телефонной книжке пользователя.
     @param userId id пользователя, у которого нужно удалить запись.
     @param id id записи которую нужно удалить.
     @return объект представляющий удалённую запись.*/
    @DeleteMapping("phoneRecord/{userId}")
    public ResponseEntity<PhoneRecord> deleteUserPhoneRecord(@PathVariable("userId") Long userId,
                                                          @RequestParam Long id)
    {
        PhoneRecord record =  usersService.getUserPhoneRecord(userId, id);
        if (record == null)
            throw new EntityNotFoundException("Records id-" + id + " not found.");
        usersService.deleteUserPhoneRecord(userId, id);
        return ResponseEntity.ok().body(record);
    }

    /**Метод обрабатывает PUT-запросы и редактирует  запись в телефонной книжке пользователя.
     *@param userId id пользователя, у которого нужно отредактировать запись.
     *@param phoneRecord объект записи, которым нужно обновить существующую запись.
     * @return объект отредактированной записи.*/
    @PutMapping("phoneRecord/{userId}")
    public ResponseEntity<PhoneRecord> updateUserPhoneRecord(@PathVariable("userId") Long userId,
                                                      @RequestBody PhoneRecord phoneRecord)
    {
        Long id = usersService.updateUserPhoneRecord(userId, phoneRecord);
        if (id == null)
            throw new EntityNotFoundException("Records id-" + id + " not found.");

        return ResponseEntity.ok().body(phoneRecord);
    }

    /**Метод обрабатывает GET-запросы и возвращает список всех записей
     * в телефонной книжке пользователя с указанным номером телефона.
     * @param userId id пользователя, у которого нужно найти записи.
     * @param phoneNumber номер телефона.
     * @return список записей в телефонной книжке пользователя с указанным номером телефона*/
    @GetMapping("phoneRecords/phoneNumber/{userId}")
    public ResponseEntity<List<PhoneRecord>> getUserPhoneRecordsByPhoneNumber(
            @PathVariable("userId") Long userId, @RequestParam String phoneNumber)
    {
        List<PhoneRecord> records = usersService.getUserPhoneRecordsByPhoneNumber(
                userId, phoneNumber);
        if (records == null)
            throw new EntityNotFoundException("Users id-" + userId + " not found.");

        return ResponseEntity.ok().body(records);
    }
}
