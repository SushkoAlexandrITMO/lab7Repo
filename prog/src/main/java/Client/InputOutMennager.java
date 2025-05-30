package Client;

import Client.transfer.*;

import java.util.Scanner;

/**
 * IOMennager - класс, ответственный за первичную обработку вводимой информации и вывод ответов
 */
public class InputOutMennager {
    ClientConnectMenager connectMenager;
    private boolean isCommandInput;
    private String commandName;
    Response resp;
    private boolean isConnectReady;

    /**
     * InputOutMennager(CommandMennager commandMennager) - конструктор класса
     * @param connectMenager - ссылка на коннект-менеджер
     */
    public InputOutMennager(ClientConnectMenager connectMenager) {
        this.connectMenager = connectMenager;
        this.isCommandInput = false;
        this.isConnectReady = false;
    }

    /**
     * Start() - метод, отвечающий за запуск и функционирования приёма вводимых данных и их первичную обработку и вывод ответов
     */
    public void Start() {
        isCommandInput = false;


        Scanner scanner = new Scanner(System.in);

        System.out.println("Приложение начало работу");

        if (!this.connectMenager.initConnect()) {
            System.out.println("Невозможно установить соединение с сервером\n" +
                    "Попробуйте перезапустить программу и убедитесь в надёжности Вашего интернет соединения");
            System.exit(2);
        };

        while (scanner.hasNextLine()) {

            String[] inp = scanner.nextLine().split(" ");

            if (inp.length == 0) {continue;}

            if (inp.length > 2) {
                System.out.println("Слишком большое количество аргументов!");
                continue;
            }
            else if (this.isCommandInput) {
                this.resp = connectMenager.sendGetToFromServer(new Request(this.commandName, inp[0]));
            }
            else if (inp.length == 1) {
                this.resp = connectMenager.sendGetToFromServer(new Request(inp[0], null));
            }
            else {
                this.resp = connectMenager.sendGetToFromServer(new Request(inp[0], inp[1]));
            }
            if (resp != null) {
                if (resp.message().isEmpty()) {
                    System.out.println("В ответе пустовато...\nВозможно сервак прилёг или ему плохо\nИли программист дебич");
                } else {
                    resp.message().replace("\n\n", "\n");
                    System.out.println(resp.message());
                }
            }

            if (resp.args() != null) {
                switch (resp.args()) {
                    case "input" -> {
                        this.isCommandInput = true;
                        this.commandName = inp[0];
                    }
                    case "finish" -> {
                        this.isCommandInput = false;
                        this.commandName = null;
                    }
                    case "exit" -> {
                        System.out.println("Приложение завершило работу");
                        System.exit(0);
                    }
                }
            }

        }

    }
}
