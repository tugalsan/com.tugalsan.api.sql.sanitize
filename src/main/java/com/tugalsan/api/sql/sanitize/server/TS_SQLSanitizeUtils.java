package com.tugalsan.api.sql.sanitize.server;

import com.tugalsan.api.list.client.*;
import com.tugalsan.api.unsafe.client.*;
import java.util.*;

public class TS_SQLSanitizeUtils {

    public static void sanitize(Object word, CharSequence banned) {
        if (word == null || banned == null) {
            return;
        }
        if (String.valueOf(word).contains(banned)) {
            TGS_UnSafe.thrw(TS_SQLSanitizeUtils.class.getSimpleName(), "sanitize", "tag [" + word + "] contains [" + banned + "]");
        }
    }

    public static void sanitize(Object word) {
        if (word == null) {
            return;
        }
        var wordStr = String.valueOf(word);
        if (TGS_ListUtils.of(
                "INTEGER NOT NULL",
                "VARCHAR(254) NOT NULL",
                "INT NOT NULL"
        ).contains(wordStr)) {
            return;
        }
        TGS_ListUtils.of(",", ";", "+", "-", "*", "/", "\\", "{", "}", "[", "]", "=", "!", "<", ">", "%", " ")
                .forEach(banned -> sanitize(word, banned));
    }

    public static void sanitize(List words) {
        if (words == null) {
            return;
        }
        words.forEach(word -> sanitize(word));
    }

    public static void sanitize(Object[] words) {
        if (words == null) {
            return;
        }
        Arrays.stream(words).forEach(word -> sanitize(word));
    }
}
