package com.epam.jmp.fileshare.util;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

/**
 * Created by nbuny on 19.07.2016.
 */
public enum FileShareExtension {

    JPG("JPEG", "JPG"), PNG("PNG"), BMP("BMP"), DOC("DOC", "DOCX"), PDF("PDF"), ZIP("ZIP");

    private final List<String> extensions;

    private FileShareExtension(final String... extensions) {

        this.extensions = new ArrayList<>();
        for (final String code : extensions) {
            this.extensions.add(code);
        }
    }

    public static FileShareExtension getFromString(final String extension) {

        if (StringUtils.isEmpty(extension)) {
            throw new IllegalStateException("Where is file extension?");
        }
        for (final FileShareExtension fileShareExtension : FileShareExtension.values()) {
            if (fileShareExtension.extensions.contains(extension.toUpperCase())) {
                return fileShareExtension;
            }
        }
        throw new IllegalStateException(MessageFormat.format("Not supported file extension {0}",
                extension));
    }
}
