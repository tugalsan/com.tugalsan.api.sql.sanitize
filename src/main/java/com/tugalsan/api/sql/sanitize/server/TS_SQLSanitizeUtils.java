package com.tugalsan.api.sql.sanitize.server;

import com.tugalsan.api.list.client.*;
import com.tugalsan.api.union.client.TGS_UnionExcuse;
import java.util.*;

public class TS_SQLSanitizeUtils {

    public static void sanitize(Object word, CharSequence banned) throws SecurityException {
        if (word == null || banned == null) {
            return;
        }
        if (String.valueOf(word).contains(banned)) {
            throw new SecurityException(TS_SQLSanitizeUtils.class.getSimpleName() + "->sanitize->word [" + word + "] contains [" + banned + "]");
        }
    }

    public static void sanitize(Object word) {
        if (word == null) {
            return;
        }
        if (word instanceof TGS_UnionExcuse u && u.isPresent()) {
            word = u.value();
        }
        if (word instanceof Optional o && o.isPresent()) {
            word = o.get();
        }
        var wordStr = String.valueOf(word);
        if (TGS_ListUtils.of(
                "INTEGER NOT NULL",
                "VARCHAR(254) NOT NULL",
                "INT NOT NULL"
        ).contains(wordStr)) {
            return;
        }
        var lst = TGS_ListUtils.of(",", ";", "+", "-", "*", "/", "\\", "{", "}", "[", "]", "=", "!", "<", ">", "%", " ");
        for (var banned : lst) {
            sanitize(word, banned);
        }
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
