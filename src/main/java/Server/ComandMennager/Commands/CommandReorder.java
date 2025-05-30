package Server.ComandMennager.Commands;

import Server.Model.Organization;
import Server.Stack.StackMennager;
import Server.transfer.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Stack;

/**
 * CommandReorder - класс, реализующий выполнение команды reorder
 */
public class CommandReorder implements Command{
    StackMennager stackMennager;

    /**
     * CommandReorder(StackMennager stackMennager) - класс, реализующий выполнение команды reorder
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandReorder(StackMennager stackMennager) {this.stackMennager = stackMennager;}

    @Override
    public Response execute(String arg) {
        if (!arg.startsWith("null")) {return new Response("После команды reorder не должно быть аргументов", "error");}
        Stack<Organization> stack = this.stackMennager.getStack();

        ArrayList<Organization> list = new ArrayList<>(stack);

        Collections.sort(list, Collections.reverseOrder());

        this.stackMennager.clearStack();
        this.stackMennager.setAll(list);
        return new Response("Данные ре отсортированы", null);
    }

    @Override
    public String description() {
        return "reorder - отсортировать коллекцию в порядке, обратном нынешнему";
    }
}
