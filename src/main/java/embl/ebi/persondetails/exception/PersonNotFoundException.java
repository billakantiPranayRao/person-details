package embl.ebi.persondetails.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus
public class PersonNotFoundException extends RuntimeException {

    public PersonNotFoundException(String message){

        super(message);
    }

    public PersonNotFoundException(String message, Throwable throwable){

        super(message, throwable);
    }

}
