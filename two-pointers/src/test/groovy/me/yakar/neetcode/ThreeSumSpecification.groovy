package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ThreeSumSpecification extends Specification {

    private static final int RUNS = 200

    @Subject
    def solution = new ThreeSum()

    @Shared
    def random = new Random(42L)

    def "should return #expected for #scenario"() {
        expect:
        normalize(solution.threeSum(nums as int[])) == expected as Set

        where:
        scenario                                  | nums                     || expected
        "the smallest array with no triplet"      | [0, 1, 1]                || []
        "three zeros"                             | [0, 0, 0]                || [[0, 0, 0]]
        "four zeros still yielding one triplet"   | [0, 0, 0, 0]             || [[0, 0, 0]]
        "the canonical example"                   | [-1, 0, 1, 2, -1, -4]    || [[-1, -1, 2], [-1, 0, 1]]
        "all positive numbers"                    | [1, 2, 3, 4]             || []
        "all negative numbers"                    | [-4, -3, -2, -1]         || []
        "input already sorted"                    | [-1, -1, 0, 1, 2]        || [[-1, -1, 2], [-1, 0, 1]]
        "input in reverse order"                  | [2, 1, 0, -1, -1]        || [[-1, -1, 2], [-1, 0, 1]]
        "a duplicate pair inside the two pointers"| [-2, 0, 0, 2, 2]         || [[-2, 0, 2]]
        "many duplicates of every value"          | [-1, -1, -1, 2, 2, 2]    || [[-1, -1, 2]]
        "two distinct triplets"                   | [-3, -1, 0, 1, 2, 3]     || [[-3, 0, 3], [-3, 1, 2], [-1, 0, 1]]
        "a triplet needing the same value twice"  | [-4, 2, 2]               || [[-4, 2, 2]]
        "values at the constraint boundaries"     | [-100000, 0, 100000]     || [[-100000, 0, 100000]]
        "a boundary sum split across two values"  | [-100000, 50000, 50000]  || [[-100000, 50000, 50000]]
    }

    def "should throw IllegalArgumentException when nums is null"() {
        when:
        solution.threeSum(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should return triplets that are well formed and free of duplicates (iteration #iterationIndex)"() {
        when:
        List<List<Integer>> result = solution.threeSum(nums as int[])

        then: "every triplet has three elements summing to zero"
        result.every { it.size() == 3 }
        result.every { it.sum() == 0 }

        and: "no triplet is reported twice, regardless of internal order"
        result.collect { it.toSorted() }.toSet().size() == result.size()

        and: "every triplet can actually be built from three distinct positions in the input"
        result.every { isDrawableFrom(it, nums) }

        where:
        nums << (1..RUNS).collect { randomNums() }
    }

    def "should agree with the brute-force oracle (iteration #iterationIndex)"() {
        expect:
        normalize(solution.threeSum(nums as int[])) == oracle(nums)

        where:
        nums << (1..RUNS).collect { randomNums() }
    }

    private static Set<List<Integer>> normalize(List<List<Integer>> triplets) {
        triplets.collect { it.toSorted() }.toSet()
    }

    private static Set<List<Integer>> oracle(List<Integer> nums) {
        Set<List<Integer>> found = []
        for (int i = 0; i < nums.size(); i++) {
            for (int j = i + 1; j < nums.size(); j++) {
                for (int k = j + 1; k < nums.size(); k++) {
                    if (nums[i] + nums[j] + nums[k] == 0) {
                        found << [nums[i], nums[j], nums[k]].toSorted()
                    }
                }
            }
        }
        found
    }

    // Can this triplet be drawn from three distinct positions of the input?
    private static boolean isDrawableFrom(List<Integer> triplet, List<Integer> nums) {
        List<Integer> pool = new ArrayList<>(nums)
        triplet.every { value ->
            int index = pool.indexOf(value)
            if (index < 0) {
                return false
            }
            pool.remove(index)   // now genuinely by index, and that is what we want
            true
        }
    }

    // Tiny value range on purpose: a wide one almost never produces a triplet
    // summing to zero, which would leave the non-empty path untested.
    private List<Integer> randomNums() {
        int size = 3 + random.nextInt(8)
        (0..<size).collect { random.nextInt(9) - 4 }
    }
}