package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class EncodeDecodeStringSpecification extends Specification {

    private static final int RUNS = 100

    private static final String HOSTILE_PAYLOAD = new String([0, '#', ',', '\n', 128, 199, 255] as char[])

    @Subject
    EncodeDecodeString codec = new EncodeDecodeString()

    @Shared
    Random random = new Random(42L)

    def "should throw #exception.simpleName when encoding #scenario"() {
        when:
        codec.encode(strs)

        then:
        thrown(exception)

        where:
        scenario                     | strs                 || exception
        "a null list"                | null                 || IllegalArgumentException
        "a list holding a null"      | [null]               || IllegalArgumentException
        "a chunk beyond char range"  | ["a".repeat(70_000)] || IllegalArgumentException
    }

    def "should throw #exception.simpleName when decoding #scenario"() {
        when:
        codec.decode(encoded)

        then:
        thrown(exception)

        where:
        scenario                       | encoded                              || exception
        "a null stream"                | null                                 || IllegalArgumentException
        "a stream truncated at the first chunk" | [5, 'a'] as char[] as String || IllegalArgumentException
        "a stream truncated at a later chunk"   | [1, 'a', 4, 'b', 'c', 'd'] as char[] as String || IllegalArgumentException
    }

    def "should round-trip #scenario"() {
        expect:
        codec.decode(codec.encode(strs)) == strs

        where:
        scenario                              | strs
        "an empty list"                       | []
        "a single blank chunk"                | [""]
        "three blank chunks"                  | ["", "", ""]
        "a blank chunk between two others"    | ["a", "", "b"]
        "the simplest single chunk"           | ["a"]
        "multiple distinct chunks"            | ["I", "love", "this", "job"]
        "a chunk at the constraint boundary"  | ["x".repeat(199)]
        "a payload full of delimiter-like and high-byte chars" | [HOSTILE_PAYLOAD]
    }

    def "should round-trip randomly generated input (iteration #iterationIndex)"() {
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
