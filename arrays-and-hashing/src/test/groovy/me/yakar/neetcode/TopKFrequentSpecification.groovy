package me.yakar.neetcode

import spock.lang.Specification
import spock.lang.Subject

class TopKFrequentSpecification extends Specification {

    @Subject
    def solution = new TopKFrequent();

    def "should throw #exception.simpleName when #scenario passed"() {
        when:
        solution.topKFrequent(nums as int[], k)

        then:
        thrown(exception)

        where:
        scenario                     | nums      | k  || exception
        "null number array"          | null      | 1  || IllegalArgumentException
        "negative k value"           | [1]       | -1 || IllegalArgumentException
        "zero k value"               | [1]       | 0  || IllegalArgumentException
        "k one above distinct count" | [1, 1, 2] | 3  || IllegalArgumentException
        "k far above distinct count" | [1, 1, 2] | 5  || IllegalArgumentException
        "empty array"                | []        | 1  || IllegalArgumentException
    }

    def "should return top #k for #scenario"() {
        when:
        def result = solution.topKFrequent(nums as int[], k)

        then:
        result.length == k
        result.toList() as Set == expected as Set

        where:
        scenario                    | nums                  | k || expected
        "classic frequency split"   | [1, 1, 1, 2, 2, 3]    | 2 || [1, 2]
        "k equals distinct count"   | [4, 4, 5, 5, 5, 6]    | 3 || [4, 5, 6]
        "negatives and zero"        | [-1, -1, -1, 0, 0, 7] | 2 || [-1, 0]
        "all elements identical"    | [9, 9, 9, 9]          | 1 || [9]
        "frequency beats magnitude" | [100, 1, 1, 1, 100]   | 1 || [1]
    }
}
