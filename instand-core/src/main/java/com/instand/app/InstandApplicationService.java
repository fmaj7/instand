package com.instand.app;

import com.instand.domain.Subject;

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
