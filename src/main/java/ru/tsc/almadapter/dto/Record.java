package ru.tsc.almadapter.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
@EqualsAndHashCode
public class Record {
    public String internalID;
    public int externalID;
    public int heritageID;
    public String parentId;
    public String value;
    public String version;
    public boolean isActive;
    public String startDate;
    public String endDate;
    public List<ExtField> extFields;
}