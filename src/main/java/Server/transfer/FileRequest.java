package Server.transfer;

import Server.Model.Organization;

import java.util.Stack;

/**
 * FileRequest(Stack<Organization> stack, String file) - запрос от StackMennager к FileManager
 * @param stack - стек, необходимый для выполнения запроса
 */
public record FileRequest(Stack<Organization> stack) {}