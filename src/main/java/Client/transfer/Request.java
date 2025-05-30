package Client.transfer;

/**
 * Request - record, служащий для хранения данных запроса
 * @param command - название команды
 * @param arg - аргумент команды
 */
public record Request(String command, String arg) {}