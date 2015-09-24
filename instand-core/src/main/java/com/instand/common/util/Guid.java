package com.instand.common.util;

import com.google.common.io.BaseEncoding;
import lombok.experimental.UtilityClass;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 */
@UtilityClass
public final class Guid {

    public static String randomBase64() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        String s = Base64.getUrlEncoder().encodeToString(bb.array());
        return s.replaceAll("=", "").toLowerCase();
    }

    public static String randomBase32() {
        UUID uuid = UUID.randomUUID();
        ByteBuffer bb = ByteBuffer.wrap(new byte[16]);
        bb.putLong(uuid.getMostSignificantBits());
        bb.putLong(uuid.getLeastSignificantBits());
        String s = BaseEncoding.base32().encode(bb.array());
        return s.replaceAll("=", "").toLowerCase();
    }
}
