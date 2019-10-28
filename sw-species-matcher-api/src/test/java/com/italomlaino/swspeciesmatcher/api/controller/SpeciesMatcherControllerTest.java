package com.italomlaino.swspeciesmatcher.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.italomlaino.swspeciesmatcher.api.dto.LoginResultDto;
import com.italomlaino.swspeciesmatcher.api.exception.FailedToFetchCharacterException;
import com.italomlaino.swspeciesmatcher.api.exception.FailedToFetchFilmException;
import com.italomlaino.swspeciesmatcher.api.provider.Provider;
import com.italomlaino.swspeciesmatcher.api.service.CredentialsService;
import com.italomlaino.swspeciesmatcher.api.service.UrlService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.contains;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class SpeciesMatcherControllerTest {

    private static final String END_POINT = "/api/jdtest";
    private static final String RESTRICTED_END_POINT = "/restricted/api/jdtest";
    private static final String AUTHENTICATION_END_POINT = "/authentication";
    private static final String FILM_ID_PARAM = "film_id";
    private static final String CHARACTER_ID_PARAM = "character_id";
    private static final String AUTHORIZATION_PARAM = "Authorization";
    private static final String USERNAME_PARAM = "username";
    private static final String PASSWORD_PARAM = "password";

    @Autowired
    private MockMvc mvc;
    @Autowired
    private ObjectMapper objectMapper;
    @SpyBean
    private Provider provider;
    @SpyBean
    private UrlService urlService;
    @SpyBean
    private CredentialsService credentialsService;

    @Test
    public void matches_happyDay() throws Exception {
        mvc.perform(
                getRequestBuilder("1", "2"))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[\"C-3PO\",\"R2-D2\",\"R5-D4\"]")));
    }

    @Test
    public void matches_characterNotFound() throws Exception {
        doReturn(99999999999L).when(urlService).getIdByUrl(contains("people"));

        mvc.perform(
                getRequestBuilder("1", "99999999999"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("{\"message\":\"Character not found\"}")));
    }

    @Test
    public void matches_filmNotFound() throws Exception {
        mvc.perform(
                getRequestBuilder("99999999999", "2"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(equalTo("{\"message\":\"Film not found\"}")));
    }

    @Test
    public void matches_failedToFetchCharacter() throws Exception {
        doThrow(new FailedToFetchCharacterException()).when(provider).fetchCharacter(2);

        mvc.perform(
                getRequestBuilder("1", "2"))
                .andExpect(status().isFailedDependency())
                .andExpect(content().string(equalTo("{\"message\":\"Failed to fetch the character\"}")));
    }

    @Test
    public void matches_failedToFetchFilm() throws Exception {
        doThrow(new FailedToFetchFilmException()).when(provider).fetchFilm(1);

        mvc.perform(
                getRequestBuilder("1", "2"))
                .andExpect(status().isFailedDependency())
                .andExpect(content().string(equalTo("{\"message\":\"Failed to fetch the film\"}")));
    }

    @Test
    public void matches_incorrectFilmIdParam() throws Exception {
        mvc.perform(
                getRequestBuilder("A", "2"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("{\"message\":\"Failed to convert value of type 'java.lang.String' to required type 'long'; nested exception is java.lang.NumberFormatException: For input string: \\\"A\\\"\"}")));
    }

    @Test
    public void matches_incorrectCharacterIdParam() throws Exception {
        mvc.perform(
                getRequestBuilder("1", "A"))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(equalTo("{\"message\":\"Failed to convert value of type 'java.lang.String' to required type 'long'; nested exception is java.lang.NumberFormatException: For input string: \\\"A\\\"\"}")));
    }

    @Test
    public void restrictedMatches_happyDay() throws Exception {
        String content = mvc.perform(post(AUTHENTICATION_END_POINT)
                .param(USERNAME_PARAM, "root")
                .param(PASSWORD_PARAM, "root")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn()
                .getResponse()
                .getContentAsString();
        LoginResultDto loginResultDto = objectMapper.readValue(content, LoginResultDto.class);

        mvc.perform(
                getRestrictedRequestBuilder("1", "2", loginResultDto.getToken()))
                .andExpect(status().isOk())
                .andExpect(content().string(equalTo("[\"C-3PO\",\"R2-D2\",\"R5-D4\"]")));
    }

    @Test
    public void restrictedMatches_unauthorized() throws Exception {
        doReturn(false).when(credentialsService).isValid(anyString(), anyString());

        mvc.perform(post(AUTHENTICATION_END_POINT)
                .param(USERNAME_PARAM, "root")
                .param(PASSWORD_PARAM, "root")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string(equalTo("{\"message\":\"Invalid credentials\"}")));
    }

    @Test
    public void restrictedMatches_badToken() throws Exception {
        mvc.perform(
                getRestrictedRequestBuilder("1", "2", ""))
                .andExpect(status().isForbidden());
    }
    
    private MockHttpServletRequestBuilder getRequestBuilder(String filmId, String characterId) {
        return get(END_POINT)
                .param(FILM_ID_PARAM, filmId)
                .param(CHARACTER_ID_PARAM, characterId)
                .accept(MediaType.APPLICATION_JSON);
    }

    private MockHttpServletRequestBuilder getRestrictedRequestBuilder(String filmId, String characterId, String authorization) {
        return get(RESTRICTED_END_POINT)
                .param(FILM_ID_PARAM, filmId)
                .param(CHARACTER_ID_PARAM, characterId)
                .header(AUTHORIZATION_PARAM, authorization)
                .accept(MediaType.APPLICATION_JSON);
    }
}