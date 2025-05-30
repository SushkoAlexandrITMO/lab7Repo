package Server.ComandMennager.Commands;

import Server.Model.Organization;
import Server.Stack.StackMennager;
import Server.transfer.Response;

import java.util.ArrayList;
import java.util.Comparator;

/**
 * CommandSort - класс, реализующий выполнение команды sort
 */
public class CommandSort implements Command{
    private final StackMennager stackMennager;

    /**
     * CommandSort(StackMennager stackMennager) - класс, реализующий выполнение команды save
     * @param stackMennager  - ссылка на стек-менеджер
     */
    public CommandSort(StackMennager stackMennager) {this.stackMennager = stackMennager;}

    @Override
    public Response execute(String arg) {
        if (arg != null) {return new Response("После команды reorder не должно быть аргументов", null);}

        ArrayList<Organization> list = new ArrayList<>();

        for (Organization org: this.stackMennager.getStack()) {
            list.add(org);
        }

        list.sort(Comparator.comparingLong(Organization::getID));

        this.stackMennager.clearStack();
        this.stackMennager.setAll(list);

        return new Response("Коллекция отсортирована", null);
    }

    @Override
    public String description() {
        return "sort - отсортировать коллекцию в естественном порядке";
    }
}
