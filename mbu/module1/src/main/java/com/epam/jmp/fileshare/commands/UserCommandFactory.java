package com.epam.jmp.fileshare.commands;

import com.epam.jmp.fileshare.commands.impl.DownloadFileCommand;
import com.epam.jmp.fileshare.commands.impl.HelpCommand;
import com.epam.jmp.fileshare.commands.impl.LoadAllFilesCommand;
import com.epam.jmp.fileshare.commands.impl.QuitCommand;
import com.epam.jmp.fileshare.commands.impl.UploadFileCommand;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class UserCommandFactory {

    public static UserCommand getCommandByType(final UserCommandType commandType) {

        if (commandType == null) {
            System.out.println(">Unrecognized command ");
            System.out.println(">Try to use:");
            return new HelpCommand();
        }

        switch (commandType) {
        case HELP:
            return new HelpCommand();
        case LOAD_SELECTED:
            return new DownloadFileCommand();
        case QUIT:
            return new QuitCommand();
        case SHOW_ALL:
            return new LoadAllFilesCommand();
        case UPLOAD:
            return new UploadFileCommand();
        default:
            return null;
        }
    }
}
