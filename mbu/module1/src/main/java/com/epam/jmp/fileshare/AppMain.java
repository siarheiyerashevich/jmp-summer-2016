package com.epam.jmp.fileshare;

import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.services.impl.FileShareServiceImpl;
import com.epam.jmp.fileshare.util.FileShareDateFormatter;
import com.epam.jmp.fileshare.util.UserCommand;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by nbuny on 19.07.2016.
 */
public class AppMain {

    private static Map<Integer, FileDto> allFiles = new HashMap<>();

    public static void main(String[] args) {
        final ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        final FileShareService fileShareService = context.getBean(FileShareServiceImpl.class);

        loadAllFiles(fileShareService);


        final Scanner scanner = new Scanner(System.in);

        UserCommand prevUserCommand = null;
        while (scanner.hasNext()) {
            final String command = scanner.nextLine();
            final UserCommand userCommand = UserCommand.fromString(command);

            if (userCommand == null) {
                printCommandNotFount(command);
                continue;
            }

            try {
                switch (userCommand) {
                    case HELP:
                        printHelp();
                        continue;
                    case LOAD_SELECTED:
                        loadSelectedFile(scanner, fileShareService);
                        continue;
                    case QUIT:
                        return;
                    case SHOW_ALL:
                        showAllFiles(fileShareService);
                        continue;
                    case UPLOAD:
                        uploadFile(scanner, fileShareService);
                        continue;
                }
            } catch (final Exception ex) {
                System.err.println("PANIC!!!" + ex.getMessage());
            }
        }
    }

    private static void uploadFile(final Scanner scanner, final FileShareService fileShareService) throws IOException {

        FileInputStream fis = null;
        System.out.println(">Enter full file path with name which you want to upload");
        final String filePath = scanner.nextLine();
        try {

            final File newFile = new File(filePath);
            if (!newFile.exists()) {
                System.out.println(">Check file path and come back later");
                return;
            }
            fis = new FileInputStream(newFile);
            final byte[] fileData = IOUtils.toByteArray(fis);
            final String[] splittedName = newFile.getName().split("\\.");
            if (splittedName.length <= 1) {
                System.out.println(">Check file path and come back later");
                return;
            }
            fileShareService.saveFile(fileData, splittedName[splittedName.length - 2], splittedName[splittedName.length - 1]);
            System.out.println(">Done");
        } catch (FileShareException e) {
            System.out.println(e.getMessage());
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    private static void loadAllFiles(final FileShareService fileShareService) {
        try {
            final List<FileDto> files = fileShareService.loadAllFiles();
            for (int i = 0; i < files.size(); i++) {
                allFiles.put(i + 1, files.get(i));
            }
        } catch (FileShareException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void showAllFiles(final FileShareService fileShareService) {
        loadAllFiles(fileShareService);
        if (allFiles.isEmpty()) {
            System.out.println(">Be first who upload new file. try to use " + UserCommand.UPLOAD);
        }
        for (final Map.Entry<Integer, FileDto> fileDtoEntry : allFiles.entrySet()) {
            System.out.println(MessageFormat.format(">{0} {1}.{2} will expire in {3}",
                    fileDtoEntry.getKey(),
                    fileDtoEntry.getValue().getName(),
                    fileDtoEntry.getValue().getExtension(),
                    FileShareDateFormatter.format(fileDtoEntry.getValue().getExpirationDate())));
        }
    }

    private static void loadSelectedFile(final Scanner scanner, final FileShareService fileShareService) throws IOException {

        System.out.println(">Which file you want? Enter file number:");
        final Integer fileNumber = scanner.nextInt();
        System.out.println(">Enter path (without file name) for saving:");
        scanner.reset();
        final String newPath = scanner.nextLine();
        if (StringUtils.isEmpty(newPath)) {
            System.out.println(">Check file path and come back later");
            return;
        }
        if (allFiles.containsKey(fileNumber)) {
            FileOutputStream fos = null;
            try {
                final FileDto fileDto = fileShareService.loadFile(allFiles.get(fileNumber).getUuid());
                System.out.println(MessageFormat.format(">Starting download {0} bytes", fileDto.getData().length));
                final File newFile = new File(newPath + File.separator + fileDto.getName() + "." + fileDto.getExtension());
                if (newFile.exists()) {
                    System.out.println(">You already have this file");
                    return;
                } else {
                    newFile.createNewFile();
                    fos = new FileOutputStream(newFile);
                    fos.write(fileDto.getData());
                    IOUtils.closeQuietly(fos);
                    System.out.println(">Done. Check " + newFile.getAbsolutePath());
                }
            } catch (FileShareException e) {
                System.out.println(e.getMessage());
            } finally {
                IOUtils.closeQuietly(fos);
            }
        }
    }

    private static void printCommandNotFount(final String command) {
        System.out.println(">Unrecognized command " + command);
        System.out.println(">Try to use:");
        printCommands();
    }

    private static void printHelp() {
        System.out.println(">Useful commands:");
        printCommands();
    }

    private static void printCommands() {
        for (final UserCommand userCommand : UserCommand.values()) {
            System.out.println(">" + userCommand);
        }
    }
}
