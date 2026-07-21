package me.yakar.neetcode;

import java.util.HashSet;
import java.util.Set;


public class ContainsDuplicate {

    // Time:    O(n)
    // Space:   O(n)
    public boolean hasDuplicate(int[] numbers) {
        if (numbers == null || numbers.length == 0 || numbers.length == 1) {
            return false;
        }
        Set<Integer> set = new HashSet<>();
        for (int number : numbers) {
            if (!set.add(number)) {
                return true;
            }
        }
        return false;
    }
}
