package boot.entities;



import java.util.Objects;

/**Класс представляет пользователя, владельца телефонной книжки.
 @author Артемьев Р.А.
 @version 02.12.2019 */
public class User
{
    /**Счётчик для получения уникальных id номеров новыми пользователями.*/
     /*Он применяется поскольку задание не предусматривает использование
     * баз данных с характерными для них механизмами присвоения уникальных id*/
    private static Long counter = 0L;

    /**id пользователя*/
    private Long userId;

    /**Имя пользователя*/
    private String userName;

    /**Телефонная книга, владельцем которой является пользователь*/
    private PhoneBook phoneBook;

    public User(String userName) {
        this.userId = ++User.counter;
        this.userName = userName;
        this.phoneBook = new PhoneBook();
    }

    public User() {
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public PhoneBook getPhoneBook() {
        return phoneBook;
    }

    public void setPhoneBook(PhoneBook phoneBook) {
        this.phoneBook = phoneBook;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(userId, user.userId) &&
                Objects.equals(userName, user.userName) &&
                Objects.equals(phoneBook, user.phoneBook);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, userName, phoneBook);
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", phoneBook=" + phoneBook +
                '}';
    }
}
