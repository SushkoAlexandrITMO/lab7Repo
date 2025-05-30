package Server;

import Server.ComandMennager.CommandMennager;
import Server.DeveloperKit.SimpleIOMenager;
import Server.FileMennager.FileMenager;
import Server.Stack.StackMennager;
import Server.ConnectMenager.ServerConnectMenager;

public class ServerMain {
    public static void main(String[] args) {
        SimpleIOMenager simpleIO = new SimpleIOMenager();
        boolean isLogsON = simpleIO.getSettings();
        FileMenager fileMenager = new FileMenager(isLogsON, simpleIO);
        StackMennager stackMennager = new StackMennager(fileMenager, isLogsON, simpleIO);
        CommandMennager commandMennager= new CommandMennager(stackMennager, isLogsON, simpleIO);
        ServerConnectMenager serverConnectMenager = new ServerConnectMenager(commandMennager, isLogsON, simpleIO);
        serverConnectMenager.getSendFromToClient();

    }
}
