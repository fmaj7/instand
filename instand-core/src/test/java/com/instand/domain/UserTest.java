package com.instand.domain;

import com.instand.common.util.Guid;
import org.junit.Test;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

public class UserTest {

    @Test
    public void testBuilderBuild() {
        User user = User.builder()
                .id(Guid.randomBase64UrlSafe())
                .createdAt(Instant.now())
                .username("guest-user-123456")
                .account(Optional.empty())
                .build();
        assertThat(user.getAccount(), equalTo(Optional.empty()));
    }
}


