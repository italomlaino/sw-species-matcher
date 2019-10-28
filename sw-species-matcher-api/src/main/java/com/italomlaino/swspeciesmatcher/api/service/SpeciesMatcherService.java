package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.dto.MatchedSpeciesDTO;
import com.italomlaino.swspeciesmatcher.api.provider.FilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.PeopleDTO;
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
        List<String> selectedSpecies = fetchPeople(characterId).getSpecies();
        return charactersIds
                .get()
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

    private PeopleDTO fetchPeople(long peopleId) {
        return provider.fetchPeople(peopleId);
    }
}
