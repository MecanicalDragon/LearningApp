package net.medrag.algtasks

/**
 * Task is about to find celebrity (a person in a set of people, who knows no one, but who is known by everyone else),
 * when you're allowed to only ask a single question: "Do you know exactly that person?".
 */
data class Person(
    val name: String,
    val knownPeople: Set<String>
) {
    fun doesKnow(another: Person): Boolean {
        return knownPeople.contains(another.name)
    }

    override fun hashCode(): Int {
        return name.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Person

        return name == other.name
    }

    override fun toString(): String {
        return "Person(name='$name')"
    }
}

val testData = listOf<Person>(
    Person("Jack", setOf("Jane", "Jimmie")),
    Person("Jimmie", setOf("Jane", "Donnie")),
    Person("Cortney", setOf("Donnie", "Jane", "Jack")),
    Person("Lora", setOf("Mickey", "Jane", "Marge")),
    Person("Jane", emptySet()),
//    Person("Jane", setOf("Mickey")),
    Person("Sandy", setOf("Jane", "Cortney")),
//    Person("Sandy", setOf("Cortney")),
    Person("Brian", setOf("Jane", "Mickey")),
    Person("Mickey", setOf("Jane", "Marge")),
    Person("Marge", setOf("Lora", "Jane", "Marge")),
)

/**
 * O(n) runtime complexity
 */
fun findCelebrity(people: List<Person>): Person? {
    var i = 0
    var j = people.lastIndex
    // remove from consideration people, who can't be celebrity (if person knows another one - he's not a celebrity.
    // If person doesn't know another one - another one can not be celebrity.) - O(n)
    while (i != j) {
        if (people[i].doesKnow(people[j])) {
            i++
        } else {
            j--
        }
    }
    // check the last remaining candidate for celebrity conditions - O(n)
    for (k in 0..people.lastIndex) {
        if (k != i && (!people[k].doesKnow(people[i]) || people[i].doesKnow(people[k]))) {
            return null
        }
    }
    return people[i]
}

fun main(args: Array<String>) {
    println(findCelebrity(testData))
}
