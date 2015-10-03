package com.instand.domain.repo;

import com.instand.domain.User;

import java.util.Optional;

public interface UserRepository extends GenericRepository<String, User> {

    /**
     * {@inheritDoc}
     */
    @Override
    default User findOrElseThrow(String id) {
        return find(id).orElseThrow(() -> new EntityNotFoundException(User.class, id));
    }

    /**
     * Finds an user by username.
     *
     * @param username username
     * @return an optional user
     */
    Optional<User> findByUsername(String username);


    /**
     * Returns true if there already exists a user with the given username.
     *
     * @param username username
     * @return true if there already exists a user with the given username
     */
    boolean containsUsername(String username);

    /**
     * Finds an user by account email address.
     *
     * @param emailAddress emailAddress
     * @return an optional user
     */
    Optional<User> findByEmailAddress(String emailAddress);

    /**
     * Returns true if there already exists a user with the given email address.
     *
     * @param emailAddress emailAddress
     * @return true if there already exists a user with the given email address
     */
    boolean containsEmailAddress(String emailAddress);
}
