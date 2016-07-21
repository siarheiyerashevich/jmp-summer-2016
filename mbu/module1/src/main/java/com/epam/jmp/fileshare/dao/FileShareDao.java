package com.epam.jmp.fileshare.dao;

import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.util.FileShareExtension;

import java.io.IOException;
import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
public interface FileShareDao {

    List<FileDto> loadAllFiles() throws FileShareException;

    FileDto loadFile(final String uuid) throws FileShareException;

    void saveFile(final FileDto fileDto) throws FileShareException;
}
