package me.yakar.neetcode

import spock.lang.Specification
import spock.lang.Subject

class ValidAnagramSpecification extends Specification {

    @Subject
    def solution = new ValidAnagram()

    def "should return #expected when #description"() {
        expect:
        solution.isAnagram(s1 as String, s2 as String) == expected

        where:
        s1     | s2    | expected || description
        null   | "str" | false    || "only first parameter are null"
        "str"  | null  | false    || "only second parameter is null"
        null   | null  | false    || "both parameters are null"
        ""     | ""    | true    || "both parameters are empty"
        "str1" | "str" | false    || "length of the parameters are not equal"
    }

    def "should return true when \"abacus\" and \"cusaba\" are given"() {
        given:
        def str1 = "abacus"
        def str2 = "cusaba"

        when:
        def isAnagram = solution.isAnagram(str1, str2)

        then:
        isAnagram
    }

}
