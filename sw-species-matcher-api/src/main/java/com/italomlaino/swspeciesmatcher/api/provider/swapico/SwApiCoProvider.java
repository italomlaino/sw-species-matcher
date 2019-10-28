package com.italomlaino.swspeciesmatcher.api.provider.swapico;

import com.italomlaino.swspeciesmatcher.api.provider.FilmDto;
import com.italomlaino.swspeciesmatcher.api.provider.CharacterDto;
import com.italomlaino.swspeciesmatcher.api.provider.SpeciesDto;
import com.italomlaino.swspeciesmatcher.api.provider.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SwApiCoProvider implements Provider {

    private SwApiCoFeignClient feignClient;

    @Autowired
    public SwApiCoProvider(SwApiCoFeignClient feignClient) {
        this.feignClient = feignClient;
    }

    @Override
    public FilmDto fetchFilm(long filmId) {
        return feignClient.fetchFilm(filmId);
    }

    @Override
    public CharacterDto fetchCharacter(long characterId) {
        return feignClient.fetchPeople(characterId);
    }

    @Override
    public SpeciesDto fetchSpecies(long speciesId) {
        return feignClient.fetchSpecies(speciesId);
    }
}
