package com.tm.tmInterview.services;

import com.tm.tmInterview.models.Position;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Tokenizer {

    private final static String WHITE_SPACES = "\\s";

    public static Map<String, Set<Map.Entry<String, List<Position>>>> ngramTokenizer(String input) {
        Map<String, List<Position>> tokens = tokenizeWords(input);
        Map<String, Set<Map.Entry<String, List<Position>>>> result = new LinkedHashMap<>();

        tokens.entrySet().forEach(entry -> toNgram(entry.getKey(), entry, result));
        return result;
    }

    private static void toNgram(String token,
                                Map.Entry<String, List<Position>> entry,
                                Map<String, Set<Map.Entry<String, List<Position>>>> result) {

        for (int iLength = 1; iLength < token.length() + 1; iLength++) {
            for (int iEnd = 1; iEnd <= iLength; iEnd++) {
                for (int iOffset = 0; iOffset < iEnd; iOffset++) {
                    addNgramToken(token.substring(iOffset, iEnd), entry, result);
                }
            }
        }
    }

    public static void addNgramToken(String subStr,
                                     Map.Entry<String, List<Position>> entry,
                                     Map<String, Set<Map.Entry<String, List<Position>>>> result) {
        if (!result.containsKey(subStr)) {
            result.put(subStr, new HashSet<>());
        }
        result.get(subStr).add(entry);
    }

    public static Map<String, List<Position>> tokenizeWords(String input) {
        AtomicLong position = new AtomicLong();

        String[] tokens = input.strip().toLowerCase().split(WHITE_SPACES);
        return Arrays.stream(tokens).collect(Collectors.groupingBy(Function.identity(), LinkedHashMap::new,
                Collectors.mapping(token -> toPosition(token, position), Collectors.toList())));
    }

    private static Position toPosition(String token, AtomicLong startPosition) {
        Position position = new Position(startPosition.get(), startPosition.get() + token.length() - 1);
        startPosition.addAndGet(token.length());
        startPosition.incrementAndGet();
        return position;
    }
}
