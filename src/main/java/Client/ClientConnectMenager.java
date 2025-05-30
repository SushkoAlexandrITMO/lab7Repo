package Client;

import Client.transfer.Request;
import Client.transfer.Response;

import java.net.*;
import java.nio.channels.AlreadyBoundException;

public class ClientConnectMenager {
    DatagramSocket socket;
    InetAddress serverAddress;

    public ClientConnectMenager() {}

    public boolean initConnect() {
        int reqCount = 0;
        try {
            this.serverAddress = InetAddress.getByName("localhost");
            //this.serverAddress = InetAddress.getByName("77.234.196.4");
            this.socket = new DatagramSocket();
            this.socket.setSoTimeout(5000);
        } catch (AlreadyBoundException e) {
            return true;
        } catch (SocketException e) {
            if (e.getMessage().equals("Already bound")) {
                return false;
            }
            reqCount ++;
            if (this.socket != null && !this.socket.isClosed()) {this.socket.close();}
            if (reqCount > 10) {
                initConnect();
                return false;
            }
        } catch (UnknownHostException e) {
            return false;
        }

        return true;
    }

    public Response sendGetToFromServer(Request request) {
        Response gatherResponse;
        try {
            /*
              Траходром начинается,
             */
            /*
              Запрос преобразуется в байты и формируется пакет
             */
            byte[] byteRequest = (request.command() + "|" + request.arg()).getBytes();
            DatagramPacket requestPacket = new DatagramPacket(
                    byteRequest,
                    byteRequest.length,
                    this.serverAddress,
                    5555
            );
            /*
              Пакет отправляется на сервак
             */
            this.socket.send(requestPacket);

            /*
              Получаем ответ и обрабатываем этот пиздец
             */
            /*
              Создаём второй буфер и принимаем в него ответ
             */
            byte[] responseData = new byte[16384];
            DatagramPacket receivePacket = new DatagramPacket(responseData, responseData.length);
            this.socket.receive(receivePacket);
            /*
              Делаем строку
             */
            String response = new String(receivePacket.getData(), 0, receivePacket.getLength());
            String[] inp = response.split("\\|");
            /*
            Создаём ответ и кидаем обратно
             */
            gatherResponse = new Response(inp[0], inp[1]);

        } catch (SocketTimeoutException e) {
            return new Response("Сервер долго не отвечает.\nВозможно вычисления слишком объёмны или связь потеряна.", "err");
        } catch (PortUnreachableException e) {
            return new Response("Возникла ошибка при работе с портом: " + e.getMessage(), "err");
        } catch (Exception e) {
            return new Response("Ошибка!\n" + e.getMessage(), "err");
        }

        return gatherResponse;
    }
}
