package projeto.service.user.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_GATEWAY)
public class EmailAlreadyUsedException extends RuntimeException{
    
    public EmailAlreadyUsedException() {
        super("Email already used",null,false,false);
    }
}
