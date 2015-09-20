package com.instand;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.equalTo;

public class GreeterTest {

    @Test
    public void getGreetingMessage() {
        assertThat(new Greeter().getGreetingMessage(), equalTo("hello instand!"));
    }

}
