package com.epam.jmp.fileshare.validation;

import com.epam.jmp.fileshare.validation.model.FileValidationResult;

/**
 * Created by nbuny on 20.07.2016.
 */
public interface FileShareValidator {

    FileValidationResult validate(final byte[] fileData, final String fileName,
            final String fileExtension);
}
