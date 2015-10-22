package com.instand.app;

import com.google.inject.Inject;
import com.instand.common.util.Guid;
import com.instand.domain.Interpretation;
import com.instand.domain.Subject;
import com.instand.domain.User;
import com.instand.domain.UserAccount;
import com.instand.domain.repo.*;
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

    @Inject
    private final InterpretationRepository interpretationRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public User createUser(CreateUserInput input) {
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
    public Optional<User> getUser(String id) {
        return userRepo.find(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean existsUser(String id) {
        return userRepo.exists(id);
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
        String createdByUserId = input.getCreatedByUserId();
        if (!existsUser(createdByUserId)) {
            String msg = String.format("Cannot find user with createdByUserId %s", createdByUserId);
            throw new IllegalArgumentException(msg);
        }
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
    public boolean existsSubject(String id) {
        return subjectRepo.exists(id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Subject> findAllSubjects() {
        return subjectRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Interpretation createInterpretation(CreateInterpretationInput input) {
        String createdByUserId = input.getCreatedByUserId();
        if (!existsUser(createdByUserId)) {
            String msg = String.format("Cannot find user with createdByUserId %s", createdByUserId);
            throw new IllegalArgumentException(msg);
        }
        String interpretingSubjectId = input.getInterpretingSubjectId();
        if (!existsSubject(interpretingSubjectId)) {
            String msg = String.format("Cannot find subject with interpretingSubjectId %s", interpretingSubjectId);
            throw new IllegalArgumentException(msg);
        }

        Instant now = Instant.now();
        Interpretation interpretation = Interpretation.builder()
                .id(Guid.randomBase32())
                .createdAt(now)
                .updatedAt(now)
                .createdByUserId(createdByUserId)
                .interpretingSubjectId(interpretingSubjectId)
                .content(input.getContent())
                .imageUri(input.getImageUri())
                .build();
        interpretationRepo.create(interpretation);
        return interpretation;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Optional<Interpretation> getInterpretation(String interpretationId) {
        return interpretationRepo.find(interpretationId);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Interpretation> findAllInterpretations() {
        return interpretationRepo.findAll();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<Interpretation> findByInterpretingSubjectId(String subjectId) {
        if (!subjectRepo.exists(subjectId)) {
            String msg = format("Subject with id [%s] does not exist", subjectId);
            throw new EntityNotFoundException(msg);
        }
        return interpretationRepo.findByInterpretingSubjectId(subjectId);
    }
}
