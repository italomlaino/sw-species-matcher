package com.italomlaino.swspeciesmatcher.api.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import javax.servlet.http.HttpServletRequest;
import java.util.Collections;
import java.util.List;
import java.util.Locale;

@Configuration
public class CustomLocaleResolver
        extends AcceptHeaderLocaleResolver
        implements WebMvcConfigurer {

    private static final String LANGUAGE_EN = "en";
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String MESSAGES = "messages";
    private static final String ACCEPT_LANGUAGE_HEADER = "Accept-Language";

    private List<Locale> locales = Collections.singletonList(
            new Locale(LANGUAGE_EN));

    @Override
    public Locale resolveLocale(HttpServletRequest request) {
        String headerLang = request.getHeader(ACCEPT_LANGUAGE_HEADER);
        return headerLang == null || headerLang.isEmpty()
                ? Locale.getDefault()
                : Locale.lookup(Locale.LanguageRange.parse(headerLang), locales);
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource rs = new ResourceBundleMessageSource();
        rs.setBasename(MESSAGES);
        rs.setDefaultEncoding(DEFAULT_ENCODING);
        rs.setUseCodeAsDefaultMessage(true);
        return rs;
    }
}
