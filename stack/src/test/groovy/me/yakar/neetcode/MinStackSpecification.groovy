package me.yakar.neetcode

import spock.lang.Shared
import spock.lang.Specification
import spock.lang.Subject

class MinStackSpecification extends Specification {

    private static final int RUNS = 200

    @Subject
    def stack = new MinStack()

    @Shared
    def random = new Random(42L)

    def "should report #expectedMin as the minimum and #expectedTop on top after pushing #values"() {
        given:
        values.each { stack.push(it) }

        expect:
        stack.getMin() == expectedMin
        stack.top() == expectedTop

        where:
        values                                 || expectedMin       | expectedTop
        [1]                                    || 1                 | 1
        [1, 2, 3]                              || 1                 | 3
        [3, 2, 1]                              || 1                 | 1
        [2, 2, 2]                              || 2                 | 2
        [-2, 0, -3]                            || -3                | -3
        [1, 2, 0]                              || 0                 | 0
        [5, -5, 5]                             || -5                | 5
        [0, -1, -1, 0]                         || -1                | 0
        [Integer.MIN_VALUE, Integer.MAX_VALUE] || Integer.MIN_VALUE | Integer.MAX_VALUE
        [Integer.MAX_VALUE, Integer.MIN_VALUE] || Integer.MIN_VALUE | Integer.MIN_VALUE
    }

    def "should restore the minimum to #expectedMin after pushing #values and popping #popCount times"() {
        given:
        values.each { stack.push(it) }
        popCount.times { stack.pop() }

        expect:
        stack.getMin() == expectedMin
        stack.top() == expectedTop

        where:
        values          | popCount || expectedMin | expectedTop
        [1, 2, 3]       | 1        || 1           | 2
        [3, 2, 1]       | 1        || 2           | 2
        [1, 1]          | 1        || 1           | 1
        [2, 1, 1]       | 1        || 1           | 1
        [2, 1, 1]       | 2        || 2           | 2
        [1, 2, 1]       | 1        || 1           | 2
        [0, 0, 0]       | 2        || 0           | 0
        [2, 2, 1, 1]    | 2        || 2           | 2
        [5, 3, 3, 5]    | 2        || 3           | 3
        [1, -1, -1, -1] | 2        || -1          | -1
        [-2, 0, -3]     | 1        || -2          | 0
        [1, 2, 0]       | 1        || 1           | 2
    }

    def "should walk through the canonical example one call at a time"() {
        when:
        stack.push(1)
        stack.push(2)
        stack.push(0)

        then:
        stack.getMin() == 0

        when:
        stack.pop()

        then:
        stack.top() == 2
        stack.getMin() == 1
    }

    def "should keep working after the stack has been emptied and refilled"() {
        given:
        stack.push(5)
        stack.push(1)
        stack.pop()
        stack.pop()

        when:
        stack.push(7)

        then:
        stack.top() == 7
        stack.getMin() == 7
    }

    def "should leave the stack unchanged when top and getMin are called repeatedly"() {
        given:
        stack.push(4)
        stack.push(2)

        expect:
        stack.top() == 2
        stack.top() == 2
        stack.getMin() == 2
        stack.getMin() == 2
        stack.top() == 2
    }

    def "should agree with a plain-list model across a random call sequence (iteration #iterationIndex)"() {
        given:
        List<Integer> model = []

        expect:
        operations.each { operation ->
            switch (operation.name) {
                case "push":
                    stack.push(operation.value)
                    model.add(operation.value)
                    break
                case "pop":
                    stack.pop()
                    model.removeLast()
                    break
                case "top":
                    assert stack.top() == model.last()
                    break
                case "getMin":
                    assert stack.getMin() == Collections.min(model)
                    break
            }
        }

        where:
        operations << (1..RUNS).collect { randomOperations() }
    }

    // Values drawn from a tiny range on purpose: duplicates of the current
    // minimum are the whole point, and a wide range would make them vanish.
    private List<Map> randomOperations() {
        List<Map> operations = []
        int depth = 0
        (1..(5 + random.nextInt(26))).each {
            String name = depth == 0 ? "push" : ["push", "push", "pop", "top", "getMin"][random.nextInt(5)]
            if (name == "push") {
                operations << [name: "push", value: random.nextInt(11) - 5]
                depth++
            } else {
                operations << [name: name]
                if (name == "pop") {
                    depth--
                }
            }
        }
        operations
    }
}
