package com.epam.jmp.fileshare.services;

import com.epam.jmp.fileshare.dto.FileDto;

import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
public interface FileShareService {

    List<FileDto> loadAllFiles();

    FileDto loadFile(final Long id);

    List<FileDto> loadFilesByExtension(final String extension);

    void saveFile(final byte[] fileData, final String fileName, final String fileExtension);
}
