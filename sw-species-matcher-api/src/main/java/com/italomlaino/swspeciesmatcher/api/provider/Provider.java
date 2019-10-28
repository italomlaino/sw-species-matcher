package com.italomlaino.swspeciesmatcher.api.provider;

public interface Provider {

    FilmDto fetchFilm(long filmId);

    CharacterDto fetchCharacter(long characterId);

    SpeciesDto fetchSpecies(long speciesId);
}
