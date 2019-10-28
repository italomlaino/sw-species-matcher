package com.italomlaino.swspeciesmatcher.api.controller;

import com.italomlaino.swspeciesmatcher.api.dto.ResponseDto;
import com.italomlaino.swspeciesmatcher.api.service.SpeciesMatcherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpeciesMatcherController {

    private SpeciesMatcherService service;

    @Autowired
    public SpeciesMatcherController(SpeciesMatcherService service) {
        this.service = service;
    }

    @RequestMapping("/api/jdtest")
    public ResponseEntity<ResponseDto> matches(
            @RequestParam("film_id") long filmId,
            @RequestParam("character_id") long characterId) {
        return new ResponseEntity<>(service.matches(filmId, characterId), HttpStatus.OK);
    }

    @RequestMapping("/restricted/api/jdtest")
    public ResponseEntity<ResponseDto> restrictedMatches(
            @RequestParam("film_id") long filmId,
            @RequestParam("character_id") long characterId) {
        return new ResponseEntity<>(service.matches(filmId, characterId), HttpStatus.OK);
    }
}
