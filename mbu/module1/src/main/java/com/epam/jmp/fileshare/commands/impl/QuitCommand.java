package com.epam.jmp.fileshare.commands.impl;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.services.FileShareService;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class QuitCommand implements UserCommand {

    @Override
    public void execute(final FileShareService shareService) throws Exception {

        System.exit(0);
    }
}
