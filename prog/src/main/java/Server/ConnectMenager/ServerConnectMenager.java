package Server.ConnectMenager;

import Server.DeveloperKit.SimpleIOMenager;
import Server.transfer.IORequest;
import Server.transfer.Request;
import Server.transfer.Response;
import Server.ComandMennager.CommandMennager;

import java.io.IOException;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.DatagramChannel;
import java.util.Arrays;

public class ServerConnectMenager {
    boolean isCommandInput;
    int packetsErrors;
    SimpleIOMenager ioMenager;
    DatagramChannel serverChanel;
    boolean isLogsON;
    InetSocketAddress serverAddress;
    int waitCount;
    int reqCount;

    CommandMennager commandMennager;

    public ServerConnectMenager(CommandMennager commandMennager, boolean isLogsON, SimpleIOMenager simpleIOMenager) {
        this.commandMennager = commandMennager;
        this.isCommandInput = false;
        this.packetsErrors = 0;
        this.ioMenager = simpleIOMenager;
        this.isLogsON = isLogsON;
        initConnect(0);
        this.waitCount = 0;
    }

    private void initConnect(int reqCount) {
        this.reqCount = reqCount;
        try {
            this.serverChanel = DatagramChannel.open();
            this.serverChanel.bind(new InetSocketAddress(5555));
            this.serverChanel.configureBlocking(false);
            this.ioMenager.sendLog(new IORequest("Сервер успешно инициализировал канал на порту 5555 ", 0));
        } catch (SocketException e) {
            reqCount ++;
            if (this.serverChanel != null) {
                try {
                    this.serverChanel.close();
                } catch (IOException ex) {
                    this.ioMenager.sendLog(new IORequest("Ошибка создания сокета: \n" +
                            e.getMessage() +
                            "\nПрограмма завершает работу.", 2));
                }
            }
            if (reqCount > 10) {
                this.ioMenager.sendLog(new IORequest("Ошибка создания сокета: \n" +
                        e.getMessage() +
                        "\nПрограмма завершает работу.", 2));
                System.exit(2);
            }
            initConnect(this.reqCount);
        } catch (IOException e) {
            this.ioMenager.sendLog(new IORequest("Не удалось создать сокет. Завершение выполнения программы." ,2));
        }
    }

    public void getSendFromToClient() {
        while (true) {
            try {
            /*
            Проверяем, не надо ли закрываться
             */
                if (this.ioMenager.isShutDown()) {System.exit(0);}
                if (this.ioMenager.isSave()) {this.commandMennager.useCommandSave();}
            /*
            Создаём сокет и буфер
             */
                ByteBuffer buffer = ByteBuffer.allocate(16384);
                byte[] requestByte = Arrays.copyOf(buffer.array(), buffer.limit());
            /*
            Создаём пакет и грузим запрос
             */
                while (true) {
                    this.waitCount ++;
                    this.serverAddress = (InetSocketAddress) serverChanel.receive(buffer);
                    if (this.serverAddress != null) {break;}
                    if (this.waitCount > 20) {break;}
                }
                this.waitCount = 0;
            /*
            Пытаемся декодировать
             */
                if (this.serverAddress != null) {
                    for (byte b : requestByte) {
                        if (b != 0) {
                            buffer.put(b);
                        }
                    }


                    String message = new String(buffer.array(), 0, buffer.limit());
                    message = message.replaceAll("\n", "");
                    message = message.replaceAll("\u0000", "");
                    String[] inp = message.split("\\|");
            /*
            Получаем данные для отправки ответа
             */
                    InetAddress serverAddress = this.serverAddress.getAddress();
                    int clientPort = this.serverAddress.getPort();

                    if (this.isLogsON) {
                        this.ioMenager.sendLog(new IORequest("Получен пакет от:\n" +
                                serverAddress.toString() + " " +
                                clientPort +
                                "\nСообщение:\n" +
                                inp[0] + " " + inp[1],
                                0));
                    }
            /*
            Формируем запрос и отправляем на обработку, получаем ответ
             */
                    Request request = new Request(inp[0], inp[1]);
                    Response response = this.commandMennager.useCommand(request);
                    if (this.isLogsON) {
                        this.ioMenager.sendLog(new IORequest("Получен ответ: " +
                                "args: " + response.args() +
                                "message " + response.message(),
                                0));
                    }
            /*
            Создаём пакет и отправляем ответ
             */
                    buffer.clear();
                    buffer.put((response.message() + "|" + response.args()).getBytes());
                    buffer.flip();
                    this.serverChanel.send(buffer, new InetSocketAddress(serverAddress, clientPort));
                    if (this.isLogsON) {
                        this.ioMenager.sendLog(new IORequest("Пакет отправлен", 0));
                    }
                /*
                Обнуляем количество ошибок
                 */
                    this.packetsErrors = 0;
                }

            } catch (SocketException e) {
                this.ioMenager.sendLog(new IORequest("Ошибка создания сокета:\n" + e.getMessage(),0));
            } catch (IOException e) {
                this.packetsErrors++;
                this.ioMenager.sendLog(new IORequest("\"Ошибка отправки пакетов:\n" + e.getMessage(),0));
                if (this.packetsErrors > 20) {
                    this.ioMenager.sendLog(new IORequest("Достигнуто критическое количество ошибок отправки\n" +
                            "Сервер прекращает работу по техническим причинам",
                            0));
                    System.exit(2);
                }
            }
        }
    }

}
