package boot.entities;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**Класс представляет телефонную книжку.
 @author Артемьев Р.А.
 @version 02.12.2019 */
public class PhoneBook
{
    /**Счётчик для получения уникальных(в рамках конкретного экземпляра телефонной книжки)
     * id номеров новыми записями в телефонной книжке.*/
    /*Он применяется поскольку задание не предусматривает использование
     * баз данных с характерными для них механизмами присвоения уникальных id*/
    private Long counter;

    /**Список телефонных записей*/
    private List<PhoneRecord> listPhoneRecords;

    public PhoneBook()
    {
        this.counter = 0L;
        this.listPhoneRecords = new ArrayList<>();
    }

    public Long addPhoneRecord(PhoneRecord newPhoneRecord)
    {
        newPhoneRecord.setPhoneRecordId(++this.counter);
        listPhoneRecords.add(newPhoneRecord);
        return newPhoneRecord.getPhoneRecordId();
    }

    public Long deletePhoneRecord(PhoneRecord delPhoneRecord)
    {
        listPhoneRecords.remove(delPhoneRecord);
        return delPhoneRecord.getPhoneRecordId();
    }

    public Long updatePhoneRecord(PhoneRecord phoneRecord)
    {
        listPhoneRecords.set(listPhoneRecords.indexOf(phoneRecord), phoneRecord);
        return phoneRecord.getPhoneRecordId();
    }

    public List<PhoneRecord> getListPhoneRecords() {
        return listPhoneRecords;
    }

    public void setListPhoneRecords(List<PhoneRecord> listPhoneRecords) {
        this.listPhoneRecords = listPhoneRecords;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PhoneBook)) return false;
        PhoneBook phoneBook = (PhoneBook) o;
        return Objects.equals(listPhoneRecords, phoneBook.listPhoneRecords);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listPhoneRecords);
    }

    @Override
    public String toString() {
        return "PhoneBook{" +
                "counter=" + counter +
                ", listPhoneRecords=" + listPhoneRecords +
                '}';
    }
}
