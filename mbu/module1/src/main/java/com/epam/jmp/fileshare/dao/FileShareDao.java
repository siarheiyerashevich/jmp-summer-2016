package com.epam.jmp.fileshare.dao;

import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.dto.FileShareExtension;

import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
public interface FileShareDao {

    List<FileDto> loadAllFiles();

    FileDto loadFile(final Long id);

    List<FileDto> loadFilesByExtension(final FileShareExtension extension);

    void saveFile(final FileDto fileDto);
}
