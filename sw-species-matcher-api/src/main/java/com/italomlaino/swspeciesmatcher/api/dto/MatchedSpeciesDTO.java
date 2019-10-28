package com.italomlaino.swspeciesmatcher.api.dto;

import java.util.ArrayList;
import java.util.List;

public class MatchedSpeciesDTO extends ArrayList<String> implements ResponseDTO {

    public MatchedSpeciesDTO(List<String> names) {
        super(names);
    }
}
