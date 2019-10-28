package com.italomlaino.swspeciesmatcher.api.controller;


import com.italomlaino.swspeciesmatcher.api.dto.ErrorDTO;
import com.italomlaino.swspeciesmatcher.api.dto.ResponseDTO;
import com.italomlaino.swspeciesmatcher.api.exception.*;
import com.italomlaino.swspeciesmatcher.api.service.TranslatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class GlobalControllerAdvice {

    private TranslatorService translatorService;

    @Autowired
    public GlobalControllerAdvice(TranslatorService translatorService) {
        this.translatorService = translatorService;
    }

    @ExceptionHandler({FilmNotFoundException.class})
    public ResponseEntity<ResponseDTO> handle(FilmNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({CharacterNotFoundException.class})
    public ResponseEntity<ResponseDTO> handle(CharacterNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({SpeciesNotFoundException.class})
    public ResponseEntity<ResponseDTO> handle(SpeciesNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({CharacterIsNotInTheFilmException.class})
    public ResponseEntity<ResponseDTO> handle(CharacterIsNotInTheFilmException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({FailedToFetchFilmException.class})
    public ResponseEntity<ResponseDTO> handle(FailedToFetchFilmException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({FailedToFetchSpeciesException.class})
    public ResponseEntity<ResponseDTO> handle(FailedToFetchSpeciesException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({FailedToFetchCharacterException.class})
    public ResponseEntity<ResponseDTO> handle(FailedToFetchCharacterException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ResponseDTO> handle(MethodArgumentTypeMismatchException e) {
        return handle(e, BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDTO> handle(Exception e) {
        return handle(e, INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseDTO> handle(Exception e, HttpStatus status) {
        ErrorDTO errorDTO = new ErrorDTO();
        errorDTO.setMessage(translatorService.toLocale(e.getMessage()));

        return new ResponseEntity<>(errorDTO, status);
    }
}
