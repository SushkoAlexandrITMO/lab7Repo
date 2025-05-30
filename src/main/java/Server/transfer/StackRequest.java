package Server.transfer;

import Server.Model.Organization;

/**
 * StackRequest(Organization org, Long id) - record, служащий для хранения данных от CommandMenager к StackMenager
 * @param org - организация, которая необходима для выполнения запроса
 * @param id - айди, необходимый для выполнения запроса
 */
public record StackRequest(Organization org, Long id) {}