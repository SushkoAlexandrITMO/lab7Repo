package Server.FileMennager.Adapters;

import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;

/**
 * DateAdapter - класс адаптер из строки в LocalDate для xml файлов
 */
public class DateAdapter extends XmlAdapter<String, LocalDate> {
    /**
     * unmarshal(String string) - метод, парсящий строку в LocalDate
     * @param string - строка, которую необходимо преобразовать
     * @return - время в формате LocalDate
     * @throws Exception - ошибка маршалинга
     */
    @Override
    public LocalDate unmarshal(String string) throws Exception {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("В файле поле для даты пусто");
        }
        try {
            return LocalDate.parse(string);
        } catch (Exception e) {
            throw new UnmarshalException("Некорректное значение в поле для LocalDate");
        }
    }

    /**
     * marshal(LocalDate aDate) - метод, парсящий LocalDate в строку
     * @param aDate - дата в формате LocalDate, которую необходимо преобразовать в строку
     * @return - строчное представление времена из LocalDate
     * @throws Exception - ошибка маршалинга
     */
    @Override
    public String marshal(LocalDate aDate) throws Exception {
        return aDate.toString();
    }
}
