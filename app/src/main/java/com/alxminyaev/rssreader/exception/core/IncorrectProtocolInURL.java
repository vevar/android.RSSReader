package com.alxminyaev.rssreader.exception.core;

final public class IncorrectProtocolInURL extends CoreException {

    private static final String MESSAGE = "Incorrect protocol in URL";

    public IncorrectProtocolInURL() {
        super(MESSAGE);
    }

}
