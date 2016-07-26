package com.epam.jmp.fileshare.commands;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbuny on 21.07.2016.
 */
public enum UserCommandType {

    QUIT("quit", "q"), SHOW_ALL("all", "a"), LOAD_SELECTED("select", "s"), UPLOAD("upload", "u"), HELP(
            "help", "h");

    private List<String> codes = new ArrayList<>();

    private UserCommandType(final String... codes) {

        for (final String code : codes) {
            this.codes.add(code);
        }
    }

    public static UserCommandType fromString(final String value) {

        for (final UserCommandType userCommandType : UserCommandType.values()) {
            if (userCommandType.codes.contains(value)) {
                return userCommandType;
            }
        }
        return null;
    }

    @Override
    public String toString() {

        return this.codes.get(0);
    }
}
