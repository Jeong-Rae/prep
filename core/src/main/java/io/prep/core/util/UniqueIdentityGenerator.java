package io.prep.core.util;

import lombok.experimental.UtilityClass;

import java.util.UUID;

@UtilityClass
public class UniqueIdentityGenerator {
    /**
     * UUID v4 기반 고유 ID를 생성
     *
     * @return UUID uid
     */
    public UUID generate() {
        return UUID.randomUUID();
    }
}
