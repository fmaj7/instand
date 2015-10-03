package controllers;

import com.instand.app.InstandApplicationService;
import com.instand.app.RegisterUserInput;
import com.instand.domain.User;
import com.instand.domain.repo.EntityAlreadyExistsException;
import ninja.Result;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.time.Instant;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class UserControllerTest {

    @Mock
    InstandApplicationService applicationService;

    UserController controller;

    @Before
    public void setup() {
        controller = new UserController(applicationService);
    }

    @Test
    public void testRegisterUserReturnOkWhenAppServiceReturnUser() {
        RegisterUserInput input = RegisterUserInput.builder().build();
        User user = User.builder().build();
        when(applicationService.registerUser(input)).thenReturn(user);
        Result result = controller.register(input);
        assertThat(result.getStatusCode(), equalTo(Result.SC_200_OK));
    }

    @Test
    public void testRegisterUserReturnBadRequestWhenAppServiceThrow() {
        RegisterUserInput input = RegisterUserInput.builder().build();
        when(applicationService.registerUser(input)).thenThrow(new EntityAlreadyExistsException(""));
        Result result = controller.register(input);
        assertThat(result.getStatusCode(), equalTo(Result.SC_400_BAD_REQUEST));
    }

    @Test
    public void testGetReturnUserDescriptorWhenAppServiceReturnUser() {
        String id = "1234";
        Instant createdAt = Instant.now();
        User user = User.builder()
                .id(id)
                .username("foo")
                .createdAt(createdAt)
                .updatedAt(createdAt)
                .build();
        when(applicationService.getUser(id)).thenReturn(Optional.of(user));
        Result result = controller.get(id);
        assertThat(result.getStatusCode(), equalTo(Result.SC_200_OK));
        assertThat(result.getRenderable(), equalTo(user.toDescriptor()));
    }

    @Test
    public void testGetReturnNotFoundWhenAppServiceReturnOptionalEmpty() {
        String id = "1234";
        when(applicationService.getUser(id)).thenReturn(Optional.empty());
        Result result = controller.get(id);
        assertThat(result.getStatusCode(), equalTo(Result.SC_404_NOT_FOUND));
        assertThat(result.getRenderable(), equalTo(ErrorDescriptor.notFound()));
    }
}
