package com.epam.jmp.fileshare.util;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbuny on 21.07.2016.
 */
public enum UserCommand {

    QUIT("quit", "q"),
    SHOW_ALL("all", "a"),
    LOAD_SELECTED("select", "s"),
    UPLOAD("upload", "u"),
    HELP("help", "h");


    private List<String> codes = new ArrayList<>();

    private UserCommand(final String... codes) {
        for (final String code : codes) {
            this.codes.add(code);
        }
    }

    public static UserCommand fromString(final String value) {
        for (final UserCommand userCommand : UserCommand.values()) {
            if (userCommand.codes.contains(value)) {
                return userCommand;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return this.codes.get(0);
    }
}
