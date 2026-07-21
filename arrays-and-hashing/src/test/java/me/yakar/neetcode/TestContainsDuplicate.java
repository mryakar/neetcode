package me.yakar.neetcode;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TestContainsDuplicate {
    @Test
    void shouldReturnTrueWhenArrayContainsDuplicate() {
        // Given
        int[] numbers = {1, 2, 3, 1};
        ContainsDuplicate solution = new ContainsDuplicate();

        // When
        boolean containsDuplicate = solution.hasDuplicate(numbers);

        // Then
        assertTrue(containsDuplicate);
    }

    @Test
    void shouldReturnFalseWhenArrayContainsDuplicate() {
        // Given
        int[] numbers = {1, 2, 3, 4};
        ContainsDuplicate solution = new ContainsDuplicate();

        // When
        boolean containsDuplicate = solution.hasDuplicate(numbers);

        // Then
        assertFalse(containsDuplicate);
    }
}
