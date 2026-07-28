package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ProductsOfArrayExceptSelfSpecification extends Specification {

    private static final int RUNS = 100

    @Subject
    ProductsOfArrayExceptSelf solution = new ProductsOfArrayExceptSelf()

    @Shared
    Random random = new Random(42L)

    def "should throw IllegalArgumentException when nums is null"() {
        when:
        solution.productExceptSelf(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should return #expected for #scenario"() {
        expect:
        solution.productExceptSelf(nums as int[]).toList() == expected

        where:
        scenario                          | nums             || expected
        "the smallest valid input"        | [3, 4]           || [4, 3]
        "a plain positive array"          | [1, 2, 4, 6]     || [48, 24, 12, 8]
        "a single zero sparing one index" | [-1, 0, 1, 2, 3] || [0, -6, 0, 0, 0]
        "two zeros collapsing everything" | [1, 0, 0, 4]     || [0, 0, 0, 0]
        "an odd number of negatives"      | [-1, -2, -3]     || [6, 3, 2]
        "an even number of negatives"     | [-1, -2, -3, -4] || [-24, -12, -8, -6]
        "identity elements only"          | [1, 1, 1]        || [1, 1, 1]
    }

    def "should satisfy output[i] * nums[i] == total product for zero-free input #nums"() {
        given: "the product of every element, which is only meaningful without zeros"
        int total = nums.inject(1) { acc, value -> acc * value }

        when:
        int[] result = solution.productExceptSelf(nums as int[])

        then:
        (0..<nums.size()).every { result[it] * nums[it] == total }

        where:
        nums << [[3, 4], [1, 2, 4, 6], [-1, -2, -3], [2, -5, 3, 1], [20, 20, 20]]
    }

    def "should agree with the brute-force oracle (iteration #iterationIndex)"() {
        expect:
        solution.productExceptSelf(nums as int[]).toList() == bruteForce(nums)

        where:
        nums << (1..RUNS).collect { randomNums() }
    }

    private static List<Integer> bruteForce(List<Integer> nums) {
        (0..<nums.size()).collect { int i ->
            int product = 1
            for (int j = 0; j < nums.size(); j++) {
                if (j != i) {
                    product *= nums[j]
                }
            }
            product
        }
    }

    // Bounded by the 32-bit guarantee, not by the stated constraints:
    // 20^7 = 1.28e9 fits in an int, 20^8 = 2.56e10 does not.
    private List<Integer> randomNums() {
        int size = 2 + random.nextInt(6)                 // 2..7
        (0..<size).collect { random.nextInt(41) - 20 }   // -20..20
    }
}
