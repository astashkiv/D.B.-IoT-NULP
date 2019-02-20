package iot.database.controller;

import iot.database.DTO.MessageDTO;
import iot.database.exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(NoSuchInvoiceException.class)
    ResponseEntity<MessageDTO> handleNoSushInvoiceException(){
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such invoice not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchProviderException.class)
    ResponseEntity<MessageDTO> handleNoSushProviderException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such provider not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NoSuchGoodException.class)
    ResponseEntity<MessageDTO> handleNoSushGoodException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Such good not found"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ExistsProviderForInvoiceException.class)
    ResponseEntity<MessageDTO> handleExistsProviderForInvoiceException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete impossible. There are providers for this invoice"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsGoodForProviderException.class)
    ResponseEntity<MessageDTO> handleExistsGoodForProviderException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete impossible. There are goods for this provider"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(ExistsProviderForGoodException.class)
    ResponseEntity<MessageDTO> handleExistsProviderForGoodException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Delete impossible. There are providers for this good"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(AlreadyExistsGoodInProviderException.class)
    ResponseEntity<MessageDTO> handleAlreadyExistsGoodInProviderException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Add impossible. The provider already contain this good"), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(GoodAbsentException.class)
    ResponseEntity<MessageDTO> handleGoodAbsentException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("Now this good is absent"), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(ProviderHasNoGoodException.class)
    ResponseEntity<MessageDTO> handleProviderHasNoGoodException() {
        return new ResponseEntity<MessageDTO>(new MessageDTO("The provider hasn't this good"), HttpStatus.NOT_FOUND);
    }

}
