package com.instand.domain.repo;

import com.instand.domain.Interpretation;
import com.instand.domain.User;

import java.util.List;
import java.util.Optional;

/**
 * Repository for interpretations.
 */
public interface InterpretationRepository extends GenericRepository<String, Interpretation> {

    /**
     * {@inheritDoc}
     */
    @Override
    default Interpretation findOrElseThrow(String id) {
        return find(id).orElseThrow(() -> new EntityNotFoundException(Interpretation.class, id));
    }

    /**
     * Finds by interpreting subject id.
     *
     * @param subjectId subjectId
     * @return list of interpretations
     */
    List<Interpretation> findByInterpretingSubjectId(String subjectId);
}
