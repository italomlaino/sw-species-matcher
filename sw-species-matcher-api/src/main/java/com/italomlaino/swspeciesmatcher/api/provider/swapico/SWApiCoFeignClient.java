package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import com.italomlaino.swspeciesmatcher.api.exception.*;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.CharacterDTO;
import com.italomlaino.swspeciesmatcher.api.provider.SpeciesDTO;
import feign.error.ErrorCodes;
import feign.error.ErrorHandling;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(name = "SWApiCoFeignClient", url = "${SWApi.base.url}")
public interface SWApiCoFeignClient {

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = FilmNotFoundException.class)
    }, defaultException = FailedToFetchFilmException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/films/{filmId}")
    FilmDTO fetchFilm(@PathVariable("filmId") long filmId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = CharacterNotFoundException.class)
    }, defaultException = FailedToFetchCharacterException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/people/{peopleId}")
    CharacterDTO fetchPeople(@PathVariable("peopleId") long peopleId);

    @ErrorHandling(codeSpecific = {
            @ErrorCodes(codes = {404}, generate = SpeciesNotFoundException.class)
    }, defaultException = FailedToFetchSpeciesException.class)
    @RequestMapping(method = RequestMethod.GET, value = "/species/{speciesId}")
    SpeciesDTO fetchSpecies(@PathVariable("speciesId") long speciesId);

}