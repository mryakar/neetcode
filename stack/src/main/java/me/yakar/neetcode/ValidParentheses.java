package me.yakar.neetcode;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Map;
import java.util.Objects;

public class ValidParentheses {
    public boolean isValid(String s) {
        if (s == null) {
            throw new IllegalArgumentException("Input string cannot be null");
        }
        if (s.length() % 2 != 0) {
            return false;
        }

        Map<Character, Character> pairs = Map.of(
                ')', '(',
                ']', '[',
                '}', '{'
        );
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (pairs.containsValue(c)) {
                stack.push(c);
            } else if (pairs.containsKey(c) && (stack.isEmpty() || !Objects.equals(stack.pop(), pairs.get(c)))) {
                return false;
            }
        }

        return stack.isEmpty();
    }
}
