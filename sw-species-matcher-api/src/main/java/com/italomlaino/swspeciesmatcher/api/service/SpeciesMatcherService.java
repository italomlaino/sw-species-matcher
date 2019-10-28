package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.dto.MatchedSpeciesDTO;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.CharacterDTO;
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
    private URLService urlService;

    @Autowired
    public SpeciesMatcherService(Provider provider, URLService urlService) {
        this.provider = provider;
        this.urlService = urlService;
    }

    public MatchedSpeciesDTO matches(
            long filmId,
            long characterId) {
        Supplier<Stream<String>> characters = () -> getFilm(filmId).getCharacters().parallelStream();
        Supplier<Stream<Long>> charactersIds = () -> characters.get().map(characterURL -> urlService.getIdByURL(characterURL));
        List<String> selectedSpecies = fetchCharacter(characterId).getSpecies();
        return charactersIds
                .get()
                .map(this::fetchCharacter)
                .filter(character -> character.getSpecies().containsAll(selectedSpecies))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new MatchedSpeciesDTO(list.stream().map(CharacterDTO::getName).collect(Collectors.toList())))
                );
    }

    private FilmDTO getFilm(long filmId) {
        return provider.fetchFilm(filmId);
    }

    private CharacterDTO fetchCharacter(long characterId) {
        return provider.fetchCharacter(characterId);
    }
}
