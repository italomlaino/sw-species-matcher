package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import com.italomlaino.swspeciesmatcher.api.exception.*;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDto;
import com.italomlaino.swspeciesmatcher.api.provider.CharacterDto;
import com.italomlaino.swspeciesmatcher.api.provider.SpeciesDto;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SwApiCoFeignClient", url = "${SwApi.base.url}")
public interface SwApiCoFeignClient {

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = FilmNotFoundException.class)
    }, defaultException = FailedToFetchFilmException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/films/{filmId}")
    FilmDto fetchFilm(@PathVariable("filmId") long filmId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = CharacterNotFoundException.class)
    }, defaultException = FailedToFetchCharacterException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/people/{peopleId}")
    CharacterDto fetchPeople(@PathVariable("peopleId") long peopleId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = SpeciesNotFoundException.class)
    }, defaultException = FailedToFetchSpeciesException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/species/{speciesId}")
    SpeciesDto fetchSpecies(@PathVariable("speciesId") long speciesId);

}