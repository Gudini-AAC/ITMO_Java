package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import com.itmo.exceptions.IdNotFoundException;
import com.itmo.exceptions.InputFormatException;
import lombok.NonNull;

import java.util.Scanner;

/**
 * команда удаляет элемент из коллекции по его id
 */
public class RemoveFirstCommand extends Command implements CommandWithInit {
    private Long id;

    //валидация id
    public void init(String argument, Scanner scanner) {
        if (!FieldsValidator.checkStringParseToLong(argument, "id - это целое число!!!"))
            throw new InputFormatException();
        id = Long.parseLong(argument);
    }

    /**
     * удаление элемента
     */
    @Override
    public String execute(Application application, @NonNull Session session) {
        try {
//            if (application.getCollection().stream().noneMatch(studyGroup -> studyGroup.getOwner().equals(session.getUser()) && studyGroup.getId() == id) || !(application.getDataBaseManager().remove(id) > 0)) {
//                if (application.getCollection().stream().noneMatch(studyGroup -> studyGroup.getId() == id))
//                    throw new IdNotFoundException("Элемент не удален, т.к. элемента с таким id нет в коллекции");
//                throw new IdNotFoundException("Элемент не удален, т.к. вы не являетесь владельцем этого элемента");
//            }
            Long first_id = application.getIdList().iterator().next();
            application.getCollection().removeIf(studyGroup -> studyGroup.getId() == first_id);
            application.getIdList().removeIf(listId -> listId.equals(first_id));
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
        return "Первый элемент был удален из коллекции";
    }

    @Override
    String getCommandInfo() {
        return "remove_first : удалит первый элемент из коллекции";
    }

    @Override
    public String toString() {
        return "remove_first";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
