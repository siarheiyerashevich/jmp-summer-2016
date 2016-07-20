package com.epam.jmp.fileshare.services.impl;

import com.epam.jmp.fileshare.dao.FileShareDao;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.dto.FileShareExtension;
import com.epam.jmp.fileshare.services.FileShareService;
import com.epam.jmp.fileshare.util.FileShareConverter;
import com.epam.jmp.fileshare.validation.FileShareValidator;
import com.epam.jmp.fileshare.validation.model.FileValidationResult;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
public class FileShareServiceImpl implements FileShareService {


    @Autowired
    private FileShareValidator validator;

    @Autowired
    private FileShareDao shareDao;

    @Override
    public List<FileDto> loadAllFiles() {
        return this.shareDao.loadAllFiles();
    }

    @Override
    public FileDto loadFile(final Long id) {
        return this.shareDao.loadFile(id);
    }

    @Override
    public List<FileDto> loadFilesByExtension(final String extension) {
        final FileShareExtension fileExtension = FileShareExtension.getFromString(extension);
        if (fileExtension == null) {
            return Collections.emptyList();
        }
        return this.shareDao.loadFilesByExtension(fileExtension);
    }

    @Override
    public void saveFile(final byte[] fileData, final String fileName, final String fileExtension) {

        final FileValidationResult validationResult = this.validator.validate(fileData, fileName, fileExtension);
        if (validationResult.isSuccess()) {
            this.shareDao.saveFile(FileShareConverter.convertToDto(fileData, fileName, fileExtension));
        }
    }
}
