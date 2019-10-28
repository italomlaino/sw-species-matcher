package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.dto.MatchedSpeciesDTO;
import com.italomlaino.swspeciesmatcher.api.exception.CharacterIsNotInTheFilmException;
import com.italomlaino.swspeciesmatcher.api.provider.Provider;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.PeopleDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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
        List<String> selectedSpecies = getSpecies(characterId);
        List<String> characters = getFilm(filmId).getCharacters();
        boolean characterIsInTheFilm = characters.stream().anyMatch(characterURL -> urlService.getIdByURL(characterURL) == characterId);
        if (!characterIsInTheFilm)
            throw new CharacterIsNotInTheFilmException();
        
        return characters
                .stream()
                .map(url -> urlService.getIdByURL(url))
                .map(this::fetchPeople)
                .filter(people -> people.getSpecies().containsAll(selectedSpecies))
                .collect(Collectors.collectingAndThen(
                        Collectors.toList(),
                        list -> new MatchedSpeciesDTO(list.stream().map(PeopleDTO::getName).collect(Collectors.toList())))
                );
    }

    private FilmDTO getFilm(long filmId) {
        return provider.fetchFilm(filmId);
    }

    private List<String> getSpecies(long characterId) {
        List<String> species = fetchPeople(characterId).getSpecies();
        species.forEach(s -> provider.fetchSpecies(urlService.getIdByURL(s)));
        return species;
    }

    private PeopleDTO fetchPeople(long peopleId) {
        return provider.fetchPeople(peopleId);
    }
}
