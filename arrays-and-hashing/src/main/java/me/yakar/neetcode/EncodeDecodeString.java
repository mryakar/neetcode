package me.yakar.neetcode;

import java.util.ArrayList;
import java.util.List;

public class EncodeDecodeString {

    // Time:  O(m)   m = sum of all chunk lengths
    // Space: O(m)
    public String encode(List<String> strs) {
        if (strs == null) {
            throw new IllegalArgumentException("Message list must not be null");
        }

        StringBuilder sb = new StringBuilder();
        for (String chunk : strs) {
            if (chunk == null) {
                throw new IllegalArgumentException("Message list must not contain null elements");
            }

            int length = chunk.length();
            if (length > Character.MAX_VALUE) {
                throw new IllegalArgumentException(
                        "chunk length "
                                + length
                                + " exceeds the maximum encodable length "
                                + (int) Character.MAX_VALUE
                );
            }
            sb
                    .append((char) length)
                    .append(chunk);
        }
        return sb.toString();
    }

    // Time:  O(m)
    // Space: O(m)
    public List<String> decode(String encoded) {
        if (encoded == null) {
            throw new IllegalArgumentException("encoded must not be null");
        }

        List<String> result = new ArrayList<>();
        int cursor = 0;
        while (cursor < encoded.length()) {
            int declaredLength = encoded.charAt(cursor);
            int remaining = encoded.length() - cursor - 1;

            if (declaredLength > remaining) {
                throw new IllegalArgumentException(
                        "malformed stream at index "
                                + cursor
                                + ": header declares "
                                + declaredLength
                                + " chars, only "
                                + remaining
                                + " remain"
                );
            }

            int chunkStart = cursor + 1;
            int chunkEnd = chunkStart + declaredLength;
            result.add(encoded.substring(chunkStart, chunkEnd));
            cursor = chunkEnd;
        }
        return result;
    }
}
