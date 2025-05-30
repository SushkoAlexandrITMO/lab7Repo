package Server.ComandMennager.Commands;

import Server.Model.*;
import Server.Stack.StackMennager;
import Server.transfer.*;

import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * CommandAverage - класс, предназначенный для реализации команды average
 */
public class CommandAverage implements Command{
    private final StackMennager stackMennager;

    /**
     * CommandAverage(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandAverage(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        if (arg != null) {return new Response("После команды average_of_annual_turnover не должно быть аргументов", "error");}

        Stream<Organization> streamed = this.stackMennager.getStack().stream();
        Stream<Float> annual = streamed.map(a -> a.getAnnualTurnover());
        double sum = annual.reduce((float) 0, (a, b) -> a+b);

        return new Response("Средний годовой оборот компаний: " + String.valueOf((sum/streamed.count())), null);
    }

    @Override
    public String description() {
        return "average_of_annual_turnover - вывести среднее значение годового оборота для всех элементов коллекции";
    }
}
