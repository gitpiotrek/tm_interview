package com.tm.tmInterview.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ReportRecord {

    private String word;

    private List<Position> indexPositions;

    private int count;
}
