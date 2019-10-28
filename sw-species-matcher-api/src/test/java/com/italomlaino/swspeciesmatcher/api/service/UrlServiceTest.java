package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.exception.FailedToGetIdByUrlException;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UrlServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    private UrlService subject;

    @Test
    public void getIdByUrl_happyDay() {
        assertThat(subject.getIdByUrl("https://swapi.co/people/1"), equalTo(1L));
        assertThat(subject.getIdByUrl("https://swapi.co/people/1/"), equalTo(1L));
        assertThat(subject.getIdByUrl("/1/"), equalTo(1L));
        assertThat(subject.getIdByUrl("people/1/"), equalTo(1L));
        assertThat(subject.getIdByUrl("species/1/"), equalTo(1L));
        assertThat(subject.getIdByUrl("film/1/"), equalTo(1L));
    }

    @Test
    public void getIdByUrl_failedToGetIdByUrl() {
        expectedException.expect(FailedToGetIdByUrlException.class);

        subject.getIdByUrl("https://swapi.co/people/A");
    }
}
