package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.dto.MatchedSpeciesDTO;
import com.italomlaino.swspeciesmatcher.api.exception.CharacterIsNotInTheFilmException;
import com.italomlaino.swspeciesmatcher.api.provider.swapico.SWAFilmDTO;
import com.italomlaino.swspeciesmatcher.api.provider.swapico.SWAPeopleDTO;
import com.italomlaino.swspeciesmatcher.api.provider.swapico.SWApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class SpeciesMatcherService {

    private SWApiClient apiClient;
    private URLService urlService;

    @Autowired
    public SpeciesMatcherService(SWApiClient apiClient, URLService urlService) {
        this.apiClient = apiClient;
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
                        list -> new MatchedSpeciesDTO(list.stream().map(SWAPeopleDTO::getName).collect(Collectors.toList())))
                );
    }

    private SWAFilmDTO getFilm(long filmId) {
        return apiClient.fetchFilm(filmId);
    }

    private List<String> getSpecies(long characterId) {
        List<String> species = fetchPeople(characterId).getSpecies();
        species.forEach(s -> apiClient.fetchSpecies(urlService.getIdByURL(s)));
        return species;
    }

    private SWAPeopleDTO fetchPeople(long peopleId) {
        return apiClient.fetchPeople(peopleId);
    }
}
