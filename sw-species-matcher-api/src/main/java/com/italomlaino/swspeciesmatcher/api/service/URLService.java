package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.exception.FailedToGetIdByURL;
import org.springframework.stereotype.Service;

import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class URLService {

    public long getIdByURL(String url) {
        String characterIdRegex = ".*?[/]([0-9_-]+)[/]?$";
        Pattern pattern = Pattern.compile(characterIdRegex);
        Matcher matches = pattern.matcher(url);
        if (!matches.matches())
            throw new FailedToGetIdByURL();

        MatchResult matchResult = matches.toMatchResult();
        return Long.parseLong(matchResult.group(1));
    }
}
