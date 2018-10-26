package com.alxminyaev.rssreader.exception.view;

final public class IncorrectItemsOfViewException extends ViewException {
    public final static String MESSAGE_INCORRECT_ITEMS_OF_VIEW = "Incorrect items of view";

    public IncorrectItemsOfViewException(){
        super(MESSAGE_INCORRECT_ITEMS_OF_VIEW);
    }

    public IncorrectItemsOfViewException(final String message) {
        super(message);
    }
}
