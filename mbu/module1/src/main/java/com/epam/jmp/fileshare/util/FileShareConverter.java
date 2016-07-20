package com.epam.jmp.fileshare.util;

import com.epam.jmp.fileshare.dto.FileDto;

/**
 * Created by nbuny on 20.07.2016.
 */
public class FileShareConverter {

    public static FileDto convertToDto(final byte[] fileData, final String fileName, final String fileExtension) {
        final FileDto fileDto = new FileDto();

        fileDto.setData(fileData);
        fileDto.setExtension(fileExtension);
        fileDto.setName(fileName);

        return fileDto;
    }
}
