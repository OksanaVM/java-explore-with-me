package ru.practicum.utility;

import java.time.format.DateTimeFormatter;

public class UtilityClass {
    public static final String pattern = "yyyy-MM-dd HH:mm:ss";
    public static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
}
