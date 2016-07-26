package com.epam.jmp.fileshare.commands.impl;

import java.text.MessageFormat;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.commands.UserCommandType;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.util.FileShareDateFormatter;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class LoadAllFilesCommand implements UserCommand {

    @Override
    public void execute(final FileShareService shareService) {

        try {
            final List<FileDto> files = shareService.loadAllFiles();
            if (CollectionUtils.isEmpty(files)) {
                System.out.println(">Be first who upload new file. try to use "
                        + UserCommandType.UPLOAD);
            }
            for (final FileDto file : files) {
                System.out.println(MessageFormat.format(
                        ">{0}.{1} will expire in {2}. link for download: {3}", file.getName(), file
                                .getExtension(), FileShareDateFormatter.format(file
                                .getExpirationDate()), file.getUuid()));
            }
        } catch (FileShareException e) {
            System.out.println(e.getMessage());
        }
    }
}
