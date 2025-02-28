package io.prep.core.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class FilenameUtils {
    public String getExtension(String filename) {
        int lastDotIndex = filename.lastIndexOf(".");
        if (lastDotIndex == -1) {
            return "";
        }
        return filename.substring(lastDotIndex + 1);
    }
}
