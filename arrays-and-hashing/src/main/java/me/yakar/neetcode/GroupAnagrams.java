package me.yakar.neetcode;

import java.util.*;

public class GroupAnagrams {
    public List<List<String>> groupAnagrams(String[] strs) {
        if (strs == null) {
            throw new NullPointerException();
        }

        if (strs.length > 1000) {
            throw new IllegalArgumentException();
        }


        for (String str : strs) {
            if (!str.matches("^[a-z]+$")) {
                throw new IllegalArgumentException();
            }
        }
        Map<String, List<String>> map = new HashMap<>();
        for (String str : strs) {
            char[] keyArray = str.toCharArray();
            Arrays.sort(keyArray);
            String key = new String(keyArray);
            map.putIfAbsent(key, new ArrayList<>());
            map.get(key).add(str);
        }
        return map.values().stream().toList();
    }
}
