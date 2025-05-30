package Server.ComandMennager.Commands;

import Server.Model.*;
import Server.Stack.StackMennager;
import Server.transfer.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * CommandShuffle - класс, реализующий выполнение команды shuffle
 */
public class CommandShuffle implements Command{
    private final StackMennager stackMennager;

    /**
     * CommandShuffle(StackMennager stackMennager) - класс, реализующий выполнение команды save
     * @param stackMennager  - ссылка на стек-менеджер
     */
    public CommandShuffle(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        ArrayList<Organization> list = new ArrayList<>();
        Stack<Organization> stack = this.stackMennager.getStack();
        list.addAll(stack);
        Collections.shuffle(list);
        this.stackMennager.clearStack();
        this.stackMennager.setAll(list);
        return new Response("Коллекция перемешана", null);
    }

    @Override
    public String description() {
        return "shuffle - перемешать элементы коллекции в случайном порядке";
    }
}
