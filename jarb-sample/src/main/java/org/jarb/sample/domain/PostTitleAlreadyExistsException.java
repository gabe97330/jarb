package org.jarb.sample.domain;

import org.jarb.violation.ConstraintViolation;
import org.jarb.violation.UniqueKeyViolationException;

/**
 * Thrown whenever a post is being inserted with a title that already exists.
 * @author Jeroen van Schagen
 * @since 8-6-2011
 */
public class PostTitleAlreadyExistsException extends UniqueKeyViolationException {
    private static final long serialVersionUID = -7112473482705797922L;

    /**
     * Construct a new {@link PostTitleAlreadyExistsException}.
     * @param violation constraint violation reference
     * @param cause the actual cause
     */
    public PostTitleAlreadyExistsException(ConstraintViolation violation, Throwable cause) {
        super(violation, cause);
    }
}
