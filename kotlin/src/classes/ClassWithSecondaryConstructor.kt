package classes

class ClassWithSecondaryConstructor (name: String) {

    var secondName:String = "default"
    var age:Int = 0

    constructor(name:String, lastName: String) : this(name){
        this.secondName = lastName
    }

    constructor(name: String, lastName: String, age: Int) : this(name, lastName){
        this.age = age
    }
}

// If class has secondary constructor(s), it(they) must refer to the primary directly or indirectly through other constructors