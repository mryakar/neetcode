package me.yakar.neetcode;

import java.util.HashSet;
import java.util.Set;

public class LongestConsecutiveSequence {
    public int longestConsecutive(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        if (nums.length < 2) {
            return nums.length;
        }

        Set<Integer> set = new HashSet<>();
        for (int i : nums) {
            set.add(i);
        }

        int max = 1;
        for (int i : set) {
            if (!set.contains(i - 1)) {
                int currentSequence = 1;
                int currentValue = i;
                while (set.contains(currentValue + 1)) {
                    currentValue++;
                    currentSequence++;
                }
                max = Math.max(max, currentSequence);
            }
        }

        return max;
    }
}
