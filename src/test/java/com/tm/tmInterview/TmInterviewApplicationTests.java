package com.tm.tmInterview;

import com.tm.tmInterview.models.Position;
import com.tm.tmInterview.services.Tokenizer;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Disabled
class TmInterviewApplicationTests {

    private final static String loremIpsum = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua.
            Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat.
            Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.
            Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            """;

    @Test
    void reportTest() {
        Map<String, List<Position>> tokens = Tokenizer.tokenizeWords(loremIpsum);

        Assertions.assertEquals(63, tokens.size());
        Assertions.assertEquals(69L, tokens.values().stream().mapToInt(List::size).sum());
    }

    @Test
    void matchTest() {
        Map<String, List<Position>> tokens = Tokenizer.tokenizeWords(loremIpsum);

        List<Position> position_lorem = tokens.get("lorem");
        List<Position> position_ipsum = tokens.get("ipsum");
        List<Position> position_in = tokens.get("in");

        Assertions.assertEquals(1, position_lorem.size());
        Assertions.assertEquals(0, position_lorem.get(0).getStart());
        Assertions.assertEquals(4, position_lorem.get(0).getEnd());

        Assertions.assertEquals(1, position_ipsum.size());
        Assertions.assertEquals(6, position_ipsum.get(0).getStart());
        Assertions.assertEquals(10, position_ipsum.get(0).getEnd());

        Assertions.assertEquals(3, position_in.size());
    }

    @Test
    void regexTest() {
        Map<String, Set<Map.Entry<String, List<Position>>>> ngramTokens = Tokenizer.ngramTokenizer(loremIpsum);

        Set<Map.Entry<String, List<Position>>> matches = ngramTokens.get("lit");

        Assertions.assertEquals(3, matches.size());
        Assertions.assertEquals(Set.of("elit,", "velit", "mollit"), matches.stream().map(Map.Entry::getKey).collect(Collectors.toSet()));
    }
}