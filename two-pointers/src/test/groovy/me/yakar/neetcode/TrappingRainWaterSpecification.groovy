package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class TrappingRainWaterSpecification extends Specification {

    private static final int RUNS = 200

    @Subject
    def solution = new TrappingRainWater()

    @Shared
    def random = new Random(42L)

    def "should trap #expected units of water for #scenario"() {
        expect:
        solution.trap(height as int[]) == expected

        where:
        scenario                                 | height                               || expected
        "a single bar"                           | [0]                                  || 0
        "a single tall bar"                      | [4]                                  || 0
        "two equal bars with no gap"             | [1, 1]                               || 0
        "the smallest possible well"             | [2, 0, 2]                            || 2
        "the canonical LeetCode example"         | [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1] || 6
        "the second LeetCode example"            | [4, 2, 0, 3, 2, 5]                   || 9
        "the NeetCode example"                   | [0, 2, 0, 3, 1, 0, 1, 3, 2, 1]       || 9
        "strictly increasing bars"               | [1, 2, 3, 4, 5]                      || 0
        "strictly decreasing bars"               | [5, 4, 3, 2, 1]                      || 0
        "a flat surface"                         | [3, 3, 3]                            || 0
        "all bars of height zero"                | [0, 0, 0]                            || 0
        "a deep narrow well"                     | [5, 0, 5]                            || 5
        "a wide flat-bottomed well"              | [3, 0, 0, 0, 3]                      || 9
        "two separate shallow wells"             | [1, 0, 1, 0, 1]                      || 2
        "a stepped well"                         | [2, 1, 0, 1, 2]                      || 4
        "a well bounded by a shorter right wall" | [5, 1, 4]                            || 3
        "a well bounded by a shorter left wall"  | [4, 1, 5]                            || 3
        "water only on the left half"            | [3, 0, 3, 1, 0]                      || 3
        "water only on the right half"           | [0, 1, 3, 0, 3]                      || 3
        "bars at the constraint boundary"        | [100_000, 0, 100_000]                || 100_000
    }

    def "should throw IllegalArgumentException when height is null"() {
        when:
        solution.trap(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should not overflow on the largest total the constraints allow"() {
        given: "two maximal walls with 19,998 empty positions between them"
        int[] height = new int[20_000]
        height[0] = 100_000
        height[19_999] = 100_000

        expect:
        solution.trap(height) == 1_999_800_000
    }

    def "should trap the same amount for a mirrored elevation map"() {
        expect:
        solution.trap(height as int[]) == solution.trap(height.reverse() as int[])

        where:
        height << [[2, 0, 2], [4, 2, 0, 3, 2, 5], [0, 1, 0, 2, 1, 0, 1, 3, 2, 1, 2, 1],
                   [5, 1, 4], [3, 0, 3, 1, 0], [1, 2, 3, 4, 5]]
    }

    def "should scale the trapped water linearly when every bar is scaled by #factor"() {
        expect:
        solution.trap(scale(height, factor)) == factor * solution.trap(height as int[])

        where:
        height                      | factor
        [2, 0, 2]                   | 3
        [4, 2, 0, 3, 2, 5]          | 5
        [0,1,0,2,1,0,1,3,2,1,2,1]   | 7
        [3, 0, 0, 0, 3]             | 2
    }

    private static int[] scale(List<Integer> height, int factor) {
        int[] scaled = new int[height.size()]
        for (int i = 0; i < height.size(); i++) {
            scaled[i] = height[i] * factor
        }
        scaled
    }

    def "should agree with the brute-force oracle (iteration #iterationIndex)"() {
        expect:
        solution.trap(height as int[]) == oracle(height)

        where:
        height << (1..RUNS).collect { randomHeights() }
    }

    private static int oracle(List<Integer> height) {
        int total = 0
        for (int i = 0; i < height.size(); i++) {
            int leftMax = 0
            for (int j = 0; j <= i; j++) {
                leftMax = Math.max(leftMax, height[j])
            }
            int rightMax = 0
            for (int j = i; j < height.size(); j++) {
                rightMax = Math.max(rightMax, height[j])
            }
            total += Math.min(leftMax, rightMax) - height[i]
        }
        total
    }

    // Short arrays and low bars on purpose: they make wells, plateaus and ties
    // happen constantly. Full-range random heights almost never trap anything.
    private List<Integer> randomHeights() {
        int size = 1 + random.nextInt(10)
        (0..<size).collect { random.nextInt(6) }
    }
}
