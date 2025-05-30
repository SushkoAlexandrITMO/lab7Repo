package Server.ComandMennager.Commands;

import Server.Stack.StackMennager;
import Server.transfer.*;

/**
 * CommandClear - класс, предназначенный для реализации команды clear
 */
public class CommandClear implements Command{
    StackMennager stackMennager;

    /**
     * CommandClear(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - ссылка на стек-менеджер
     */
    public CommandClear(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        return this.stackMennager.clearStack();
    }

    @Override
    public String description() {
        return "clear - очистить коллекцию";
    }
}
