package Server.Model;

import Server.FileMennager.Adapters.FloatAdapter;
import Server.FileMennager.Adapters.IntAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Location - класс, предназначенный для хранения координат и названия локации\n
 * Имеет поля - int x, long y, String name
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Location extends Model{
    @XmlJavaTypeAdapter(IntAdapter.class)
    private Integer x;

    @XmlJavaTypeAdapter(FloatAdapter.class)
    private Float y;

    @XmlElement(name = "name")
    private String name;

    /**
     * Location(x, y, name) - конструктор класса
     * @param x - координата x (int)
     * @param y - координата y (long)
     * @param name - название локации (строка)
     */
    public Location(int x, Float y, String name){
        setX(x);
        setY(y);
        setName(name);
    }

    /**
     *  Location() - конструктор класса, предназначенный дял задания пустого экземпляра, заполняемого в последующем при помощи setter-ов
     */
    public Location() {}

    /**
     * SetX - метод для задания значения x
     * @param nx - координата x (int)
     */
    public void setX(int nx){
        this.x = nx;
    }

    /**
     * SetY - метод для задания значения y
     * @param ny - координата y (long)
     */
    public void setY(Float ny){
        this.y = ny;
    }

    /**
     * setName - метод для задания названия места
     * @param name - название локации (строка)
     */
    public void setName(String name){
        this.name = name;
    }

    /**
     * isAllSet() - метод, предназначенный для проверки
     * @return - заданны ли значения всех полей
     */
    public boolean isAllSet() {
        return this.x == null || this.y == null || this.name == null;
    }

    @Override
    public String toString() {
        return "Название локации: " + this.name + ", Координата x: " + String.valueOf(this.x) + ", Координата y : " + String.valueOf(this.y);
    }
}
