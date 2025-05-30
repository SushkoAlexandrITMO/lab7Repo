package Server.ComandMennager.Commands;

import Server.Model.*;
import Server.Stack.StackMennager;
import Server.transfer.*;

import java.awt.*;
import java.util.List;
import java.util.Stack;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * CommandFilterFullName - класс, реализующий выполнение команды filter_starts_with_full_name
 */
public class CommandFilterFullName implements Command{
    private final StackMennager stackMennager;

    /**
     * CommandFilterFullName(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandFilterFullName(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        if (!arg.startsWith("null")) {return new Response("Введите подстроку для поиска!", null);}
        StringBuilder resp = new StringBuilder();

        List<Organization> stackStream = this.stackMennager.getStack()
                .stream().filter(b -> b.getFullName().contains(arg)).toList();
        for (Organization org: stackStream) {
            resp.append(org.toString()).append("\n");
        }

        if (resp.isEmpty()) {return new Response("Нет компаний, чьё полное название содержало в виде подстроки введённую строку", null);}
        return new Response("Список компаний, чьё полное название начинается с " + arg + ": " + resp, null);
    }

    @Override
    public String description() {
        return "filter_starts_with_full_name fullName - вывести элементы, полное название которых начинается с заданной подстроки";
    }
}
