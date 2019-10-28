package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import com.italomlaino.swspeciesmatcher.api.provider.FilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.PeopleDTO;
import com.italomlaino.swspeciesmatcher.api.provider.SpeciesDTO;
import com.italomlaino.swspeciesmatcher.api.provider.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SWApiCoProvider implements Provider {

    private SWApiCoFeignClient feignClient;

    @Autowired
    public SWApiCoProvider(SWApiCoFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public FilmDTO fetchFilm(long filmId) {
        return feignClient.fetchFilm(filmId);
    }

    @Override
    public PeopleDTO fetchPeople(long peopleId) {
        return feignClient.fetchPeople(peopleId);
    }

    @Override
    public SpeciesDTO fetchSpecies(long speciesId) {
        return feignClient.fetchSpecies(speciesId);
    }
}
