package com.learnenglish.vocabulary.logic.pack1;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Element {

    public static final int DEFAULT_COUNT_OF_TRYINGS = 1;

    private String englishWord;
    private String translation;
    private int countOfTrying = DEFAULT_COUNT_OF_TRYINGS;

    public Element(String englishWord, String translation) {
        this.englishWord = englishWord;
        this.translation = translation;
    }

    public Element(String englishWord, String translation, int countOfTrying) {
        this.englishWord = englishWord;
        this.translation = translation;
        this.countOfTrying = countOfTrying;
    }

    @Override
    public String toString() {
        return englishWord + LowTermCheck.SEPARATOR + translation;
    }

    @Override
    public boolean equals(Object obj) {
        Element bufElem = obj instanceof Element ? ((Element) obj) : null;
        if (bufElem == null)
            return false;
        if (bufElem.englishWord.equals(englishWord) && bufElem.translation.equals(translation))
            return true;
        else
            return false;
    }

    @Override
    public int hashCode() {
        return (englishWord+translation).hashCode();
    }
}