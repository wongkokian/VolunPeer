package com.project.volunpeer.common.util;

import com.project.volunpeer.common.enums.KeyType;

import java.util.UUID;

public class KeyGeneratorUtil {
    public String generateKey(KeyType keyType) {
        return keyType.getName() + "_" + UUID.randomUUID();
    }
}
