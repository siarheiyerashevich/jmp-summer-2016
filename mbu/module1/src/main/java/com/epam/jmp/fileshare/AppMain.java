package com.epam.jmp.fileshare;

import java.util.Scanner;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.epam.jmp.fileshare.commands.UserCommand;
import com.epam.jmp.fileshare.commands.UserCommandFactory;
import com.epam.jmp.fileshare.commands.UserCommandType;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.services.impl.FileShareServiceImpl;

/**
 * Created by nbuny on 19.07.2016.
 */
public class AppMain {

    public static void main(String[] args) {

        final ApplicationContext context = new ClassPathXmlApplicationContext("spring-config.xml");
        final FileShareService fileShareService = context.getBean(FileShareServiceImpl.class);

        final Scanner scanner = new Scanner(System.in);
        while (true) {

            final String command = scanner.nextLine();
            final UserCommandType userCommandType = UserCommandType.fromString(command);
            final UserCommand userCommand = UserCommandFactory.getCommandByType(userCommandType);
            try {
                userCommand.execute(fileShareService);
            } catch (final Exception ex) {
                System.err.println("PANIC!!!" + ex.getMessage());
            }
        }
    }
}
