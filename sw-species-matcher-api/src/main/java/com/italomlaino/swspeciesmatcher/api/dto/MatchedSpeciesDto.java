package com.italomlaino.swspeciesmatcher.api.dto;

import java.util.ArrayList;
import java.util.List;

public class MatchedSpeciesDto extends ArrayList<String> implements ResponseDto {

    public MatchedSpeciesDto(List<String> names) {
        super(names);
    }
}
