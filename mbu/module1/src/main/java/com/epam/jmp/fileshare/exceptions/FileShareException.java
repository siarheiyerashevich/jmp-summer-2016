package com.epam.jmp.fileshare.exceptions;

/**
 * Created by nbuny on 21.07.2016.
 */
public class FileShareException extends Exception {

    public FileShareException(final String message) {
        super(message);
    }

    public FileShareException(final Exception ex) {
        super(ex);
    }

    public FileShareException(final String message, final Exception ex) {
        super(message, ex);
    }
}
