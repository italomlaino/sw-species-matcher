package com.italomlaino.swspeciesmatcher.api.provider;

public interface Provider {

    FilmDTO fetchFilm(long filmId);

    CharacterDTO fetchCharacter(long characterId);

    SpeciesDTO fetchSpecies(long speciesId);
}
