import me.yakar.neetcode.ContainsDuplicate
import spock.lang.Specification
import spock.lang.Subject

class TestContainsDuplicate extends Specification {

    @Subject
    def solution = new ContainsDuplicate()

    def "hasDuplicate returns #expected for #description"() {
        expect:
        solution.hasDuplicate(numbers as int[]) == expected

        where:
        numbers      | expected | description
        [1, 2, 3, 1] | true     | "array with duplicate"
        [1, 1]       | true     | "adjacent duplicates"
        [1, 2, 3, 4] | false    | "array with no duplicate"
        []           | false    | "empty array"
        [7]          | false    | "single element"
    }

    def "should detect duplicate in large input"() {
        given:
        def numbers = (-100000..100000) + [50000]

        when:
        def result = solution.hasDuplicate(numbers as int[])

        then:
        result
    }
}