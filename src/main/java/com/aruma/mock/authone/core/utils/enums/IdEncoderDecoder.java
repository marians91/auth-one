package com.aruma.mock.authone.core.utils.enums;

import java.nio.ByteBuffer;
import java.util.Base64;

public class IdEncoderDecoder {
    public static String encodeId(Long id) {
        byte[] idBytes = ByteBuffer.allocate(Long.BYTES).putLong(id).array();
        return Base64.getUrlEncoder().withoutPadding().encodeToString(idBytes);
    }
    
    public static Long decodeId(String encodedId) {
        byte[] idBytes = Base64.getUrlDecoder().decode(encodedId);
        return ByteBuffer.wrap(idBytes).getLong();
    }

}
