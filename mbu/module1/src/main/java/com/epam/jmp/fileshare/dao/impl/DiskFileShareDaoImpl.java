package com.epam.jmp.fileshare.dao.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Properties;
import java.util.UUID;

import javax.annotation.PostConstruct;

import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.epam.jmp.fileshare.dao.FileShareDao;
import com.epam.jmp.fileshare.dto.FileDto;
import com.epam.jmp.fileshare.exceptions.FileShareException;
import com.epam.jmp.fileshare.util.FileShareDateFormatter;

/**
 * Created by nbuny on 20.07.2016.
 */
@Repository
public class DiskFileShareDaoImpl implements FileShareDao {

    private static final String NAME_PROPERTY_POSTFIX          = ".name";
    private static final String EXTENSION_PROPERTY_POSTFIX     = ".extension";
    private static final String EXPIRATION_PROPERTY_POSTFIX    = ".expiration";

    private static final String PROPERTY_FILE_EXTENSION        = ".properties";
    private static final String REPOSITORY_LOCATION            = "repositoryLocation";
    private static final String REPOSITORY_NAME                = "repositoryName";
    private static final String DEFAULT_FILE_EXPIRATION_PERIOD = "defaultFileExpirationPeriod";
    @Autowired
    @Qualifier("disk-dao.properties")
    private Properties          defaultRepositoryProperties;
    private Properties          repositoryProperties;

    private String              repositoryLocation;
    private String              repositoryName;
    private int                 defaultFileExpirationPeriod;

    @PostConstruct
    private void init() throws FileShareException {

        if (this.defaultRepositoryProperties == null
                || CollectionUtils.isEmpty(this.defaultRepositoryProperties.values())) {
            throw new FileShareException("Repository Properties not exists");
        }

        this.repositoryLocation = this.defaultRepositoryProperties.getProperty(REPOSITORY_LOCATION);
        this.repositoryName = this.defaultRepositoryProperties.getProperty(REPOSITORY_NAME);
        this.defaultFileExpirationPeriod = Integer.valueOf(this.defaultRepositoryProperties
                .getProperty(DEFAULT_FILE_EXPIRATION_PERIOD));

        final File repository = new File(getRepositoryPath());
        if (!repository.exists()) {
            repository.mkdirs();
        }

        final String repositoryPropertiesPath = getRepositoryPropertiesPath();
        final File repositoryPropertiesFile = new File(repositoryPropertiesPath);
        if (!repositoryPropertiesFile.exists()) {
            try {
                repositoryPropertiesFile.createNewFile();
            } catch (final IOException ex) {
                throw new FileShareException("Failed to create repository property file", ex);
            }
        }

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(repositoryPropertiesPath);
            this.repositoryProperties = new Properties();
            this.repositoryProperties.load(fis);
        } catch (final Exception ex) {
            throw new FileShareException("Failed to load repository properties", ex);
        } finally {
            IOUtils.closeQuietly(fis);
        }
    }

    @Override
    public List<FileDto> loadAllFiles() throws FileShareException {

        final File repositoryFolder = new File(getRepositoryPath());
        final String[] repositoryUuids = repositoryFolder.list(new FilenameFilter() {

            @Override
            public boolean accept(final File dir, final String name) {

                return isUUID(name);
            }

            private boolean isUUID(final String name) {

                try {
                    UUID.fromString(name);
                    return true;
                } catch (Exception ex) {
                    return false;
                }
            }
        });

        final List<FileDto> result = new ArrayList<>(repositoryUuids.length);
        for (final String repositoryUuid : repositoryUuids) {
            final FileDto dto = getFileDto(repositoryUuid);
            if (new Date().before(dto.getExpirationDate())) {
                result.add(dto);
            }
        }

        return result;
    }

    @Override
    public FileDto loadFile(final String uuid) throws FileShareException {

        final String filePath = getRepositoryPath() + File.separator + uuid;
        final File file = new File(filePath);
        if (!file.exists()) {
            throw new FileShareException(MessageFormat.format("File {0} not found", uuid));
        }

        final FileDto dto = getFileDto(uuid);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
            final byte[] fileData = IOUtils.toByteArray(fis);
            dto.setData(fileData);
        } catch (final Exception ex) {
            throw new FileShareException(MessageFormat.format("Failed to get file data for {0}",
                    uuid), ex);
        } finally {
            IOUtils.closeQuietly(fis);
        }

        return dto;
    }

    @Override
    public void saveFile(final FileDto fileDto) throws FileShareException {

        FileOutputStream fileOutputStream = null;
        FileOutputStream propertyOutputStream = null;

        try {
            final String storedFileKey = UUID.randomUUID().toString();
            final String storedFilePath = getRepositoryPath() + File.separator + storedFileKey;
            final File storedFile = new File(storedFilePath);
            storedFile.createNewFile();
            fileOutputStream = new FileOutputStream(storedFile);
            fileOutputStream.write(fileDto.getData());

            this.repositoryProperties.setProperty(storedFileKey + NAME_PROPERTY_POSTFIX, fileDto
                    .getName());
            this.repositoryProperties.setProperty(storedFileKey + EXTENSION_PROPERTY_POSTFIX,
                    fileDto.getExtension());
            this.repositoryProperties.setProperty(storedFileKey + EXPIRATION_PROPERTY_POSTFIX,
                    FileShareDateFormatter.format(getFileExpirationDate()));
            propertyOutputStream = new FileOutputStream(getRepositoryPropertiesPath());
            this.repositoryProperties.store(propertyOutputStream, null);

        } catch (final IOException ex) {
            throw new FileShareException(MessageFormat.format("Failed to save file {0}", fileDto
                    .getName()), ex);
        } finally {
            IOUtils.closeQuietly(fileOutputStream);
            IOUtils.closeQuietly(propertyOutputStream);
        }
    }

    private Date getFileExpirationDate() {

        final Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        calendar.add(Calendar.DAY_OF_MONTH, this.defaultFileExpirationPeriod);
        return calendar.getTime();
    }

    private String getRepositoryPath() {

        return this.repositoryLocation + File.separator + this.repositoryName;
    }

    private String getRepositoryPropertiesPath() {

        return getRepositoryPath() + File.separator + this.repositoryName + PROPERTY_FILE_EXTENSION;
    }

    private FileDto getFileDto(String repositoryUuid) throws FileShareException {

        try {
            final FileDto dto = new FileDto();
            dto.setName(this.repositoryProperties.getProperty(repositoryUuid
                    + NAME_PROPERTY_POSTFIX));
            dto.setExtension(this.repositoryProperties.getProperty(repositoryUuid
                    + EXTENSION_PROPERTY_POSTFIX));
            dto.setExpirationDate(FileShareDateFormatter.parse(this.repositoryProperties
                    .getProperty(repositoryUuid + EXPIRATION_PROPERTY_POSTFIX)));
            dto.setUuid(repositoryUuid);
            return dto;
        } catch (final Exception ex) {
            throw new FileShareException(MessageFormat.format("Failed to get properties for {0}",
                    repositoryUuid), ex);
        }
    }
}
