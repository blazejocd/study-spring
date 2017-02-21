package spittr.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_IMPLEMENTED, reason="Spitter already exists. Can't update.")
public class SpitterAlreadyExists extends RuntimeException {

}

