package Server.ComandMennager.Commands;

import Server.ComandMennager.CommandMennager;
import Server.transfer.*;

/**
 * CommandExit - класс, реализующий выполнение команды exit
 */
public class CommandExit implements Command{
    CommandSave commandSave;

    public CommandExit(CommandSave commandSave) {
        this.commandSave = commandSave;
    }

    @Override
    public Response execute(String arg) {
        return new Response("Программа завершает свою работу.", "exit");
    }

    @Override
    public String description() {
        return "exit - завершить программу (без сохранения в файл)";
    }
}
