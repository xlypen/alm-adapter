package ru.tsc.almadapter.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class RefArrayItem {
    public String code;
    public List<Record> recordList;
}