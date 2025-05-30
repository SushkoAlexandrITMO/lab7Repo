package Server.ComandMennager.Commands;

import Server.Stack.StackMennager;
import Server.transfer.Request;
import Server.transfer.Response;

/**
 * CommandSave - класс, реализующий выполнение команды save
 */
public class CommandSave implements Command{
    StackMennager stackMennager;

    /**
     * CommandSave(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandSave(StackMennager stackMennager) {this.stackMennager = stackMennager;}

    @Override
    public Response execute(String arg) {
        return this.stackMennager.saveStack(new Request("", null));
    }

    @Override
    public String description() {
        return "save - сохранить коллекцию в файл";
    }
}
