package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import com.italomlaino.swspeciesmatcher.api.exception.*;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SWApiClient", url = "${SWApi.base.url}")
public interface SWApiFeignClient {

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = FilmNotFoundException.class)
    }, defaultException = FailedToFetchFilmException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/films/{filmId}")
    SWAFilmDTO fetchFilm(@PathVariable("filmId") long filmId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = CharacterNotFoundException.class)
    }, defaultException = FailedToFetchCharacterException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/people/{peopleId}")
    SWAPeopleDTO fetchPeople(@PathVariable("peopleId") long peopleId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = SpeciesNotFoundException.class)
    }, defaultException = FailedToFetchSpeciesException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/species/{speciesId}")
    SWASpeciesDTO fetchSpecies(@PathVariable("speciesId") long speciesId);

}