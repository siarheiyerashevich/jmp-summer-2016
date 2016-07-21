package com.epam.jmp.fileshare.validation.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
public class FileValidationResult {

    private List<String> errorMessages;
    private boolean success;

    public FileValidationResult() {
        this.errorMessages = new ArrayList<>();
        this.success = true;
    }

    public void addErrorMessage(final String message) {
        this.errorMessages.add(message);
        this.success = false;
    }

    public List<String> getErrorMessages() {
        return this.errorMessages;
    }

    public boolean isSuccess() {
        return this.success;
    }
}
