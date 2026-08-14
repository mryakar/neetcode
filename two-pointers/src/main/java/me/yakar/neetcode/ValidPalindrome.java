package me.yakar.neetcode;

public class ValidPalindrome {
    public boolean isPalindrome(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }

        char[] chars = s
                .toLowerCase()
                .replaceAll("[^a-zA-Z0-9]", "")
                .toCharArray();

        int left = 0;
        int right = chars.length - 1;
        while (left < right) {
            if (chars[left] != chars[right]) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }
}
