package io.prep.core.resume;

import java.util.Arrays;

public enum FileType {
    PDF;

    public static boolean isValid(String fileType) {
        return Arrays.stream(FileType.values()).anyMatch(type -> type.name().equalsIgnoreCase(fileType));
    }
}
