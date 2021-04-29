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
public class RemoveLowerCommand extends Command implements CommandWithInit {
    private Long value;

    //валидация id
    public void init(String argument, Scanner scanner) {
        if (!FieldsValidator.checkStringParseToLong(argument, "value - это целое число!!!"))
            throw new InputFormatException();
        value = Long.parseLong(argument);
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

            application.getCollection().removeIf(studyGroup -> studyGroup.getStudentsCount() < value);
//            application.getIdList().removeIf(listId -> listId.equals(id));
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
        return "Элементы были удалёны из коллекции";
    }

    @Override
    String getCommandInfo() {
        return "remove_lower {count} : удалит элементы меньший чем заданный";
    }

    @Override
    public String toString() {
        return "remove_lower";
    }

    @Override
    public boolean withArgument() {
        return true;
    }
}
