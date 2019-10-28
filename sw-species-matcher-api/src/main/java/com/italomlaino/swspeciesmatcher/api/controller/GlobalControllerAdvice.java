package com.italomlaino.swspeciesmatcher.api.controller;


import com.italomlaino.swspeciesmatcher.api.dto.ErrorDto;
import com.italomlaino.swspeciesmatcher.api.dto.ResponseDto;
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
    public ResponseEntity<ResponseDto> handle(FilmNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({CharacterNotFoundException.class})
    public ResponseEntity<ResponseDto> handle(CharacterNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({SpeciesNotFoundException.class})
    public ResponseEntity<ResponseDto> handle(SpeciesNotFoundException e) {
        return handle(e, NOT_FOUND);
    }

    @ExceptionHandler({FailedToFetchFilmException.class})
    public ResponseEntity<ResponseDto> handle(FailedToFetchFilmException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({FailedToFetchSpeciesException.class})
    public ResponseEntity<ResponseDto> handle(FailedToFetchSpeciesException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({FailedToFetchCharacterException.class})
    public ResponseEntity<ResponseDto> handle(FailedToFetchCharacterException e) {
        return handle(e, FAILED_DEPENDENCY);
    }

    @ExceptionHandler({MethodArgumentTypeMismatchException.class})
    public ResponseEntity<ResponseDto> handle(MethodArgumentTypeMismatchException e) {
        return handle(e, BAD_REQUEST);
    }

    @ExceptionHandler({Exception.class})
    public ResponseEntity<ResponseDto> handle(Exception e) {
        return handle(e, INTERNAL_SERVER_ERROR);
    }

    public ResponseEntity<ResponseDto> handle(Exception e, HttpStatus status) {
        ErrorDto errorDto = new ErrorDto();
        errorDto.setMessage(translatorService.toLocale(e.getMessage()));

        return new ResponseEntity<>(errorDto, status);
    }
}
