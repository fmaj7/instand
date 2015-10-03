package com.instand.app;

import com.instand.domain.User;
import com.instand.domain.repo.EntityAlreadyExistsException;
import com.instand.domain.repo.SubjectRepository;
import com.instand.domain.repo.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.junit.Assert.*;
import static org.mockito.Matchers.notNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class InstandApplicationServiceImplTest {

    @Mock
    UserRepository userRepository;

    @Mock
    SubjectRepository subjectRepository;


    InstandApplicationServiceImpl applicationService;

    @Before
    public void setup() {
        applicationService = new InstandApplicationServiceImpl(userRepository, subjectRepository);
    }

    @Test
    public void testRegisterUserShouldReturnUserWhenRepositoryReturnUser() throws Exception {
        RegisterUserInput input = RegisterUserInput.builder()
                .username("foo")
                .emailAddress("foo@gmail.com")
                .password("bar")
                .build();
        User user = User.builder().build();
        when(userRepository.create(user)).thenReturn(user);
        User out = applicationService.registerUser(input);
        assertThat(out.getUsername(), equalTo("foo"));
        assertThat(out.getAccount().get().getEmailAddress(), equalTo("foo@gmail.com"));
        assertThat(out.getId(), notNullValue());
        verify(userRepository, times(1)).containsEmailAddress("foo@gmail.com");
        verify(userRepository, times(1)).containsUsername("foo");
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testRegisterUserShouldReturnThrowWhenRepositoryContainsEmailAddressReturnTrue() throws Exception {
        RegisterUserInput input = RegisterUserInput.builder()
                .username("foo")
                .emailAddress("foo@gmail.com")
                .password("bar")
                .build();
        when(userRepository.containsEmailAddress("foo@gmail.com")).thenReturn(true);
        applicationService.registerUser(input);
    }

    @Test(expected = EntityAlreadyExistsException.class)
    public void testRegisterUserShouldReturnThrowWhenRepositoryContainsUsernameReturnTrue() throws Exception {
        RegisterUserInput input = RegisterUserInput.builder()
                .username("foo")
                .emailAddress("foo@gmail.com")
                .password("bar")
                .build();
        when(userRepository.containsUsername("foo")).thenReturn(true);
        applicationService.registerUser(input);
    }

    @Test
    public void testAuthenticateUser() throws Exception {

    }

    @Test
    public void testGetUser() throws Exception {

    }

    @Test
    public void testFindUserByUsername() throws Exception {

    }

    @Test
    public void testFindUserByEmailAddress() throws Exception {

    }

    @Test
    public void testFindAllUsers() throws Exception {

    }

    @Test
    public void testCreateSubject() throws Exception {

    }

    @Test
    public void testGetSubject() throws Exception {

    }

    @Test
    public void testFindAllSubjects() throws Exception {

    }
}