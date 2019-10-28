package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.dto.MatchedSpeciesDto;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDto;
import com.italomlaino.swspeciesmatcher.api.provider.CharacterDto;
import com.italomlaino.swspeciesmatcher.api.provider.Provider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class SpeciesMatcherService {

    private Provider provider;
    private UrlService urlService;

    @Autowired
    public SpeciesMatcherService(Provider provider, UrlService urlService) {
        this.provider = provider;
        this.urlService = urlService;
    }

    public MatchedSpeciesDto matches(
            long filmId,
            long characterId) {
        Supplier<Stream<String>> characters = () -> getFilm(filmId).getCharacters().parallelStream();
        Supplier<Stream<Long>> charactersIds = () -> characters.get().map(characterUrl -> urlService.getIdByUrl(characterUrl));
        List<String> selectedSpecies = fetchCharacter(characterId).getSpecies();
        return charactersIds
                .get()
                .map(this::fetchCharacter)
                .filter(character -> character.getSpecies().containsAll(selectedSpecies))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new MatchedSpeciesDto(list.stream().map(CharacterDto::getName).collect(Collectors.toList())))
                );
    }

    private FilmDto getFilm(long filmId) {
        return provider.fetchFilm(filmId);
    }

    private CharacterDto fetchCharacter(long characterId) {
        return provider.fetchCharacter(characterId);
    }
}
