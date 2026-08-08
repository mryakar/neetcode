package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ValidPalindromeSpecification extends Specification {

    private static final int RUNS = 200

    @Subject
    def solution = new ValidPalindrome()

    @Shared
    def random = new Random(42L)

    def "should accept #scenario"() {
        expect:
        solution.isPalindrome(input)

        where:
        scenario                                          | input
        "a single character"                              | "a"
        "a single digit"                                  | "7"
        "two identical characters"                        | "aa"
        "an odd-length palindrome"                        | "aba"
        "an even-length palindrome"                       | "abba"
        "a palindrome that differs only in case"          | "AbBa"
        "a digits-only palindrome"                        | "12321"
        "letters and digits mixed"                        | "1a2a1"
        "a string with no alphanumeric characters at all" | ".,!?"
        "a single space"                                  | " "
        "punctuation around a palindrome"                 | "?!aba!?"
        "the canonical phrase example"                    | "Was it a car or a cat I saw?"
        "a phrase with commas and a colon"                | "A man, a plan, a canal: Panama"
        "dotted and dotless I in the same string"         | "Ii"
    }

    def "should reject #scenario"() {
        expect:
        !solution.isPalindrome(input)

        where:
        scenario                                         | input
        "two different characters"                       | "ab"
        "a short non-palindrome"                         | "abc"
        "the canonical negative example"                 | "tab a cat"
        "a phrase that is not a palindrome"              | "race a car"
        "a digit paired with a letter 32 apart in ASCII" | "0P"
        "a mismatch only in the middle"                  | "abcXdba"
        "a mismatch only in the digits"                  | "1a2a3"
        "letters that match but digits that do not"      | "a1bb2a"
    }

    def "should throw IllegalArgumentException when the input is null"() {
        when:
        solution.isPalindrome(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should give the same verdict for a string and its reverse"() {
        expect:
        solution.isPalindrome(input) == solution.isPalindrome(input.reverse())

        where:
        input << ["aba", "abba", "ab", "race a car", "Was it a car or a cat I saw?", "0P", ".,!?"]
    }

    def "should give the same verdict when the case is flipped"() {
        expect:
        solution.isPalindrome(input) == solution.isPalindrome(swapCase(input))

        where:
        input << ["aba", "AbBa", "ab", "tab a cat", "A man, a plan, a canal: Panama", "Ii"]
    }

    def "should give the same verdict when punctuation is injected"() {
        expect:
        solution.isPalindrome(input) == solution.isPalindrome(input.split("").join("-*-"))

        where:
        input << ["aba", "abba", "ab", "race a car", "12321"]
    }

    def "should agree with the clean-and-reverse oracle (iteration #iterationIndex)"() {
        expect:
        solution.isPalindrome(input) == oracle(input)

        where:
        input << (1..RUNS).collect { randomInput() }
    }

    private static boolean oracle(String s) {
        String cleaned = s.toLowerCase(Locale.ROOT).findAll(/[a-z0-9]/).join()
        cleaned == cleaned.reverse()
    }

    private static String swapCase(String s) {
        s.collect { Character.isUpperCase(it as char) ? it.toLowerCase() : it.toUpperCase() }.join()
    }

    // Deliberately tiny alphabet: a wide one almost never produces a palindrome,
    // so the accept path would go untested.
    private static final List<String> ALPHABET = ["a", "A", "b", "B", "1", "2", " ", ",", "."]

    private String randomInput() {
        int length = 1 + random.nextInt(8)
        (0..<length).collect { ALPHABET[random.nextInt(ALPHABET.size())] }.join()
    }
}