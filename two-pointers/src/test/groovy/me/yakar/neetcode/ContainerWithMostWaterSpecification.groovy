package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject
import spock.lang.Timeout

import java.util.concurrent.TimeUnit

class ContainerWithMostWaterSpecification extends Specification {

    private static final int RUNS = 200

    @Subject
    def solution = new ContainerWithMostWater()

    @Shared
    def random = new Random(42L)

    def "should return #expected for #scenario"() {
        expect:
        solution.maxArea(height as int[]) == expected

        where:
        scenario                                    | height                      || expected
        "the smallest possible input"               | [1, 1]                      || 1
        "two lines of different height"             | [1, 5]                      || 1
        "the canonical example"                     | [1, 8, 6, 2, 5, 4, 8, 3, 7] || 49
        "the widest pair winning over a taller one" | [4, 3, 2, 1, 4]             || 16
        "a tiny valley"                             | [1, 2, 1]                   || 2
        "all lines of equal height"                 | [3, 3, 3, 3]                || 9
        "strictly increasing heights"               | [1, 2, 3, 4, 5]             || 6
        "strictly decreasing heights"               | [5, 4, 3, 2, 1]             || 6
        "zeros at both ends"                        | [0, 9, 9, 0]                || 9
        "all zeros"                                 | [0, 0, 0]                   || 0
        "a single zero between two walls"           | [7, 0, 7]                   || 14
        "the best pair sitting in the middle"       | [1, 20, 20, 1]              || 20
        "heights at the constraint boundary"        | [10_000, 1, 10_000]         || 20_000
    }

    def "should throw IllegalArgumentException when height is null"() {
        when:
        solution.maxArea(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should give the same answer for a mirrored input"() {
        expect:
        solution.maxArea(height as int[]) == solution.maxArea(height.reverse() as int[])

        where:
        height << [[1, 1], [1, 8, 6, 2, 5, 4, 8, 3, 7], [4, 3, 2, 1, 4], [1, 2, 3, 4, 5], [0, 9, 9, 0], [7, 0, 7]]
    }

    def "should agree with the brute-force oracle (iteration #iterationIndex)"() {
        expect:
        solution.maxArea(height as int[]) == oracle(height)

        where:
        height << (1..RUNS).collect { randomHeights() }
    }

    @Timeout(value = 5, unit = TimeUnit.SECONDS)
    def "should stay linear on 100k lines"() {
        given:
        int[] height = (0..<100_000).collect { random.nextInt(10_001) } as int[]

        expect:
        solution.maxArea(height) > 0
    }

    private static int oracle(List<Integer> height) {
        int best = 0
        for (int i = 0; i < height.size(); i++) {
            for (int j = i + 1; j < height.size(); j++) {
                best = Math.max(best, Math.min(height[i], height[j]) * (j - i))
            }
        }
        best
    }

    // Small heights and short arrays on purpose: they make ties, zeros and
    // "the winner is not at the ends" cases happen often instead of rarely.
    private List<Integer> randomHeights() {
        int size = 2 + random.nextInt(9)
        (0..<size).collect { random.nextInt(8) }
    }
}
