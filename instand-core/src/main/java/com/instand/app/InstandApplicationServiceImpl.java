package com.instand.app;

import com.google.inject.Inject;
import com.instand.common.util.Guid;
import com.instand.domain.Subject;
import com.instand.domain.User;
import com.instand.domain.UserAccount;
import com.instand.domain.repo.EntityAlreadyExistsException;
import com.instand.domain.repo.SubjectRepository;
import com.instand.domain.repo.UserRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;

/**
 * Standard implementation of {@link InstandApplicationService}.
 */
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class InstandApplicationServiceImpl implements InstandApplicationService {

    @Inject
    private final UserRepository userRepo;

    @Inject
    private final SubjectRepository subjectRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public User registerUser(RegisterUserInput input) {
        UserAccount userAccount = UserAccount.builder()
                .emailAddress(input.getEmailAddress())
                .passwordHash(input.getPassword()) // TODO: hash password
                .build();

        Instant now = Instant.now();
        User user = User.builder()
                .id(Guid.randomBase32())
                .createdAt(now)
                .updatedAt(now)
                .username(input.getUsername())
                .account(Optional.of(userAccount))
                .build();

        if (userRepo.containsEmailAddress(input.getEmailAddress())) {
            String msg = format("User with email address [%s] already exists", input.getEmailAddress());
            throw new EntityAlreadyExistsException(msg);
        }
        if (userRepo.containsUsername(input.getUsername())) {
            String msg = format("User with username [%s] already exists", input.getUsername());
            throw new EntityAlreadyExistsException(msg);
        }
        userRepo.create(user);
        return user;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean authenticateUser(String emailOrUsername, String password) {
        Optional<User> user;
        // check if it is an email. FIXME: need the real, robust email check
        if (emailOrUsername.contains("@")) {
            user = findUserByEmailAddress(emailOrUsername);
        } else {
            user = findUserByUsername(emailOrUsername);
        }
        if (!user.isPresent()) {
            throw new RuntimeException("No user was found for " + emailOrUsername);
        }
        User u = user.get();
        if (!u.getAccount().isPresent()) {
            throw new RuntimeException("No registered user was found for " + emailOrUsername);
        }
        UserAccount userAccount = u.getAccount().get();
        return userAccount.matchesPassword(password);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> getUser(String id) {
        return userRepo.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserByUsername(String username) {
        return userRepo.findByUsername(username);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<User> findUserByEmailAddress(String emailAddress) {
        return userRepo.findByEmailAddress(emailAddress);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<User> findAllUsers() {
        return userRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Subject createSubject(CreateSubjectInput input) {
        Subject subject = Subject.builder()
                .id(Guid.randomBase32())
                .createdByUserId(input.getCreatedByUserId())
                .createdAt(Instant.now())
                .title(input.getTitle())
                .build();
        subjectRepo.create(subject);
        return subject;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Subject> getSubject(String subjectId) {
        return subjectRepo.find(subjectId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subject> findAllSubjects() {
        return subjectRepo.findAll();
    }
}
