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


    //��� ������ ������������ ������� ����� ���������� � ���������� �� �������� ��� ��, ��� ��������� ��������� ������ � Java

    //���������� ������� �� ����� ����� ��������� �������� (�.�. ���� ��������� ��������������� � �������),
    //�� ����� ���� ������� � ���������� ������� ������� ��� ������-���� ������������ ������.

    //���������� ������ ���� ��������� �������� ����� ����� ����� ���������:
    //
    //��������� ������ ���������������� ����� ����� ����, ��� ��� �����������
    //��������������� ������ ���������������� ������, � ������ ������� � ���� �������
    //��������������� ������ ���������������� � ������, ����� �����, � �������� �� ���������, �������� � ������������ ��������� �� ����������� ��������������� Java
}