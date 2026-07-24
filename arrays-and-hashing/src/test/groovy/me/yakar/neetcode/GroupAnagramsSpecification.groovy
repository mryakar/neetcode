package me.yakar.neetcode

import spock.lang.Specification
import spock.lang.Subject

class GroupAnagramsSpecification extends Specification {

    @Subject
    def solution = new GroupAnagrams()

    def "should throw #expectedException for #inputArrayDescription"() {
        when:
        solution.groupAnagrams(inputArray)

        then:
        thrown(expectedException)

        where:
        inputArray        || inputArrayDescription                    || expectedException
        null              || "null input"                             || NullPointerException
        new String[1001]  || "too big input"                          || IllegalArgumentException
        new String[]{"A"} || "input that contains invalid characters" || IllegalArgumentException
    }

    def "should return anagram group when suitable input array provided"() {
        expect:
        solution.groupAnagrams(inputArray as String[]).collect { it.sort() } =~ expected.collect { it.sort() }

        where:
        inputArray                                    || expected
        ["act", "pots", "tops", "cat", "stop", "hat"] || [["hat"], ["act", "cat"], ["stop", "pots", "tops"]]
        ["x"]                                         || [["x"]]
    }
}
