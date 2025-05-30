package Server.FileMennager.Adapters;

import javax.xml.bind.UnmarshalException;
import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * FloatAdapter - класс адаптер из строки в Float для xml файлов
 */
public class FloatAdapter extends XmlAdapter<String, Float>{
    /**
     * unmarshal(String string) - метод, парсящий строку в Float
     * @param string - строка, которую необходимо преобразовать
     * @return - число в формате Float
     * @throws Exception - ошибка маршалинга
     */
    @Override
    public Float unmarshal(String string) throws Exception {
        if (string == null || string.trim().isEmpty()) {
            throw new IllegalArgumentException("В файле числовое поле пусто");
        }
        try {
            return Float.parseFloat(string);
        } catch (Exception e) {
            throw new UnmarshalException("Некорректное значение в поле для Float");
        }
    }

    /**
     * marshal(Float aFloat) - метод, парсящий Float в строку
     * @param aFloat - число, которое необходимо преобразовать
     * @return - число в формате String
     * @throws Exception - ошибка парсинга
     */
    @Override
    public String marshal(Float aFloat) throws Exception {
        return Float.toString(aFloat);
    }
}