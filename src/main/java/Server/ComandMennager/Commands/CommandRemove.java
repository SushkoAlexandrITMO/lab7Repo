package Server.ComandMennager.Commands;

import Server.FileMennager.FileMenager;
import Server.Stack.StackMennager;
import Server.transfer.Response;
import Server.transfer.StackRequest;

/**
 * CommandRemove - класс, реализующий выполнение команды remove
 */
public class CommandRemove implements Command{
    private final StackMennager stackMennager;
    private final FileMenager fileMenager;

    /**
     * CommandRemove(StackMennager stackMennager, FileMenager fileMenager) - класс, реализующий выполнение команды remove
     * @param stackMennager - ссылка на стек-менеджер
     * @param fileMenager - ссылка на файл-менеджер
     */
    public CommandRemove(StackMennager stackMennager, FileMenager fileMenager) {
        this.stackMennager = stackMennager;
        this.fileMenager = fileMenager;
    }

    @Override
    public Response execute(String arg) {
        if (!arg.startsWith("null")) {return new Response("После команды remove_by_id должен быть указан ID компании", "error");}
        Long id = Long.parseLong(arg);
        if (this.stackMennager.isIDIn((new StackRequest(null, id))).message().equals("0")) {return new Response("Компании с таким ID не существует", "error");}

        this.stackMennager.removeFromStack(new StackRequest(null, id));
        this.fileMenager.addNewFreeID(String.valueOf(id));

        return new Response("Организация успешно удалена", null);
    }

    @Override
    public String description() {
        return "remove_by_id id - удалить элемент из коллекции по его id";
    }
}
