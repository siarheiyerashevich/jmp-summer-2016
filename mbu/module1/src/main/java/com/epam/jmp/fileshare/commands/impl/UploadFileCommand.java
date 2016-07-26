package com.epam.jmp.fileshare.commands.impl;

import java.io.File;
import java.io.FileInputStream;
import java.util.Scanner;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.util.FileShareFactory;

/**
 * Created by Mikalai_Bunis on 7/26/2016.
 */
public class UploadFileCommand implements UserCommand {

    @Override
    public void execute(final FileShareService shareService) throws Exception {

        final Scanner scanner = new Scanner(System.in);

        FileInputStream fis = null;
        System.out.println(">Enter full file path with name which you want to upload");
        final String filePath = scanner.nextLine();
        try {

            final File newFile = new File(filePath);
            if (!newFile.exists() || newFile.isDirectory()) {
                System.out.println(">Check file path and come back later");
                return;
            }

            final String fileExtension = FilenameUtils.getExtension(filePath);
            final String fileName = FilenameUtils.getName(filePath);

            fis = new FileInputStream(newFile);
            final byte[] fileData = IOUtils.toByteArray(fis);
            final FileDto fileDto = FileShareFactory.buildDto(fileData, fileName, fileExtension);
            shareService.saveFile(fileDto);
            System.out.println(">Done");
        } catch (FileShareException e) {
            System.out.println(e.getMessage());
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }
}
