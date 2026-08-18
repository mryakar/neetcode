package me.yakar.neetcode;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ThreeSum {
    public List<List<Integer>> threeSum(int[] nums) {
        if (nums == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }
        Arrays.sort(nums);
        List<List<Integer>> solution = new ArrayList<>();
        for (int a = 0; a < nums.length - 2; a++) {
            if (a > 0 && nums[a] == nums[a - 1]) {
                continue;
            }
            int left = a + 1;
            int right = nums.length - 1;
            while (left < right) {
                int zeroSum = nums[a] + nums[left] + nums[right];

                if (zeroSum == 0) {
                    solution.add(List.of(nums[a], nums[left], nums[right]));
                    do {
                        left++;
                    } while (left < right && nums[left] == nums[left - 1]);
                } else if (zeroSum < 0) {
                    left++;
                } else {
                    right--;
                }

            }
        }
        return solution;
    }
}
