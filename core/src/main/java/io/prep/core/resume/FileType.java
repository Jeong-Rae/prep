package io.prep.core.resume;

import java.util.Arrays;

public enum FileType {
    PDF("pdf");

    private final String extension;

    FileType(String extension) {
        this.extension = extension;
    }

    public static FileType fromExtension(String extension) {
        return Arrays.stream(FileType.values())
                     .filter(type -> type.extension.equalsIgnoreCase(extension))
                     .findFirst()
                     .orElseThrow(() -> new IllegalArgumentException("Invalid file extension: " + extension));
    }
}
