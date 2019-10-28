package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SWApiClient implements SWApiFeignClient {

    private SWApiFeignClient feignClient;

    public SWApiClient(@Autowired SWApiFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public SWAFilmDTO fetchFilm(long filmId) {
        return feignClient.fetchFilm(filmId);
    }

    @Override
    public SWAPeopleDTO fetchPeople(long peopleId) {
        return feignClient.fetchPeople(peopleId);
    }

    @Override
    public SWASpeciesDTO fetchSpecies(long speciesId) {
        return feignClient.fetchSpecies(speciesId);
    }
}
