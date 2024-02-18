package com.tm.tmInterview.services;

import com.tm.tmInterview.models.Position;
import com.tm.tmInterview.models.ReportRecord;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ReportService {

    private final static Comparator<ReportRecord> reportComparator = Comparator.comparingLong(e -> e.getIndexPositions().get(0).getStart());

    public List<ReportRecord> generateReport(String input) {
        return Tokenizer.tokenizeWords(input)
                .entrySet().stream()
                .map(entry -> new ReportRecord(entry.getKey(), entry.getValue(), entry.getValue().size()))
                .toList();
    }

    public Set<ReportRecord> matchSearch(String input, String[] matchWords) {
        Map<String, List<Position>> tokes = Tokenizer.tokenizeWords(input);

        Set<ReportRecord> result = new TreeSet<>(reportComparator);
        Arrays.stream(matchWords).map(String::toLowerCase).map(String::trim)
                .forEach(match -> {
                    List<Position> positions = tokes.get(match);
                    if (positions != null && !positions.isEmpty()) {
                        result.add(new ReportRecord(match, positions, positions.size()));
                    }
                });

        return result;
    }

    public Set<ReportRecord> regexSearch(String input, String[] regexWords) {
        var ngramTokens = Tokenizer.ngramTokenizer(input);

        Set<ReportRecord> result = new TreeSet<>(reportComparator);
        Arrays.stream(regexWords).map(String::toLowerCase).map(String::trim)
                .forEach(regex -> {
                    Set<Map.Entry<String, List<Position>>> positions = ngramTokens.get(regex);
                    if (positions != null && !positions.isEmpty()) {
                        positions.forEach(entry -> result.add(new ReportRecord(entry.getKey(), entry.getValue(), entry.getValue().size())));
                    }
                });

        return result;
    }
}