package com.epam.jmp.fileshare.dao.impl;

import com.epam.jmp.fileshare.dao.FileShareDao;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.dto.FileShareExtension;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by nbuny on 20.07.2016.
 */
@Repository
public class DiskFileShareDaoImpl implements FileShareDao {

    @Override
    public List<FileDto> loadAllFiles() {
        return null;
    }

    @Override
    public FileDto loadFile(Long id) {
        return null;
    }

    @Override
    public List<FileDto> loadFilesByExtension(FileShareExtension extension) {
        return null;
    }

    @Override
    public void saveFile(FileDto fileDto) {

    }
}
