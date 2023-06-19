package com.codekaidev.uploaddownloadfiles.util.date;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum LocalDateFormat {

    DD_MM_4Y_HH_MM_SS("dd/MM/yyyy HH:mm:ss");

    private final String pattern;

}
