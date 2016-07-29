package com.epam.jmp.fileshare.dto;

import java.util.Date;

/**
 * Created by nbuny on 19.07.2016.
 */
public class FileDto {

    private String uuid;
    private String name;
    private byte[] data;
    private String extension;
    private Date   expirationDate;

    public String getName() {

        return this.name;
    }

    public void setName(String name) {

        this.name = name;
    }

    public byte[] getData() {

        return this.data;
    }

    public void setData(byte[] data) {

        this.data = data;
    }

    public String getExtension() {

        return this.extension;
    }

    public void setExtension(String extension) {

        this.extension = extension;
    }

    public Date getExpirationDate() {

        return expirationDate;
    }

    public void setExpirationDate(Date expirationDate) {

        this.expirationDate = expirationDate;
    }

    public String getUuid() {

        return this.uuid;
    }

    public void setUuid(String uuid) {

        this.uuid = uuid;
    }
}
