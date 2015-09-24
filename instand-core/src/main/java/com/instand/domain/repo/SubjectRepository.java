package com.instand.domain.repo;

import com.instand.domain.Subject;

/**
 * Repository for subjects.
 */
public interface SubjectRepository extends GenericRepository<String, Subject> {

    /**
     * Finds the subject of the given id, or throws {@link EntityNotFoundException} if not found.
     *
     * @param id subject id
     * @return the subject
     * @throws EntityNotFoundException if subject is not found for the specified id.
     */
    Subject findOrElseThrow(String id);

}
