package me.yakar.neetcode;

public class ValidAnagram {

    // Time:    O(n)
    // Space:   O(1)
    public boolean isAnagram(String s, String t) {
        if (s == null || t == null) {
            return false;
        }

        if (s.length() != t.length()) {
            return false;
        }

        int[] count = new int['z' - 'a' + 1];
        for (int i = 0; i < s.length(); i++) {
            count[s.charAt(i) - 'a']++;
            count[t.charAt(i) - 'a']--;
        }

        for (int c : count) {
            if (c != 0) {
                return false;
            }
        }

        return true;
    }
}
