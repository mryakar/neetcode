package me.yakar.neetcode;

import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeString {

    // Time:    O(n)
    // Space:   O(n)
    public String encode(List<String> strs) {
        if (strs == null) {
            throw new IllegalArgumentException();
        }
        StringBuilder sb = new StringBuilder();
        for (String str : strs) {
            int length = str.length();
            if (length > Character.MAX_VALUE) {
                throw new IllegalArgumentException();
            }
            sb.append((char) length);
            sb.append(str);
        }
        return sb.toString();
    }

    // Time:    O(n)
    // Space:   O(n)
    public List<String> decode(String str) {
        List<String> result = new ArrayList<>();
        int initialLengthIndex = 0;

        while (initialLengthIndex != str.length()) {
            int length = str.charAt(initialLengthIndex);
            if (length > str.length() - 1) {
                throw new IllegalArgumentException();
            }
            int initialCutIndex = initialLengthIndex + length + 1;
            String message = str.substring(initialLengthIndex + 1, initialCutIndex);
            result.add(message);
            initialLengthIndex += length + 1;
        }
        return result;
    }
}
