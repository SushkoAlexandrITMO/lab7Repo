package Server.FileMennager.Adapters;

import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * LongAdapter - класс адаптер из строки в Long для xml файлов
 */
public class LongAdapter extends XmlAdapter<String, Long>{
    /**
     * unmarshal(String string) - метод, парсящий строку в Long
     * @param string - строка, которую необходимо преобразовать
     * @return - число в формате Long
     * @throws Exception - ошибка маршалинга
     */
    @Override
    public Long unmarshal(String string) throws Exception {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("В файле числовое поле пусто");
        }
        try {
            return Long.parseLong(string);
        } catch (Exception e) {
            throw new UnmarshalException("Некорректное значение в поле для Long");
        }
    }

    /**
     * marshal(Long aLong) - метод, парсящий Long в строку
     * @param aLong - число, которое необходимо преобразовать
     * @return - число в формате String
     * @throws Exception - ошибка парсинга
     */
    @Override
    public String marshal(Long aLong) throws Exception {
        return aLong.toString();
    }
}
