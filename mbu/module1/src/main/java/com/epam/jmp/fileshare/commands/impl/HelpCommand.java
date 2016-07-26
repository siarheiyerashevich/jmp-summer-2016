package com.epam.jmp.fileshare.commands.impl;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.commands.UserCommandType;
import com.epam.jmp.fileshare.services.FileShareService;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class HelpCommand implements UserCommand {

    @Override
    public void execute(final FileShareService shareService) {

        System.out.println(">Useful commands:");
        for (final UserCommandType userCommandType : UserCommandType.values()) {
            System.out.println(">" + userCommandType);
        }
    }
}
