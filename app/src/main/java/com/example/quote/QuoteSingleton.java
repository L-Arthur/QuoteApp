package com.example.quote;

import java.util.Collections;
import java.util.List;

class QuoteSingletonJava {
    private static List<Quote> quotes = Collections.emptyList();

    public static List<Quote> getQuotes() {
        return quotes;
    }

    public static void setQuotes(List<Quote> quotes) {
        QuoteSingletonJava.quotes = quotes;
    }
}