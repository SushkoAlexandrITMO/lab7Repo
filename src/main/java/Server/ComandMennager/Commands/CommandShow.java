package Server.ComandMennager.Commands;

import Server.Model.Organization;
import Server.Stack.StackMennager;
import Server.transfer.Response;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Stack;

/**
 * CommandShow - класс, реализующий выполнение команды show\n
 * Имеет поле StackMennager stackMennager
 */
@XmlRootElement(name = "show")
public class CommandShow implements Command{
    private final StackMennager stackMennager;

    /**
     * CommandShow(StackMennager stackMennager) - конструктор класса
     * @param stackMennager - стек-менеджер, с которым работает команда
     */
    public CommandShow(StackMennager stackMennager) {
        this.stackMennager = stackMennager;
    }

    @Override
    public Response execute(String arg) {
        Stack<Organization> stack = this.stackMennager.getStack();
        String resp = "";
        int i = 0;
        for (Organization org: stack) {
            resp += "Организация №" + ++i + " :" + org.toString() + "\n\n";
        }

        return new Response(resp + "\nВывод окончен\n", null);
    }

    /**
     * Description - метод, реализующий вывод информации о команде, реализуемой классом
     */
    @Override
    public String description() {
        return "show - вывести в стандартный поток вывода все элементы коллекции в строковом представлении";
    }
}
