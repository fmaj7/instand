package com.instand.domain.repo;

import com.instand.domain.Subject;

/**
 * Repository for subjects.
 */
public interface SubjectRepository extends GenericRepository<String, Subject> {

    /**
     * {@inheritDoc}
     */
    @Override
    default Subject findOrElseThrow(String id) {
        return find(id).orElseThrow(() -> new EntityNotFoundException(Subject.class, id));
    }
}
