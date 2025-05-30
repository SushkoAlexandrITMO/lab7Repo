package Server.ComandMennager;

import Server.ComandMennager.Commands.*;
import Server.DeveloperKit.SimpleIOMenager;
import Server.Stack.StackMennager;
import Server.transfer.IORequest;
import Server.transfer.Request;
import Server.transfer.Response;

import java.util.HashMap;
import java.util.Map;

/**
 * CommandMennager - класс, предназначенный для управления вызова и исполнения команд\n
 * Имеет поле - HashMap<String, Command> Commands
 */
public class CommandMennager {
    public static final Map<String, Command> Commands = new HashMap<String, Command>();
    boolean isLogsON;
    SimpleIOMenager ioMenager;
    String commandName;
    boolean isInputCommand;
    CommandSave commandSave;

    /**
     * CommandMennager - конструктор класса, предназначенный для задания команд в Map и создания экземпляра класса StackMennager
     */
    public CommandMennager(StackMennager stackMennager, boolean isLogsON, SimpleIOMenager simpleIOMenager) {
        this.ioMenager = simpleIOMenager;
        this.isLogsON = isLogsON;
        this.commandSave = new CommandSave(stackMennager);

        Commands.put("help", new CommandHelp());
        Commands.put("info", new CommandInfo(stackMennager));
        Commands.put("show", new CommandShow(stackMennager));
        Commands.put("add", new CommandAdd(stackMennager, stackMennager.getFileMennager()));
        Commands.put("update", new CommandUpdate(stackMennager));
        Commands.put("remove_by_id", new CommandRemove(stackMennager, stackMennager.getFileMennager()));
        Commands.put("clear", new CommandClear(stackMennager));
        Commands.put("execute_script", new CommandExecute(stackMennager.getFileMennager(), this));
        Commands.put("exit", new CommandExit(new CommandSave(stackMennager)));
        Commands.put("shuffle", new CommandShuffle(stackMennager));
        Commands.put("reorder", new CommandReorder(stackMennager));
        Commands.put("sort", new CommandSort(stackMennager));
        Commands.put("average_of_annual_turnover", new CommandAverage(stackMennager));
        Commands.put("count_by_official_address", new CommandCountByAddress(stackMennager));
        Commands.put("filter_starts_with_full_name", new CommandFilterFullName(stackMennager));
    }

    public void useCommandSave() {
        this.commandSave.execute(null);
    }

    /**
     * useCommand - метод, предназначенный для обработки данных и вызова необходимой команды
     * @param req - запрос из IO Менеджера
     * @return - ответ Response
     */
    public Response useCommand(Request req)  {
        Response res;
        if (this.isLogsON) {this.ioMenager.sendLog(new IORequest("Получена команда " +
                req.command() + ", с аргументом " +
                req.arg()
                ,0));
        }
        if (req.command().isEmpty() || !Commands.containsKey(req.command())) {
            return new Response("Команда введена не верно / данная команда не поддерживается", "error");
        }
        else {
            res =  Commands.get(req.command()).execute(req.arg());
        }
        return res;
    }
}
