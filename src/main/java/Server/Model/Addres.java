package Server.Model;

import javax.xml.bind.annotation.*;

/**
 * Addres - класс, служащий для хранения адреса\n
 * Имеет 3 поля - String street, String zipCode, Location town (Объект класса)
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Addres extends Model{
    @XmlElement(name = "street")
    private String street;

    @XmlElement(name = "zipCode")
    private String zipCode;

    @XmlElement(name = "town")
    private Location town;

    /**
     * Addres (String street, String zipCode, Location town) - конструктор класса
     * @param street - Название улицы (строка)
     * @param zipCode - Код (строка)
     * @param town - указание локации (объект класса Location)
     */
    public Addres(String street, String zipCode, Location town){
        setStreet(street);
        setZipCode(zipCode);
        setTown(town);
    }

    /**
     *  Addres() - конструктор класса, предназначенный дял задания пустого экземпляра, заполняемого в последующем при помощи setter-ов
     */
    public Addres() {}

    /**
     * setStreet - метод для задания значения улицы\n
     * @param str - Название улицы (строка)
     */
    public void setStreet(String str) throws IndexOutOfBoundsException{
        if (str == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        else if (str.isEmpty()) {throw new IllegalArgumentException("Строка не должна быть пустой");}
        this.street = str;
    }

    /**
     * setZipCode - метод для задания значения кода адреса\n
     * @param code - Код (строка)
     */
    public void setZipCode(String code) throws IndexOutOfBoundsException{
        if (code == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        else if (code.isEmpty()) {throw new IllegalArgumentException("Строка не должна быть пустой");}
        this.zipCode = code;
    }

    /**
     * setTown - метод для задания улицы\n
     * @param loc - указание локации (объект класса Location)
     */
    public void setTown(Location loc) throws IndexOutOfBoundsException{
        if (loc == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        this.town = loc;
    }

    /**
     * isAllSet() - метод, предназначенный для проверки
     * @return - заданны ли значения всех полей
     */
    public boolean isAllSet() {
        return this.zipCode == null || this.street == null || this.town.isAllSet();
    }

    @Override
    public String toString() {
        return "Улица: " + this.street +", зип-код: " + this.zipCode + "\n" + this.town.toString();
    }
}
