package boot.entities;


import java.util.Objects;

/**Класс представляет запись в телефонной книжке.
 @author Артемьев Р.А.
 @version 02.12.2019 */
public class PhoneRecord
{
    /**id записи*/
    private Long phoneRecordId;

    /**Имя*/
    private String name;

    /**Телефон*/
    private String phoneNumber;

    public PhoneRecord(String name, String phoneNumber) {
        this.name = name;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public Long getPhoneRecordId() {
        return phoneRecordId;
    }

    public void setPhoneRecordId(Long phoneRecordId) {
        this.phoneRecordId = phoneRecordId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneRecord)) return false;
        PhoneRecord that = (PhoneRecord) o;
        return Objects.equals(phoneRecordId, that.phoneRecordId) &&
                Objects.equals(name, that.name) &&
                Objects.equals(phoneNumber, that.phoneNumber);
    }

    @Override
    public int hashCode() {
        return Objects.hash(phoneRecordId, name, phoneNumber);
    }

    @Override
    public String toString() {
        return "PhoneRecord{" +
                "phoneRecordId=" + phoneRecordId +
                ", name='" + name + '\'' +
                ", phoneNumber=" + phoneNumber +
                '}';
    }
}
