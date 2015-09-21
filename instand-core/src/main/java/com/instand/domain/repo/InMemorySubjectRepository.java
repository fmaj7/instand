package com.instand.domain.repo;

import com.instand.domain.Subject;

/**
 * In memory implementation of {@link SubjectRepository}.
 */
public class InMemorySubjectRepository extends InMemoryGenericRepository<String, Subject> implements SubjectRepository {

    /**
     * {@inheritDoc}
     */
    @Override
    public Subject findOrElseThrow(String id) {
        return find(id).orElseThrow(() -> new EntityNotFoundException(Subject.class, id));
    }
}
