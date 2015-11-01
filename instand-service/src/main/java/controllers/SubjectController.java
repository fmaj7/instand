package controllers;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.instand.app.InstandApplicationService;
import com.instand.app.CreateSubjectInput;
import com.instand.domain.Subject;
import conf.CorsFilter;
import lombok.NonNull;
import ninja.BasicAuthFilter;
import ninja.FilterWith;
import ninja.Result;
import ninja.Results;
import ninja.params.PathParam;

import java.util.List;
import java.util.Optional;

/**
 * Controller of subjects.
 */
@Singleton
@FilterWith({CorsFilter.class})
public class SubjectController {

    /**
     * Underlying application service facade.
     */
    private final InstandApplicationService service;

    @Inject
    public SubjectController(@NonNull InstandApplicationService service) {
        this.service = service;
    }

    /**
     * Creates a subject.
     *
     * @param input create subject input
     * @return ninja result
     */
    public Result create(@NonNull CreateSubjectInput input) {
        try {
            Subject subject = service.createSubject(input);
            return Results.json().render(subject);

        } catch (IllegalArgumentException e) {
            return Results.json()
                    .status(Result.SC_400_BAD_REQUEST)
                    .render(ErrorDescriptor.illegalArgument(e.getMessage()));
        }
    }

    /**
     * Gets subject by id.
     *
     * @param id subject id
     * @return ninja result
     */
    public Result get(@PathParam("id") String id) {
        Optional<Subject> optSubject = service.getSubject(id);
        if (!optSubject.isPresent()) {
            return Results.json()
                    .status(Result.SC_404_NOT_FOUND)
                    .render(ErrorDescriptor.notFound());
        }
        return Results.json().render(optSubject.get());
    }

    /**
     * Finds all subjects.
     *
     * @return ninja result
     */
    public Result findAll() {

        List<Subject> subjects = service.findAllSubjects();
        return Results.json()
                .render(subjects);
    }

}
