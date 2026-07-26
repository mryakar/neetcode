package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EncodeDecodeStringSpecification extends Specification {

    @Subject
    def codec = new EncodeDecodeString();

    @Shared
    Random random = new Random(42L)

    private static final int RUNS = 100

    def "should throw #exception when #scenario encoding scenario"() {
        when:
        codec.encode(strs)

        then:
        thrown(exception)

        where:
        scenario                                  | strs                || exception
        "null message list"                       | null                || IllegalArgumentException
        "a list that contains a too long message" | ["a".repeat(70000)] || IllegalArgumentException
    }

    def "should throw #exception when #scenario decoding scenario"() {
        when:
        codec.decode(str)

        then:
        thrown(exception)

        where:
        scenario                                                                                | str                                   || exception
        "a malformed message that does not contain total number of characters same with length" | new String(new char[]{(char) 5, 'a'}) || IllegalArgumentException
    }

    def "should return the same value at round trip with #scenario"() {
        expect:
        codec.decode(codec.encode(strs)) == strs

        where:
        strs                         || scenario
        []                           || "empty message list"
        [""]                         || "message list with a blank string"
        ["", "", ""]                 || "message list with three blank strings"
        ["a"]                        || "simplest message \"a\""
        ["I", "love", "this", "job"] || "multiple different messages"

    }

    def "should return round-trips randomly generated input (iteration #iterationIndex)"() {
        expect:
        codec.decode(codec.encode(strs)) == strs

        where:
        strs << (1..RUNS).collect { randomList() }
    }

    private List<String> randomList() {
        int size = random.nextInt(100)
        (0..<size).collect { randomString() }
    }

    private String randomString() {
        int length = random.nextInt(200)
        char[] chars = new char[length]
        length.times { chars[it] = (char) random.nextInt(256) }
        new String(chars)
    }
}
