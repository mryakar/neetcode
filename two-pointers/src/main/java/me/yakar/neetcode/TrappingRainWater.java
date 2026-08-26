package me.yakar.neetcode;

public class TrappingRainWater {
    public int trap(int[] height) {
        if (height == null) {
            throw new IllegalArgumentException("Input array cannot be null");
        }

        if (height.length < 3) {
            return 0;
        }

        int left = 0;
        int right = height.length - 1;
        int maxLeft = 0;
        int maxRight = 0;
        int water = 0;

        while (left < right) {
            if (height[left] <= height[right]) {
                maxLeft = Math.max(maxLeft, height[left]);
                water += maxLeft - height[left];
                left++;
            } else {
                maxRight = Math.max(maxRight, height[right]);
                water += maxRight - height[right];
                right--;
            }
        }

        return water;
    }
}
