package io.prep.core.util;

import java.util.UUID;

public class UniqueIdentityGenerator {
    /**
     * UUID v4 기반 고유 ID를 생성
     * @return
     */
    public static UUID generate() {
        return UUID.randomUUID();
    }
}
