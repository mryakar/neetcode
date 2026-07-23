package me.yakar.neetcode

import spock.lang.Specification
import spock.lang.Subject

class TwoSumSpecification extends Specification {

    @Subject
    def solution = new TwoSum()

    def "should return two sum indexes as 0, 1 when numbers of 3, 4, 5, 6 and target number 7 are given"() {
        given:
        def numbers = [3, 4, 5, 6] as int[]
        def target = 7;

        when:
        def output = solution.twoSum(numbers, target)

        then:
        output == [0, 1] as int[]
    }

    def "should return two sum indexes as 0, 2 when numbers of 4, 5, 6 and target number 10 are given"() {
        given:
        def numbers = [4, 5, 6] as int[]
        def target = 10;

        when:
        def output = solution.twoSum(numbers, target)

        then:
        output == [0, 2] as int[]
    }

    def "should return two sum indexes as 0, 1 when numbers of 5, 5 and target number 10 are given"() {
        given:
        def numbers = [5, 5] as int[]
        def target = 10;

        when:
        def output = solution.twoSum(numbers, target)

        then:
        output == [0, 1] as int[]
    }
}
