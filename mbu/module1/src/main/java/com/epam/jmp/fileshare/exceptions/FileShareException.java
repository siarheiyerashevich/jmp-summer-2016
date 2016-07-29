package com.epam.jmp.fileshare.exceptions;

/**
 * Created by nbuny on 21.07.2016.
 */
public class FileShareException extends Exception {

    private static final long serialVersionUID = -7504995196930129153L;

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
