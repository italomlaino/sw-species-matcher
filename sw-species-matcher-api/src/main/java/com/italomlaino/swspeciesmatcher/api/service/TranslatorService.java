package com.italomlaino.swspeciesmatcher.api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
public class TranslatorService {

    private ResourceBundleMessageSource messageSource;

    @Autowired
    public TranslatorService(ResourceBundleMessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String toLocale(String msgCode) {
        return toLocale(msgCode, null);
    }

    public String toLocale(String msgCode, Object[] args) {
        Locale locale = LocaleContextHolder.getLocale();
        return messageSource.getMessage(msgCode, args, locale);
    }
}