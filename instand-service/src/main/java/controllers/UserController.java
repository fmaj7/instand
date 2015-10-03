package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.instand.app.InstandApplicationService;
import com.instand.app.RegisterUserInput;
import com.instand.domain.User;
import com.instand.domain.UserDescriptor;
import com.instand.domain.repo.EntityAlreadyExistsException;
import lombok.NonNull;
import ninja.BasicAuthFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Controller of users.
 */
@Singleton
@FilterWith(BasicAuthFilter.class)
public class UserController {

    /**
     * Underlying application service facade.
     */
    private final InstandApplicationService service;

    @Inject
    public UserController(@NonNull InstandApplicationService service) {
        this.service = service;
    }

    /**
     * Creates an user.
     *
     * @param input RegisterUserInput
     * @return ninja result
     */
    public Result register(@NonNull RegisterUserInput input) {
        try {
            User user = service.registerUser(input);
            return Results.json()
                    .render(user.toDescriptor());

        } catch (EntityAlreadyExistsException e) {
            return Results.json()
                    .status(Result.SC_400_BAD_REQUEST)
                    .render(ErrorDescriptor.alreadyExists(e.getMessage()));
        }
    }


    /**
     * Gets an user by id.
     *
     * @param id user id
     * @return ninja result
     */
    public Result get(@NonNull @PathParam("id") String id) {
        Optional<User> optUser = service.getUser(id);
        return renderResult(optUser);
    }

    /**
     * Finds user by username.
     *
     * @param username username
     * @return ninja result
     */
    public Result findByUsername(@NonNull @PathParam("username") String username) {
        Optional<User> optUser = service.findUserByUsername(username);
        return renderResult(optUser);
    }

    /**
     * Finds user by email address.
     *
     * @param emailAddress email address
     * @return ninja result
     */
    public Result findByEmailAddress(@NonNull @PathParam("emailAddress") String emailAddress) {
        Optional<User> optUser = service.findUserByEmailAddress(emailAddress);
        return renderResult(optUser);
    }

    /**
     * Finds all users.
     *
     * @return ninja result
     */
    public Result findAll() {
        List<User> users = service.findAllUsers();
        return renderResult(users);
    }

    private Result renderResult(User user) {
        return Results.json().render(user.toDescriptor());
    }

    private Result renderResult(Optional<User> optUser) {
        if (!optUser.isPresent()) {
            return Results.json()
                    .status(Result.SC_404_NOT_FOUND)
                    .render(ErrorDescriptor.notFound());
        }
        return renderResult(optUser.get());
    }

    private Result renderResult(List<User> users) {
        List<UserDescriptor> ud = users.stream()
                .map(User::toDescriptor)
                .collect(Collectors.toList());
        return Results.json().render(ud);
    }
}
