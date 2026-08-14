package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class TwoSum2Specification extends Specification {

    private static final int RUNS = 200

    @Subject
    def solution = new TwoSum2()

    @Shared
    Random random = new Random(42L)

    def "should return #expected for #scenario"() {
        expect:
        solution.twoSum(numbers as int[], target).toList() == expected

        where:
        scenario                                     | numbers                  | target || expected
        "the smallest possible array"                | [1, 2]                   | 3      || [1, 2]
        "a pair at the two ends"                     | [1, 3, 4, 7]             | 8      || [1, 4]
        "a pair in the middle"                       | [1, 3, 4, 9]             | 7      || [2, 3]
        "a pair at the very start"                   | [2, 3, 10, 20]           | 5      || [1, 2]
        "a pair at the very end"                     | [1, 2, 8, 9]             | 17     || [3, 4]
        "two equal numbers forming the pair"         | [1, 5, 5, 8]             | 10     || [2, 3]
        "duplicates that are not part of the answer" | [0, 0, 0, 0, 2, 3]       | 5      || [5, 6]
        "many leading zeros before the answer"       | [0, 0, 0, 0, 0, 0, 2, 3] | 5      || [7, 8]
        "negative numbers only"                      | [-9, -5, -3, -1]         | -8     || [2, 3]
        "a pair crossing zero"                       | [-4, -1, 0, 3, 7]        | 2      || [2, 4]
        "a negative target"                          | [-7, -2, 4, 8]           | -9     || [1, 2]
        "a target of zero"                           | [-6, -2, 2, 5]           | 0      || [2, 3]
        "values at the lower constraint boundary"    | [-1000, -500, 0, 500]    | -1500  || [1, 2]
        "values at the upper constraint boundary"    | [0, 500, 1000, 1000]     | 2000   || [3, 4]
        "the sum of the two largest values"          | [1, 2, 999, 1000]        | 1999   || [3, 4]
    }

    def "should throw IllegalArgumentException when numbers is null"() {
        when:
        solution.twoSum(null, 5)

        then:
        thrown(IllegalArgumentException)
    }

    def "should return indices whose values add up to the target (iteration #iterationIndex)"() {
        given:
        int[] numbers = testCase.numbers as int[]
        int target = testCase.target

        when:
        int[] result = solution.twoSum(numbers, target)

        then: "the pair is well formed"
        result.length == 2
        result[0] >= 1
        result[1] <= numbers.length
        result[0] < result[1]

        and: "the values behind those 1-indexed positions hit the target"
        numbers[result[0] - 1] + numbers[result[1] - 1] == target

        where:
        testCase << (1..RUNS).collect { randomCase() }
    }

    def "should agree with the brute-force oracle (iteration #iterationIndex)"() {
        expect:
        solution.twoSum(testCase.numbers as int[], testCase.target).toList() ==
                oracle(testCase.numbers, testCase.target)

        where:
        testCase << (1..RUNS).collect { randomCase() }
    }

    private static List<Integer> oracle(List<Integer> numbers, int target) {
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers[i] + numbers[j] == target) {
                    return [i + 1, j + 1]
                }
            }
        }
        throw new IllegalStateException("generator produced a case with no solution")
    }

    // The spec guarantees exactly one valid pair, so the generator must build
    // a case around a chosen pair and then verify uniqueness before yielding it.
    private Map randomCase() {
        while (true) {
            int size = 2 + random.nextInt(6)
            List<Integer> numbers = (0..<size).collect { random.nextInt(21) - 10 }.toSorted()
            int i = random.nextInt(size - 1)
            int j = i + 1 + random.nextInt(size - i - 1)
            int target = numbers[i] + numbers[j]
            if (countPairs(numbers, target) == 1) {
                return [numbers: numbers, target: target]
            }
        }
    }

    private static int countPairs(List<Integer> numbers, int target) {
        int count = 0
        for (int i = 0; i < numbers.size(); i++) {
            for (int j = i + 1; j < numbers.size(); j++) {
                if (numbers[i] + numbers[j] == target) {
                    count++
                }
            }
        }
        count
    }
}
