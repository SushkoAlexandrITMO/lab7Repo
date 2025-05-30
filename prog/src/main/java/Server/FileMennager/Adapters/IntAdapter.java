package Server.FileMennager.Adapters;

import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * IntAdapter - класс адаптер из строки в Integer для xml файлов
 */
public class IntAdapter extends XmlAdapter<String, Integer>{
    /**
     * unmarshal(String string) - метод, парсящий строку в Integer
     * @param string - строка, которую необходимо преобразовать
     * @return - число в формате Integer
     * @throws Exception - ошибка маршалинга
     */
    @Override
    public Integer unmarshal(String string) throws Exception {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("В файле числовое поле пусто");
        }
        try {
            return Integer.parseInt(string);
        } catch (Exception e) {
            throw new UnmarshalException("Некорректное значение в поле для Long");
        }
    }

    /**
     * marshal(Integer aInt) - метод, парсящий Float в строку
     * @param aInt - число, которое необходимо преобразовать
     * @return - число в формате String
     * @throws Exception - ошибка парсинга
     */
    @Override
    public String marshal(Integer aInt) throws Exception {
        return Integer.toString(aInt);
    }
}