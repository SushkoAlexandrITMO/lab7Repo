package Server.ComandMennager.Commands;

import Server.FileMennager.FileMenager;
import Server.Model.*;
import Server.Stack.StackMennager;
import Server.transfer.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * CommandAdd - класс, реализующий выполнение команды add
 */
public class CommandAdd implements Command{
    private final StackMennager stackMennager;
    private final FileMenager fileMenager;

    private boolean isCheck;

    private boolean isIDSet;
    private boolean isStart;
    private boolean isNameAdded;
    private boolean isCoordinatesAdded;
    private boolean isXAdded;
    private boolean isYAdded;
    private boolean isCreationDateAdded;
    private boolean isAnnualTurnoverAdded;
    private boolean isFullNameAdded;
    private boolean isOrganizationTypeAdded;
    private boolean isOfAddressAdded;
    private boolean isStreetAdded;
    private boolean isZipCodeAdded;
    private boolean isLocationAdded;
    private boolean isLocationXAdded;
    private boolean isLocationYAdded;
    private boolean isLocationNameAdded;

    Organization org;
    Coordinates coords;
    java.time.LocalDate date;
    OrganizationType type;
    Addres addres;
    Location loc;

    /**
     * Конструктор класса
     * @param stackMennager - стек-менеджер, с которым работает программа
     */
    public CommandAdd(StackMennager stackMennager, FileMenager fileMenager) {
        this.stackMennager = stackMennager;
        this.fileMenager = fileMenager;

        this.isStart = false;
        this.isIDSet = false;
        this.isNameAdded = false;
        this.isCoordinatesAdded = false;
        this.isXAdded = false;
        this.isYAdded = false;
        this.isCreationDateAdded = false;
        this.isAnnualTurnoverAdded = false;
        this.isFullNameAdded = false;
        this.isOrganizationTypeAdded = false;
        this.isOfAddressAdded = false;
        this.isStreetAdded = false;
        this.isZipCodeAdded = false;
        this.isLocationAdded = false;
        this.isLocationXAdded = false;
        this.isLocationYAdded = false;
        this.isLocationNameAdded = false;

        this.org = new Organization();
        this.coords = new Coordinates();
        this.addres = new Addres();
        this.loc = new Location();
    }

