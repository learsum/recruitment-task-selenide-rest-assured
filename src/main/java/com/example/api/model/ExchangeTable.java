package com.example.api.model;

import lombok.Data;
import java.util.List;

@Data
public class ExchangeTable {
    private String table;
    private String no;
    private String effectiveDate;
    private List<Rate> rates;
}
