package boot.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**Класс исключения, возникающего при запросе к несуществующей сущности.
 @author Артемьев Р.А.
 @version 03.12.2019 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EntityNotFoundException extends RuntimeException
{
    public EntityNotFoundException()
    {
    }

    public EntityNotFoundException(String message)
    {
        super(message);
    }

    public EntityNotFoundException(Throwable cause)
    {
        super(cause);
    }

    public EntityNotFoundException(String message, Throwable cause)
    {
        super(message, cause);
    }
}
