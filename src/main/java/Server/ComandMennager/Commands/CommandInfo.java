package Server.ComandMennager.Commands;

import Server.Stack.StackMennager;
import Server.transfer.Response;

/**
 * CommandInfo - класс, реализующий выполнение команды info
 */
public class CommandInfo implements Command{
    StackMennager stackMennager;

    /**
     * CommandInfo - класс, реализующий выполнение команды info
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandInfo(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        return this.stackMennager.getStackInfo();
    }

    @Override
    public String description() {
        return "info - вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)";
    }
}
