package com.epam.jmp.fileshare.commands;

import com.epam.jmp.fileshare.services.FileShareService;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public interface UserCommand {

    void execute(final FileShareService shareService) throws Exception;
}
