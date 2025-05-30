package Server.DeveloperKit;

import Server.transfer.IORequest;

import java.util.Scanner;

public class SimpleIOMenager {
    Scanner scanner;
    boolean fullLogs;
    String line;
    
    public SimpleIOMenager() {
        this.fullLogs = true;
        this.scanner = new Scanner(System.in);
    }
    
    public void sendLog(IORequest request) {
        switch (request.code()) {
            case 1 -> {
                System.out.println("Ошибка!");
                System.err.println(request.message());
            }

            case  2 -> {
                System.err.println("Критическая ошибка!");
                System.err.println(request.message());
            }

            default -> System.out.println(request.message());
        }
    }

    public boolean getSettings() {
        boolean res = true;
        System.out.println("Сервер начал работу");
        System.out.println("Введите, необходимы ли логи в данном сеансе? (true/false)");
        while (this.scanner.hasNextBoolean()) {
            res = this.scanner.nextBoolean();
            break;
        }
        System.out.println("Данные приняты. Установлено - " + res);
        return res;
    }

    public boolean isShutDown() {
        try {
            byte[] buffer = new byte[8];
            if (System.in.available() >= 8) {
                System.in.read(buffer);
                return new String(buffer).trim().equals("shutdown");
            } else {return false;}
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isSave() {
        try {
            byte[] buffer = new byte[8];
            if (System.in.available() >= 8) {
                System.in.read(buffer);
                return new String(buffer).trim().equals("save");
            } else {return false;}
        } catch (Exception e) {
            return false;
        }
    }
}