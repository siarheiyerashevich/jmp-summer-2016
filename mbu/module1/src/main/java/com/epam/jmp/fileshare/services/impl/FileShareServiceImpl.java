package com.epam.jmp.fileshare.services.impl;

import com.epam.jmp.fileshare.dao.FileShareDao;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.util.FileShareConverter;
import com.epam.jmp.fileshare.validation.FileShareValidator;
import com.epam.jmp.fileshare.validation.model.FileValidationResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
@Service
public class FileShareServiceImpl implements FileShareService {


    @Autowired
    private FileShareValidator validator;

    @Autowired
    private FileShareDao shareDao;

    @Override
    public List<FileDto> loadAllFiles() throws FileShareException {
        return this.shareDao.loadAllFiles();
    }

    @Override
    public FileDto loadFile(final String uuid) throws FileShareException {
        return this.shareDao.loadFile(uuid);
    }

    @Override
    public void saveFile(final byte[] fileData, final String fileName, final String fileExtension) throws FileShareException {

        final FileValidationResult validationResult = this.validator.validate(fileData, fileName, fileExtension);
        if (validationResult.isSuccess()) {
            this.shareDao.saveFile(FileShareConverter.convertToDto(fileData, fileName, fileExtension));
        } else {
            final StringBuilder sb = new StringBuilder();
            for (final String message : validationResult.getErrorMessages()) {
                sb.append(message).append("\n");
            }
            throw new FileShareException(MessageFormat.format("Validation failed. See details:\n{0}", sb.toString()));
        }
    }
}
