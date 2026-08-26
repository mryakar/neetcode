package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class ValidParenthesesSpecification extends Specification {

    private static final int RUNS = 200
    private static final String ALPHABET = "()[]{}"

    @Subject
    ValidParentheses solution = new ValidParentheses()

    @Shared
    Random random = new Random(42L)

    def "should accept #scenario"() {
        expect:
        solution.isValid(input)

        where:
        scenario                                 | input
        "a single round pair"                    | "()"
        "a single square pair"                   | "[]"
        "a single curly pair"                    | "{}"
        "all three pair types side by side"      | "()[]{}"
        "the same pair repeated"                 | "()()()"
        "one pair nested in another"             | "([])"
        "three levels of nesting"                | "{[()]}"
        "deep nesting of one type"               | "((()))"
        "nesting and sequencing combined"        | "([]{})"
        "pair types in a different order"        | "(){}[]"
    }

    def "should reject #scenario"() {
        expect:
        !solution.isValid(input)

        where:
        scenario                                          | input
        "a lone closing round bracket"                    | ")"
        "a lone closing square bracket"                   | "]"
        "a lone closing curly bracket"                    | "}"
        "a closing bracket after a complete pair"         | "())"
        "closing before opening"                          | ")("
        "a lone opening bracket"                          | "("
        "two openings never closed"                       | "(("
        "an unclosed opening after a complete pair"       | "(()"
        "an odd number of openings"                       | "((("
        "a valid prefix followed by stray openings"       | "[({})](("
        "round opened but square closed"                  | "(]"
        "round opened but curly closed"                   | "(}"
        "square opened but round closed"                  | "[)"
        "curly opened but round closed"                   | "{)"
        "correct types closed in the wrong order"         | "([)]"
        "a crossed pair nested inside a valid one"        | "{[(])}"
    }

    def "should throw IllegalArgumentException when the input is null"() {
        when:
        solution.isValid(null)

        then:
        thrown(IllegalArgumentException)
    }

    def "should give the same verdict for the mirrored input #input"() {
        expect:
        solution.isValid(input) == solution.isValid(mirror(input))

        where:
        input << ["()", "([])", "()[]{}", "([]{})", "(", ")", "(]", "([)]", "(()", "[({})](("]
    }

    def "should still accept #input once wrapped in an outer pair"() {
        expect:
        solution.isValid("(" + input + ")")
        solution.isValid("[" + input + "]")
        solution.isValid("{" + input + "}")

        where:
        input << ["()", "([])", "{[()]}", "()[]{}", "((()))"]
    }

    def "should accept the concatenation of #left and #right"() {
        expect:
        solution.isValid(left + right)

        where:
        left       | right
        "()"       | "[]"
        "([])"     | "{}"
        "{[()]}"   | "()()"
        "((()))"   | "([]{})"
    }

    def "should agree with the strip-pairs oracle (iteration #iterationIndex)"() {
        expect:
        solution.isValid(input) == oracle(input)

        where:
        input << (1..RUNS).collect { randomInput() }
    }

    private static boolean oracle(String s) {
        String current = s
        String previous = null
        while (previous != current) {
            previous = current
            current = current.replace("()", "").replace("[]", "").replace("{}", "")
        }
        current.isEmpty()
    }

    private static String mirror(String s) {
        StringBuilder sb = new StringBuilder()
        for (int i = s.length() - 1; i >= 0; i--) {
            sb.append(flip(s.charAt(i)))
        }
        sb.toString()
    }

    private static char flip(char c) {
        switch (c) {
            case '(': return ')' as char
            case ')': return '(' as char
            case '[': return ']' as char
            case ']': return '[' as char
            case '{': return '}' as char
            default: return '{' as char
        }
    }

    // Short strings on purpose: at length 8 over six symbols a random string is
    // almost never balanced, so long inputs would leave the accept path untested.
    private String randomInput() {
        int length = 1 + random.nextInt(8)
        StringBuilder sb = new StringBuilder()
        length.times { sb.append(ALPHABET.charAt(random.nextInt(ALPHABET.length()))) }
        sb.toString()
    }
}
