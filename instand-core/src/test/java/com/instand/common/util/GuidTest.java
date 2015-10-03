package com.instand.common.util;

import org.junit.Test;

import java.util.UUID;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.*;

public class GuidTest {

    @Test
    public void testRandomBase64UrlSafe() {
        String s = Guid.randomBase64UrlSafe();
        assertThat(s.length(), equalTo(22));
    }

    @Test
    public void testRandomBase32() {
        String s = Guid.randomBase32();
        assertThat(s.length(), equalTo(26));
    }
}