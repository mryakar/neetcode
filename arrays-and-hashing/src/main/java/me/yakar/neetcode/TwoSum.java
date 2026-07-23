package me.yakar.neetcode;

import java.util.HashMap;
import java.util.Map;

public class TwoSum {

    // Time:    O(n)
    // Space:   O(n)
    public int[] twoSum(int[] nums, int target) {
        Map<Integer, Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            int remain = target - nums[i];

            if (map.containsKey(remain)) {
                return new int[]{map.get(remain), i};
            } else {
                map.put(nums[i], i);
            }
        }
        return new int[]{0, 0};
    }
}
