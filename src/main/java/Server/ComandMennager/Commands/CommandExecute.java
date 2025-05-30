package Server.ComandMennager.Commands;

import Server.ComandMennager.CommandMennager;
import Server.FileMennager.FileMenager;
import Server.transfer.Request;
import Server.transfer.Response;

import java.util.Arrays;
import java.util.List;

/**
 * CommandExecute - класс, реализующий выполнение команды execute_script
 */
public class CommandExecute implements Command{
    FileMenager fileMenager;
    CommandMennager commandMennager;

    /**
     * CommandExecute(FileMenager fileMenager, CommandMennager commandMennager) конструктор класс
     * @param fileMenager - ссылка на файл-менеджер
     * @param commandMennager - ссылка на команд-менеджер
     */
    public CommandExecute(FileMenager fileMenager, CommandMennager commandMennager) {
        this.fileMenager = fileMenager;
        this.commandMennager = commandMennager;
    }

    /**
     * execute(String arg) - метод, отвечающий за исполнение команды, реализуемой классом
     * @param arg - строка запроса
     * @return - ответ
     */
    @Override
    public Response execute(String arg) {
        if (arg == null) {return new Response("После команды необходимо ввести название скрипта", null);}

        List<String[]> res = this.fileMenager.checkScript(arg);

        if (res.isEmpty()) {return new Response("Файл не найден/Файл пуст", null);}

        StringBuilder scriptResult = new StringBuilder();

        String[] resCommands = {"average_of_annual_turnover ", "filter_starts_with_full_name", "help", "show", "info"};

        boolean isCommandInput = false;
        String commandName = "";
        Response result;

        for (String[] args: res) {
            if (isCommandInput) {
                result = this.commandMennager.useCommand(new Request(commandName, args[0]));
            }
            else if (args.length == 1) {
                result = this.commandMennager.useCommand(new Request(args[0], null));
            }
            else {
                result = this.commandMennager.useCommand(new Request(args[0], args[1]));
            }

            if (result.args() != null) {
                switch (result.args()) {
                    case "error" -> {
                        String command;
                        if (isCommandInput) {command = commandName;}
                        else {command = args[0];}
                        return new Response("В ходе выполнения скрипта возникла ошибка\nOшибка при выполнении команды " + command + "\nОшибка: " + result.message(), "error");
                    }
                    case "input" -> {
                        isCommandInput = true;
                        commandName = args[0];
                    }
                    case "finish" -> isCommandInput = false;
                }
            }

            if (Arrays.asList(resCommands).contains(args[0])) {
                scriptResult.append(result.message());
            } else if (commandName.equals("count_by_official_address") && result.args() != null && result.args().equals("finish")) {
                scriptResult.append(result.message());
            }
        }

        return new Response(scriptResult + "\nСкрипт выполнен", null);
    }

    @Override
    public String description() {
        return "execute_script file_name - считать и исполнить скрипт из указанного файла.";
    }

}
