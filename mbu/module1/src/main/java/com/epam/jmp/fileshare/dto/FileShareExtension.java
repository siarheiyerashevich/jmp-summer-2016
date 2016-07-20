package com.epam.jmp.fileshare.dto;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by nbuny on 19.07.2016.
 */
public enum FileShareExtension {

    JPG("JPEG", "JPG"),
    PNG("PNG"),
    BMP("BMP"),
    DOC("DOC", "DOCX"),
    PDF("PDF"),
    ZIP("ZIP");

    private final List<String> extensions;

    private FileShareExtension(final String... extensions) {
        this.extensions = new ArrayList<>();
        for (final String code : extensions) {
            this.extensions.add(code);
        }
    }

    public static FileShareExtension getFromString(final String extension) {
        for (final FileShareExtension fileShareExtension : FileShareExtension.values()) {
            if (fileShareExtension.extensions.contains(extension)) {
                return fileShareExtension;
            }
        }
        throw new IllegalStateException(MessageFormat.format("Not supported file extension {0}", extension));
    }
}
