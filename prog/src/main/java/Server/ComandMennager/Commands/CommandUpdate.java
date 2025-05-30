package Server.ComandMennager.Commands;

import Server.Model.*;
import Server.Stack.StackMennager;
import Server.transfer.*;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

/**
 * CommandUpdate - класс, реализующий выполнение команды update
 */
public class CommandUpdate implements Command{
    private final StackMennager stackMennager;
    private boolean isCheck;

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
    Addres addres;
    Location loc;
    private Long id;
    private boolean isIDSet;

    /**
     * CommandUpdate - CommandUpdate(StackMennager stackMennager, FileMenager fileMenager)
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandUpdate(StackMennager stackMennager) {
        this.stackMennager = stackMennager;

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
        this.isCheck = false;

        this.org = new Organization();
        this.coords = new Coordinates();
        this.addres = new Addres();
        this.loc = new Location();
    }

    @Override
    public Response execute(String data) {
        if (!this.isCheck) {
            if (!check(Long.parseLong(data))) {return new Response("Организация с данным ID отсутствует", null);}
            this.id = Long.valueOf(data);
            this.org.setId(this.id);
            data = "null";
            isCheck = true;
        }

        String resp = "";

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
                    this.coords.setY(Long.parseLong(data));
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
                    if (resp != "") {
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
                if (!data.startsWith("null")) {
                    this.loc.setY(Float.parseFloat(data));
                    this.isLocationYAdded = true;
                    data = "null";
                } else {
                    return new Response(resp + "Введите координату y локации, на которой находится компания (просто название одним словом):", null);
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
        setOrg(this.org, this.id);
        cleanAll();
        return new Response("Данные обновлены", "finish");
    }

    private void cleanAll() {
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
        this.coords = null;
        this.addres = null;
        this.loc = null;
        this.isIDSet = false;

        this.org = new Organization();
        this.coords = new Coordinates();
        this.addres = new Addres();
        this.loc = new Location();
    }

    public Response setOrg(Organization org, Long id) {
        return this.stackMennager.setOrgFromID(new StackRequest(org, id));
    }

    public boolean check(Long id) {
        return this.stackMennager.isIDIn(new StackRequest(null, id)).message().equals("1");
    }

    @Override
    public String description() {
        return "update id {element} - обновить значение элемента коллекции, id которого равен заданному";
    }
}