package com.itmo.commands;

import com.itmo.app.Application;
import com.itmo.app.StudyGroup;
import com.itmo.server.Session;
import com.itmo.utils.FieldsValidator;
import com.itmo.exceptions.IdNotFoundException;
import com.itmo.exceptions.InputFormatException;
import lombok.NonNull;

import java.util.Scanner;

/**
 * команда удаляет элемент из коллекции по его id
 */
public class MaxByName extends Command {
    /**
     * удаление элемента
     */
    @Override
    public String execute(Application application, @NonNull Session session) {
        try {
            String group = application.getCollection().stream().max(StudyGroup::compareTo).orElse(null).toString();
            return group;
        } catch (IdNotFoundException e) {
            return e.getMessage();
        }
    }

    @Override
    String getCommandInfo() {
        return "max_by_name : выводит элемент с максимальным именем";
    }

    @Override
    public String toString() {
        return "max_by_name";
    }

    @Override
    public boolean withArgument() {
        return false;
    }
}
