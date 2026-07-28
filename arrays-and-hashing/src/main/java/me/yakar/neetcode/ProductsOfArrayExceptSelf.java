package me.yakar.neetcode;

public class ProductsOfArrayExceptSelf {

    // Time:    O(n)
    // Space:   O(n)
    public int[] productExceptSelf(int[] nums) {

        if (nums == null) {
            throw new IllegalArgumentException("nums must not be null");
        }

        int length = nums.length;
        int[] products = new int[length];

        int leftProduct = 1;
        for (int i = 0; i < length; i++) {
            products[i] = leftProduct;
            leftProduct *= nums[i];
        }

        int rightProduct = 1;
        for (int i = length - 1; i >= 0; i--) {
            products[i] *= rightProduct;
            rightProduct *= nums[i];
        }
        return products;
    }
}
