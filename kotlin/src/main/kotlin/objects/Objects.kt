package objects

fun main(args: Array<String>) {

    val parent = Parent(77)
    val child: Parent = object : Parent(10) {
        override val y = 25
    }

    println("Default val y of Parent is ${parent.y}")
    println("Overridden val y of Parent is ${child.y}")
    println("val x of Parent's Comp static object is ${Parent.Comp.x}")
    println("val y of Parent's Comp static object is ${Parent.Comp.y}")
    println("Insanely born Parent's value: ${Parent.Comp.create().y}")  //if keyword 'companion' was NOT used, you MUST define it's name
    println(Companion.create().i)   //if keyword 'companion' was used, you may not define it's name

    val companionWithFactory = CompanionWithFactory.create()
    println(companionWithFactory.str)

    val adHoc = object {
        var x: Int = 2
        var y: Int = 3
        val func = fun(){
            println("Method as variable!")
        }
    }

    println(adHoc.x + adHoc.y)
    adHoc.func()
    println("This singleton was inherited from Parent and it's y is ${SingletonDescendant.y}")

    val x = Singleton
    x.sout()


    // од внутри объ€вленного объекта может обращатьс€ к переменным за скобками так же, как вложенные анонимные классы в Java

    //ќбъ€вление объекта не может иметь локальный характер (т.е. быть вложенным непосредственно в функцию),
    //но может быть вложено в объ€вление другого объекта или какого-либо невложенного класса.

    //—уществует только одно смысловое различие между этими двум€ пон€ти€ми:
    //
    //анонимный объект инициализируетс€ сразу после того, как был использован
    //декларированный объект инициализируетс€ лениво, в момент первого к нему доступа
    //вспомогательный объект инициализируетс€ в момент, когда класс, к которому он относитс€, загружен и семантически совпадает со статическим инициализатором Java
}