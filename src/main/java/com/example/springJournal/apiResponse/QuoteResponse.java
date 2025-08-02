package com.example.springJournal.apiResponse;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class QuoteResponse{
    private Data data;
    @Getter
    @Setter
    public static class Data{
    private String author;
    private String quote;
}
}

