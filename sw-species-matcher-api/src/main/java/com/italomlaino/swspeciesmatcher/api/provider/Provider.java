package com.italomlaino.swspeciesmatcher.api.provider;

public interface Provider {

    FilmDTO fetchFilm(long filmId);

    PeopleDTO fetchPeople(long peopleId);

    SpeciesDTO fetchSpecies(long speciesId);
}
