package com.italomlaino.swspeciesmatcher.api.service;

import com.italomlaino.swspeciesmatcher.api.exception.FailedToGetIdByURL;
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
public class URLServiceTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Autowired
    private URLService subject;

    @Test
    public void getIdByURL_happyDay() {
        assertThat(subject.getIdByURL("https://swapi.co/people/1"), equalTo(1L));
        assertThat(subject.getIdByURL("https://swapi.co/people/1/"), equalTo(1L));
        assertThat(subject.getIdByURL("/1/"), equalTo(1L));
        assertThat(subject.getIdByURL("people/1/"), equalTo(1L));
        assertThat(subject.getIdByURL("species/1/"), equalTo(1L));
        assertThat(subject.getIdByURL("film/1/"), equalTo(1L));
    }

    @Test
    public void getIdByURL_failedToGetIdByURL() {
        expectedException.expect(FailedToGetIdByURL.class);

        subject.getIdByURL("https://swapi.co/people/A");
    }
}
