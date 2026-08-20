package me.yakar.neetcode;

// Time:  O(n) — each step retires one line, so the pointers cover n positions total
// Space: O(1)
public class ContainerWithMostWater {
    public int maxArea(int[] heights) {
        if (heights == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        int maxArea = 0;
        int left = 0;
        int right = heights.length - 1;
        while (left < right) {
            int length = right - left;
            int leftHeight = heights[left];
            int rightHeight = heights[right];
            int height = Math.min(leftHeight, rightHeight);
            int currentArea = length * height;
            maxArea = Math.max(maxArea, currentArea);
            if (leftHeight < rightHeight) {
                left++;
            } else {
                right--;
            }
        }

        return maxArea;
    }
}
