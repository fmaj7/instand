package com.instand.domain.repo.mem;

import com.instand.domain.Subject;
import com.instand.domain.repo.EntityNotFoundException;
import com.instand.domain.repo.SubjectRepository;

/**
 * In-memory implementation of {@link SubjectRepository}.
 */
public class InMemorySubjectRepository extends InMemoryGenericRepository<String, Subject> implements SubjectRepository {

}
