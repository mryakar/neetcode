package me.yakar.neetcode;

public class TwoSum2 {
    public int[] twoSum(int[] numbers, int target) {
        if (numbers == null) {
            throw new IllegalArgumentException("Input array is null");
        }
        int p1 = 0;
        int p2 = numbers.length - 1;
        while (p1 < p2) {
            int sum = numbers[p1] + numbers[p2];
            if (sum == target) {
                return new int[]{p1 + 1, p2 + 1};
            } else if (sum < target) {
                p1++;
            } else {
                p2--;
            }
        }
        throw new IllegalArgumentException("No solution found");
    }
}
