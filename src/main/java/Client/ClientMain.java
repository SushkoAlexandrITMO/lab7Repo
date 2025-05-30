package Client;

public class ClientMain {
    public static void main(String[] args) {
        ClientConnectMenager connectMenager = new ClientConnectMenager();
        InputOutMennager inputOutMennager = new InputOutMennager(connectMenager);
        inputOutMennager.Start();
    }
}
