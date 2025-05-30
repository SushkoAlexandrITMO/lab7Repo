package Server.ComandMennager.Commands;

import Server.transfer.Response;
/**
 * Command - интерфейс, задающий общее устройство команды
 */
public interface Command {
    /**
     * execute(String arg) - метод для реализации функционала команды, реализуемой классом
     * @param arg - параметр, необходимый для исполнения команды
     * @return - ответ о выполнении команды
     */
    Response execute(String arg) ;

    /**
     * description() - метод для получения описания команды
     * @return - описание команды
     */
    String description();
}
