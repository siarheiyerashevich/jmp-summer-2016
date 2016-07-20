package com.epam.jmp.fileshare.validation.impl;

import com.epam.jmp.fileshare.validation.FileShareValidator;
import com.epam.jmp.fileshare.validation.model.FileValidationResult;
import org.springframework.stereotype.Service;

import java.io.File;

/**
 * Created by nbuny on 20.07.2016.
 */
@Service
public class FileShareValidationImpl implements FileShareValidator {

    @Override
    public FileValidationResult validate(byte[] fileData, String fileName, String fileExtension) {
        return new FileValidationResult();
    }
}
