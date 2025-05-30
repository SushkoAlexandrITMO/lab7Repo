package Server.Model;

import Server.FileMennager.Adapters.IntAdapter;
import Server.FileMennager.Adapters.LongAdapter;

import javax.xml.bind.annotation.*;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 * Coordinates - класс, предназначенный для хранения координат\n
 * Имеет два поля - int x, Float y
 */

@XmlAccessorType(XmlAccessType.FIELD)
public class Coordinates extends Model{
    @XmlJavaTypeAdapter(IntAdapter.class)
    private Integer x;

    @XmlJavaTypeAdapter(LongAdapter.class)
    private Long y;

    /**
     * Coordinates(x, y) - конструктор класса\n
     * @param x - координата x (int)
     * @param y - координата x (Float)
     */
    public Coordinates(int x, Long y){
        setX(x);
        setY(y);
    }

    /**
     *  Coordinates() - конструктор класса, предназначенный дял задания пустого экземпляра, заполняемого в последующем при помощи setter-ов
     */
    public Coordinates() {}

    /**
     * SetX - метод для задания значения x
     * @param nx - координата x (int)
     */
    public void setX(int nx){
        if (nx < -756) {throw new IllegalArgumentException("Значение координаты x должно быть дольше -756");}
        this.x = nx;
    }

    /**
     * SetY - метод для задания значения y
     * @param ny - координата y (Float)
     */
    public void setY(Long ny){
        if (ny == null) {throw new IllegalArgumentException("Поле не должно быть пустым");}
        else if (ny < -927) {throw new IllegalArgumentException("Значение координаты x должно быть дольше -927");}
        this.y = ny;
    }

    /**
     * isAllSet() - метод, предназначенный для проверки
     * @return - заданны ли значения всех полей
     */
    public boolean isAllSet() {
        return this.x == null || this.y == null;
    }

    @Override
    public String toString() {
        return "x: " + String.valueOf(this.x) + ", y: " + String.valueOf(this.y);
    }
}