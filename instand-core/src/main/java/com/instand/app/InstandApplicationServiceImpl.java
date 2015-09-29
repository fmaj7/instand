package com.instand.app;

import com.google.inject.Inject;
import com.instand.common.util.Guid;
import com.instand.domain.Subject;
import com.instand.domain.repo.SubjectRepository;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

/**
 * Standard implementation of {@link InstandApplicationService}.
 */
@RequiredArgsConstructor(onConstructor=@__(@Inject))
public class InstandApplicationServiceImpl implements InstandApplicationService {

    @Inject
    private final SubjectRepository subjectRepo;

    /**
     * {@inheritDoc}
     */
    @Override
    public Subject createSubject(CreateSubjectInput input) {
        Subject subject = Subject.builder()
                .id(Guid.randomBase64UrlSafe())
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
