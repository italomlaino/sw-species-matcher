package com.italomlaino.swspeciesmatcher.api.provider;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class FilmDto {

    @JsonProperty("title")
    private String title;
    @JsonProperty("episode_id")
    private int episodeId;
    @JsonProperty("director")
    private String director;
    @JsonProperty("characters")
    private List<String> characters;

    public String getTitle() {
        return title;
    }

    public int getEpisodeId() {
        return episodeId;
    }

    public String getDirector() {
        return director;
    }

    public List<String> getCharacters() {
        return characters;
    }
}
