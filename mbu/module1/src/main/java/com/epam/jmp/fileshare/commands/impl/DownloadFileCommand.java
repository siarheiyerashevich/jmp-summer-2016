package com.epam.jmp.fileshare.commands.impl;

import java.io.File;
import java.io.FileOutputStream;
import java.text.MessageFormat;
import java.util.Scanner;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.services.FileShareService;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class DownloadFileCommand implements UserCommand {

    @Override
    public void execute(final FileShareService shareService) throws Exception {

        final Scanner scanner = new Scanner(System.in);
        System.out.println(">Which file you want? Enter file uuid:");
        final String fileUuid = scanner.next();
        System.out.println(">Enter directory path for saving:");
        final String newPath = scanner.next();
        if (StringUtils.isEmpty(newPath)) {
            System.out.println(">Check file path and come back later");
            return;
        }

        FileOutputStream fos = null;
        try {
            final FileDto fileDto = shareService.loadFile(fileUuid);
            System.out.println(MessageFormat.format(">Starting download {0} bytes", fileDto
                    .getData().length));
            final File newFile = new File(newPath + File.separator + fileDto.getName());
            if (newFile.exists()) {
                System.out.println(">You already have this file");
            } else {
                newFile.createNewFile();
                fos = new FileOutputStream(newFile);
                fos.write(fileDto.getData());
                System.out.println(">Done. Check " + newFile.getAbsolutePath());
            }
        } catch (FileShareException e) {
            System.out.println(e.getMessage());
        } finally {
            IOUtils.closeQuietly(fos);
        }

    }
}
