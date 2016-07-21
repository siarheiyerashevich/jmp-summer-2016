package com.epam.jmp.fileshare.validation.impl;

import com.epam.jmp.fileshare.util.FileShareExtension;
import com.epam.jmp.fileshare.validation.FileShareValidator;
import com.epam.jmp.fileshare.validation.model.FileValidationResult;
import org.springframework.stereotype.Service;

import java.io.File;
import java.text.MessageFormat;

/**
 * Created by nbuny on 20.07.2016.
 * I want to create separate validator for each input parameter
 */
@Service
public class FileShareValidationImpl implements FileShareValidator {

    @Override
    public FileValidationResult validate(final byte[] fileData, final String fileName, final String fileExtension) {

        final FileValidationResult validationResult = new FileValidationResult();

        final FileShareExtension extension = FileShareExtension.getFromString(fileExtension);
        if (extension == null) {
            validationResult.addErrorMessage(MessageFormat.format("Unrecognized file extension {0}", extension));
        }

        // validate file name
        // validate data[]

        return validationResult;
    }
}
