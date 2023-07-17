package dia.kids.cms.exception;

import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {
    @ExceptionHandler(value = { PictureNotFoundException.class,
                                ArticleNotFoundException.class,
                                CommentNotFoundException.class })
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFoundException(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }
    @ExceptionHandler(value = { ValidationException.class,
                                InvalidCommentException.class })
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleBadRequestException(final RuntimeException exception) {
        return new ErrorResponse(exception.getMessage());
    }
}
