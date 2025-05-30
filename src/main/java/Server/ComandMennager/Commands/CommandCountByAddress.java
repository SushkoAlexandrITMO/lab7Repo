package Server.ComandMennager.Commands;

import Server.Model.Addres;
import Server.Model.Location;
import Server.Stack.StackMennager;
import Server.transfer.Response;

import java.time.format.DateTimeParseException;
import java.util.stream.Stream;

/**
 * CommandCountByAddress - класс, реализующий выполнение команды count_by_official_address
 */
public class CommandCountByAddress implements Command{
    private StackMennager stackMennager;

    private boolean isStarted;
    private boolean isOfAddressAdded;
    private boolean isStreetAdded;
    private boolean isZipCodeAdded;
    private boolean isLocationAdded;
    private boolean isLocationXAdded;
    private boolean isLocationYAdded;
    private boolean isLocationNameAdded;

    Addres addres;
    Location loc;

    /**
     * CommandCountByAddress(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandCountByAddress(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
        this.isStarted = false;
        this.isOfAddressAdded = false;
        this.isStreetAdded = false;
        this.isZipCodeAdded = false;
        this.isLocationAdded = false;
        this.isLocationXAdded = false;
        this.isLocationYAdded = false;
        this.isLocationNameAdded = false;
        this.addres = new Addres();
        this.loc = new Location();
    }

    @Override
    public Response execute(String data) {
        if (data != null && !this.isStarted) {return new Response("После команды count_by_official_address в той же строке никаких аргументов быть не должно", null);}

        long count = 0;
        String resp = "";
        this.isStarted = true;

        try {
            if (!this.isOfAddressAdded) {
                resp = "Зададим официальный адрес компании\n";
                this.isOfAddressAdded = true;
            }

            if (!this.isStreetAdded) {
                if (!data.equals("null")) {
                    if (resp != "") {
                        resp = "";
                    }
                    this.addres.setStreet(data);
                    this.isStreetAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите улицу, на которой находится компания (просто название одним словом):", "input");
                }
            }

            if (!this.isZipCodeAdded) {
                if (!data.equals("null")) {
                    this.addres.setZipCode(data);
                    this.isZipCodeAdded = true;
                    data = "null";
                } else {
                    return new Response("Введите зип-код адреса компании:", null);
                }
            }

            if (!this.isLocationAdded) {
                resp = "Зададим локацию компании\n";
                this.isLocationAdded = true;
            }

            if (!this.isLocationXAdded) {
                if (!data.equals("null")) {
                    if (resp != "") {
                        resp = "";
                    }
                    this.loc.setX(Integer.valueOf(data));
                    this.isLocationXAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите координату x локации, на которой находится компания (просто название одним словом):", null);
                }
            }

            if (!this.isLocationYAdded) {
                if (!data.equals("null")) {
                    this.loc.setY(Float.parseFloat(data));
                    this.isLocationYAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите координату y локации, на которой находится компания (просто название одним словом):", null);
                }
            }

            if (!this.isLocationNameAdded) {
                if (!data.equals("null")) {
                    this.loc.setName(data);
                    this.isLocationNameAdded = true;
                } else {
                    return new Response("Введите название локации:", null);
                }
            }

            this.addres.setTown(this.loc);

            Stream<Boolean> countStream = this.stackMennager.getStack().stream()
                    .map(a -> a.getOfficialAddress().toString().equals(this.addres.toString()));
            count = countStream.filter(b -> b).count();

            cleanAll();

        } catch (NumberFormatException e) {
            return new Response("Неверный формат данных", "error");
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage(), "error");
        } catch (DateTimeParseException e) {
            return new Response("Дата введена не верно\nПравильный формат ввода: гггг:мм:дд", "error");
        }

        return new Response("Количество компаний с заданным адресом: " + count + "\n", "finish");
    }

    /**
     * cleanAll() - метод, предназначенный для сброса значений флагов
     */
    private void cleanAll() {
        this.isOfAddressAdded = false;
        this.isStreetAdded = false;
        this.isZipCodeAdded = false;
        this.isLocationAdded = false;
        this.isLocationXAdded = false;
        this.isLocationYAdded = false;
        this.isLocationNameAdded = false;
        this.addres = new Addres();
        this.loc = new Location();
    }

    @Override
    public String description() {
        return "count_by_official_address officialAddress - вывести количество элементов, официальный адрес которых равен заданному";
    }
}
