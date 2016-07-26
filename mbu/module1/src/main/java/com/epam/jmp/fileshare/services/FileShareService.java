package com.epam.jmp.fileshare.services;

import java.util.List;

import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;

/**
 * Created by nbuny on 20.07.2016.
 */
public interface FileShareService {

    List<FileDto> loadAllFiles() throws FileShareException;

    FileDto loadFile(final String uuid) throws FileShareException;

    void saveFile(final FileDto fileDto) throws FileShareException;
}
