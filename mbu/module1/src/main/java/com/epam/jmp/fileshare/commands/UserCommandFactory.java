package com.epam.jmp.fileshare.commands;

import java.util.HashMap;
import java.util.Map;

import com.epam.jmp.fileshare.commands.impl.DownloadFileCommand;
import com.epam.jmp.fileshare.commands.impl.HelpCommand;
import com.epam.jmp.fileshare.commands.impl.LoadAllFilesCommand;
import com.epam.jmp.fileshare.commands.impl.QuitCommand;
import com.epam.jmp.fileshare.commands.impl.UploadFileCommand;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class UserCommandFactory {

    private static Map<UserCommandType, UserCommand> commandMap = new HashMap<>();

    static {
        commandMap.put(UserCommandType.HELP, new HelpCommand());
        commandMap.put(UserCommandType.LOAD_SELECTED, new DownloadFileCommand());
        commandMap.put(UserCommandType.QUIT, new QuitCommand());
        commandMap.put(UserCommandType.SHOW_ALL, new LoadAllFilesCommand());
        commandMap.put(UserCommandType.UPLOAD, new UploadFileCommand());
    }

    public static UserCommand getCommandByType(final UserCommandType commandType) {

        if (commandType == null) {
            System.out.println(">Unrecognized command ");
            System.out.println(">Try to use:");
            return new HelpCommand();
        } else {
            return commandMap.get(commandType);
        }
    }
}
