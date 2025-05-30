package Server.Model;

import Server.FileMennager.Adapters.DateAdapter;
import Server.FileMennager.Adapters.FloatAdapter;
import Server.FileMennager.Adapters.LongAdapter;

import java.time.LocalDate;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Organization - класс, предназначенный для описания организации\n
 * Имеет 8 полей - Long id, String name, Coordinates coordinates, java.time.LocalDate creationDate, Double annualTurnover, String fullName, OrganizationType type, Addres officialAddress.\n
 * Этот класс подготовлен к записи в xml
 */

@XmlRootElement(name = "organization")
@XmlAccessorType(XmlAccessType.FIELD)
public class Organization extends Model implements Comparable<Organization>{
    @XmlTransient
    private boolean isIDSet = false;

    @XmlJavaTypeAdapter(LongAdapter.class)
    private Long id;

    @XmlElement(name = "name")
    private String name;

    @XmlElement(name = "coordinates")
    private Coordinates coordinates;

    @XmlJavaTypeAdapter(DateAdapter.class)
    private LocalDate creationDate;

    @XmlJavaTypeAdapter(FloatAdapter.class)
    private Float annualTurnover;

    @XmlElement(name = "fullName")
    private String fullName;

    @XmlElement(name = "type")
    private OrganizationType type;

    @XmlElement(name = "officialAddress")
    private Addres officialAddress;

    public Organization() {}

    /**
     * setId - метод, созданный для задания ID единожды
     * @param id - ID организации
     */
    public void setId(Long id) {
        if (!this.isIDSet) {
            this.id = id;
            this.isIDSet = true;
        }
    }

    /**
     * setName - метод, предназначенный для задания названия организации\n
     * @param name - название организации (строка)
     */
    public void setName(String name) throws IllegalArgumentException{
        if (name == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        else if (name.isEmpty()) {throw new IllegalArgumentException("Строка не должна быть пустой");}
        this.name = name;
    }

    /**
     * setCoordinates - метод, предназначенный для автоматического задания названия организации\n
     * @param coordinates - координаты организации (объект класса Coordinates)
     */
    public void setCoordinates(Coordinates coordinates) throws IllegalArgumentException{
        if (coordinates == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        this.coordinates = coordinates;
    }

    /**
     * setCreationDate - метод, предназначенный для задания даты создания компании\n
     * @param creationDate - дата основания (LocalDate)
     */
    public void setCreationDate(LocalDate creationDate) {
        if (creationDate == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        this.creationDate = creationDate;
    }

    /**
     * setAnnulTurnover - метод, предназначенный для задания ежегодного оборота\n
     * @param annualTurnover - ежегодный оборот (Double)
     */
    public void setAnnulTurnover(Float annualTurnover){
        if (annualTurnover == null) {throw new IllegalArgumentException("Поле AnnualTurnover не должно быть пустым");}
        else if (annualTurnover <= 0) {throw new IllegalArgumentException("Значение annualTurnover должно быть положительным");}
        this.annualTurnover = annualTurnover;
    }

    /**
     * setFullName - метод, предназначенный для полного названия компании\n
     * @param fullName - полное название организации (строка)
     */
    public void setFullName(String fullName){
        if (fullName == null) {throw new IllegalArgumentException("Поле FullName не должно быть пустым");}
        else if (fullName.length() > 703) {throw new IllegalArgumentException("Длинна полного названия не должна быть боле 703 символов");}
        this.fullName = fullName;
    }

    /**
     * setType - метод, предназначенный для задания типа организации\n
     * @param type - тип организации (одна из констант OrganizationType)
     */
    public void setType(OrganizationType type){
        if (type == null) {throw new IllegalArgumentException("Поле Type не должно быть пустым");}
        this.type = type;
    }

    /**
     * setType - метод, предназначенный для задания типа организации\n
     * @param officialAddress - официальный адрес (объект класса Addres)
     */
    public void setOfficialAddress(Addres officialAddress){
        if (officialAddress == null) {throw new IllegalArgumentException("Поле OfficialAddress не должно быть пустым");}
        this.officialAddress = officialAddress;
    }

    /**
     * getID - метод, предназначенный для получения ID организации
     * @return - значение ID организации
     */
    public long getID() {return this.id;}

    /**
     * getAnnualTurnover - метод, предназначенный для получения годового оборота организации
     *
     * @return - значение годового оборота организации
     */
    public Float getAnnualTurnover() {return annualTurnover;}

    /**
     * getOfficialAddress - метод, предназначенный для получения данных об официальном адресе организации
     * @return - объект класса, описывающего официальный адрес
     */
    public Addres getOfficialAddress() {return officialAddress;}

    /**
     * getFullName - метод, предназначенный для получения полного названия организации
     * @return - полное название компании
     */
    public String getFullName() {return fullName;}


    /**
     * isAllSet() - метод, предназначенный для проверки
     * @return - заданны ли значения всех полей
     */
    public boolean isAllSet() {
        return  !(this.id == null || this.name == null || this.coordinates.isAllSet() ||
                this.creationDate == null || this.annualTurnover == null || this.fullName == null ||
                this.type == null ||
                this.officialAddress.isAllSet());
    }

    @Override
    public String toString() {
        return "Название: " + this.name + ", Айди: " + this.id + ", Полное название: " + this.fullName + ",\nЕжегодный оборот: " + this.annualTurnover + ", Официальный адрес: " + this.officialAddress + ", Тип организации: " + this.type + ",\nДата создания: " + this.creationDate + ", Координаты: " + this.coordinates.toString();
    }

    @Override
    public int compareTo(Organization o) {
        return o.name.compareTo(this.name);
    }
}