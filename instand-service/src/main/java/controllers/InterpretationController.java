package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.instand.app.CreateInterpretationInput;
import com.instand.app.CreateSubjectInput;
import com.instand.app.InstandApplicationService;
import com.instand.domain.Interpretation;
import com.instand.domain.Subject;
import com.instand.domain.repo.EntityNotFoundException;
import lombok.NonNull;
import ninja.BasicAuthFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;

/**
 * Controller of interpretations.
 */
@Singleton
@FilterWith(BasicAuthFilter.class)
public class InterpretationController {

    /**
     * Underlying application service facade.
     */
    private final InstandApplicationService service;

    @Inject
    public InterpretationController(@NonNull InstandApplicationService service) {
        this.service = service;
    }

    /**
     * Creates a interpretation.
     *
     * @param input create interpretation input
     * @return ninja result
     */
    public Result create(@NonNull CreateInterpretationInput input) {
        try {
            Interpretation interpretation = service.createInterpretation(input);
            return Results.json().render(interpretation);

        } catch (IllegalArgumentException e) {
            return Results.json()
                    .status(Result.SC_400_BAD_REQUEST)
                    .render(ErrorDescriptor.illegalArgument(e.getMessage()));
        }
    }

    /**
     * Gets interpretation by id.
     *
     * @param id interpretation id
     * @return ninja result
     */
    public Result get(@PathParam("id") String id) {
        Optional<Interpretation> optInterpretation = service.getInterpretation(id);
        if (!optInterpretation.isPresent()) {
            return Results.json()
                    .status(Result.SC_404_NOT_FOUND)
                    .render(ErrorDescriptor.notFound());
        }
        return Results.json().render(optInterpretation.get());
    }

    /**
     * Finds all interpretations.
     *
     * @return ninja result
     */
    public Result findAll() {
        List<Interpretation> interpretations = service.findAllInterpretations();
        return Results.json()
                .render(interpretations);
    }

    /**
     * Finds interpretations by interpreting subject id.
     *
     * @return ninja result
     */
    public Result findByInterpretingSubjectId(@PathParam("subjectId") String subjectId) {
        try {
            List<Interpretation> interpretations = service.findByInterpretingSubjectId(subjectId);
            return Results.json().render(interpretations);

        } catch (EntityNotFoundException e) {
            return Results.json()
                    .status(Result.SC_400_BAD_REQUEST)
                    .render(ErrorDescriptor.illegalArgument(e.getMessage()));
        }

    }
}
