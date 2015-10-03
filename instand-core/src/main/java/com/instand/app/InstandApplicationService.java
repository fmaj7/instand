package com.instand.app;

import com.instand.domain.Subject;
import com.instand.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * An unified Facade exposing use-case level APIs.
 * <p>
 * It handles application-level concerns, i.e., logic that involves coordination of multiple
 * resources, transactions, security, etc., while delegating domain logic to domain models.
 */
public interface InstandApplicationService {

    /**
     * Gets a user by id.
     *
     * @param id user id
     * @return the user, or empty optional if user is not found
     */
    Optional<User> getUser(String id);

    /**
     * Finds user by username.
     *
     * @param username username
     * @return the user of the given username, or empty optional if user is not found
     */
    Optional<User> findUserByUsername(String username);

    /**
     * Finds user by email address.
     *
     * @param emailAddress email address
     * @return the user of the given email address, or empty optional if user is not found
     */
    Optional<User> findUserByEmailAddress(String emailAddress);

    /**
     * Register a user.
     *
     * @param input input
     * @return the user registered
     */
    User registerUser(RegisterUserInput input);

    /**
     * Checks whether the email address or username of a user matches the given password.
     * <p>
     * Note that it is stateless; it is not to "log in a user within a session" - it merely checks
     * user credentials against user accounts.
     *
     * @param emailOrUsername email address or username
     * @param password password
     * @return true if matches
     */
    boolean authenticateUser(String emailOrUsername, String password);

    /**
     * Finds all users.
     *
     * @return all users
     */
    List<User> findAllUsers();

    /**
     * Creates a subject from the input.
     *
     * @param input input
     * @return the created subject
     */
    Subject createSubject(CreateSubjectInput input);

    /**
     * Gets a subject by id.
     *
     * @param subjectId subject id
     * @return the subject, or empty if subject is not found
     */
    Optional<Subject> getSubject(String subjectId);

    /**
     * Return a list of subjects.
     *
     * @return all subjects
     */
    List<Subject> findAllSubjects();

}
