package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

class LongestConsecutiveSequenceSpecification extends Specification {

    private static final int RUNS = 100

    @Subject
    def solution = new LongestConsecutiveSequence()

    @Shared
    Random random = new Random(42L)

    def "should return #expected for #scenario"() {
        expect:
        solution.longestConsecutive(nums as int[]) == expected

        where:
        scenario                                  | nums                                         || expected
        "an empty array"                          | []                                           || 0
        "a single element"                        | [7]                                          || 1
        "the same element repeated"               | [5, 5, 5]                                    || 1
        "two elements that are not consecutive"   | [1, 3]                                       || 1
        "two consecutive elements out of order"   | [3, 2]                                       || 2
        "a run scattered among unrelated numbers" | [2, 20, 4, 10, 3, 4, 5]                      || 4
        "a run with duplicates inside it"         | [0, 3, 2, 5, 4, 6, 1, 1]                     || 7
        "two runs where the longer comes second"  | [1, 2, 10, 11, 12]                           || 3
        "two runs where the longer comes first"   | [10, 11, 12, 1, 2]                           || 3
        "two runs of equal length"                | [1, 2, 50, 51]                               || 2
        "negative numbers only"                   | [-5, -3, -4, -2]                             || 4
        "a run crossing zero"                     | [-2, -1, 0, 1, 2]                            || 5
        "already sorted input"                    | [1, 2, 3, 4]                                 || 4
        "reverse sorted input"                    | [4, 3, 2, 1]                                 || 4
        "values at the constraint boundaries"     | [1_000_000_000, 999_999_999, -1_000_000_000] || 2
    }

    def "should throw IllegalArgumentException when nums is null"() {
        when:
        solution.longestConsecutive(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should agree with the sorting-based oracle (iteration #iterationIndex)"() {
        expect:
        solution.longestConsecutive(nums as int[]) == oracle(nums)

        where:
        nums << (1..RUNS).collect { randomNums() }
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "should stay linear on a fully consecutive run of 100k elements"() {
        given: "input that degenerates to O(n^2) if the sequence-start check is missing"
        int[] nums = (1..100_000) as int[]

        expect:
        solution.longestConsecutive(nums) == 100_000
    }

    private static int oracle(List<Integer> nums) {
        if (nums.isEmpty()) {
            return 0
        }
        List<Integer> distinct = nums.toSorted().unique()
        int longest = 1
        int current = 1
        for (int i = 1; i < distinct.size(); i++) {
            if (distinct[i] == distinct[i - 1] + 1) {
                current++
                longest = Math.max(longest, current)
            } else {
                current = 1
            }
        }
        longest
    }

    // Small range on purpose: it forces runs, duplicates and gaps to actually occur.
    private List<Integer> randomNums() {
        int size = random.nextInt(20)
        (0..<size).collect { random.nextInt(15) - 7 }
    }
}