    /*
    Да, я знаю, что Вы указывали на недопустимости огромного кол-ва if-else'ов, но другого решения я не смог придумать
    Прошу понять, простить и не бить
     */
    @Override
    public Response execute(String data) {
        Long id;

        if (!this.isIDSet) {
            id = this.fileMenager.getFreeID();
            if (id != null) {
                this.org.setId(id);
            } else {
                this.org.setId(this.fileMenager.getIDs());
            }
            this.isIDSet = true;
        }

        String resp = "";
        if (!this.isStart && !data.startsWith("null")) {
            return new Response("Никаких аргументов после команды add в той же строке не должно", null);
        }

        this.isStart = true;

        try {
            if (!this.isNameAdded) {
                if (!data.startsWith("null")) {
                    this.org.setName(data);
                    this.isNameAdded = true;
                    data = "null";
                } else {
                    return new Response("Введите название компании:", "input");
                }
            }

            if (!this.isCoordinatesAdded) {
                resp = "Зададим координаты компании\n";
                this.isCoordinatesAdded = true;
            }

            if (!this.isXAdded) {
                if (!data.startsWith("null")) {
                    if (!resp.isEmpty()) {
                        resp = "";
                    }
                    this.coords.setX(Integer.parseInt(data));
                    this.isXAdded = true;

                    data = "null";
                } else {
                    return new Response(resp + "Введите координату x координат компании (целое число):", null);
                }
            }

            if (!this.isYAdded) {
                if (!data.startsWith("null")) {
                    this.coords.setY((long) Float.parseFloat(data));
                    this.isYAdded = true;
                    data = "null";
                } else {
                    return new Response("Введите координату y координат компании компании (дробное число):", null);
                }
            }

            if (!this.isCreationDateAdded) {
                if (!data.startsWith("null")) {
                    this.org.setCreationDate(LocalDate.parse(data));
                    this.isCreationDateAdded = true;

                    data = "null";
                } else {
                    return new Response("Введите дату основания компании в формате гггг-мм-дд компании:", null);
                }
            }

            if (!this.isAnnualTurnoverAdded) {
                if (!data.startsWith("null")) {
                    this.org.setAnnulTurnover(Float.valueOf(data));
                    this.isAnnualTurnoverAdded = true;
                    data = "null";
                } else {
                    return new Response("Введите годовой оборот компании (целое число):", null);
                }
            }

            if (!this.isFullNameAdded) {
                if (!data.startsWith("null")) {
                    this.org.setFullName(data);
                    this.isFullNameAdded = true;
                    data = "null";
                } else {
                    return new Response("Введите полное название компании:", null);
                }
            }

            if (!this.isOrganizationTypeAdded) {
                if (!data.startsWith("null")) {
                    String dt = data;
                    if (dt.equals("1")) {
                        this.isOrganizationTypeAdded = true;
                        this.org.setType(OrganizationType.COMMERCIAL);
                    } else if (dt.equals("2")) {
                        this.isOrganizationTypeAdded = true;
                        this.org.setType(OrganizationType.GOVERNMENT);
                    } else if (dt.equals("3")) {
                        this.isOrganizationTypeAdded = true;
                        this.org.setType(OrganizationType.TRUST);
                    } else if (dt.equals("4")) {
                        this.isOrganizationTypeAdded = true;
                        this.org.setType(OrganizationType.PRIVATE_LIMITED_COMPANY);
                    } else {
                        return new Response("Введите число от 1 до 4 без дополнительных символов", null);
                    }
                    data = "null";
                } else {
                    return new Response("Введите номер типа компании:\n1. COMMERCIAL\n2. GOVERNMENT\n3. TRUST\n4. PRIVATE_LIMITED_COMPANY", null);
                }
            }

            if (!this.isOfAddressAdded) {
                resp = "Зададим официальный адрес компании\n";
                this.isOfAddressAdded = true;
            }

            if (!this.isStreetAdded) {
                if (!data.startsWith("null")) {
                    if (!resp.isEmpty()) {
                        resp = "";
                    }
                    this.addres.setStreet(data);
                    this.isStreetAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите улицу, на которой находится компания (просто название одним словом):", null);
                }
            }

            if (!this.isZipCodeAdded) {
                if (!data.startsWith("null")) {
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
                if (!data.startsWith("null")) {
                    if (!resp.isEmpty()) {
                        resp = "";
                    }
                    this.loc.setX(Integer.valueOf(data));
                    this.isLocationXAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите координату x локации, на которой находится компания (просто целое число):", null);
                }
            }

            if (!this.isLocationYAdded) {
                if (!data.startsWith("null")) {
                    this.loc.setY(Float.parseFloat(data));
                    this.isLocationYAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите координату y локации, на которой находится компания (просто число):", null);
                }
            }

            if (!this.isLocationNameAdded) {
                if (!data.startsWith("null")) {
                    this.loc.setName(data);
                    this.isLocationNameAdded = true;
                } else {
                    return new Response("Введите название локации:", null);
                }
            }

            this.org.setCoordinates(this.coords);
            this.addres.setTown(this.loc);
            this.org.setOfficialAddress(this.addres);

        } catch (NumberFormatException e) {
            return new Response("Неверный формат данных", "error");
        } catch (IllegalArgumentException e) {
            return new Response(e.getMessage(), "error");
        } catch (DateTimeParseException e) {
            return new Response("Дата введена не верно\nПравильный формат ввода: гггг-мм-дд", "error");
        }
        setOrg(this.org);
        cleanAll();
        return new Response("Данные сохранены", "finish");
    }

    @Override
    public String description() {
        return "add - добавить новый элемент в коллекцию";
    }

    /**
     * cleanAll() - метод, предназначенный для обнуления всех флагов
     */
    private void cleanAll() {
        this.isStart = false;
        this.isIDSet = false;
        this.isNameAdded = false;
        this.isCoordinatesAdded = false;
        this.isXAdded = false;
        this.isYAdded = false;
        this.isCreationDateAdded = false;
        this.isAnnualTurnoverAdded = false;
        this.isFullNameAdded = false;
        this.isOrganizationTypeAdded = false;
        this.isOfAddressAdded = false;
        this.isStreetAdded = false;
        this.isZipCodeAdded = false;
        this.isLocationAdded = false;
        this.isLocationXAdded = false;
        this.isLocationYAdded = false;
        this.isLocationNameAdded = false;

        this.org = new Organization();
        this.coords = new Coordinates();
        this.addres = new Addres();
        this.loc = new Location();
    }

    /**
     * setOrg(Organization org) - метод, предназначенный для добавления организации в стек
     * @param org - организация, которую необходимо добавить
     */
    public void setOrg(Organization org) {
        this.stackMennager.addOrganization(new StackRequest(org, null));
    }
}