package me.yakar.neetcode;

import java.util.HashSet;
import java.util.Set;


public class ContainsDuplicate {

    // Time:    O(n)
    // Space:   O(n)
    public boolean hasDuplicate(int[] numbers) {
        Set<Integer> set = new HashSet<>();
        for (int number : numbers) {
            boolean alreadyExists = set.contains(number);
            if (alreadyExists) {
                return true;
            }
            set.add(number);
        }
        return false;
    }
}
