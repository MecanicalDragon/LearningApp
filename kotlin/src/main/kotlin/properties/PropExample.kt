package properties


/**
 * Самый распространённый тип свойств просто считывает (или записывает) данные из backing field.
 * Тем не менее, с пользовательскими геттерами и сеттерами мы можем реализовать совершенно любое поведение свойства.
 * В реальности, существуют общепринятые шаблоны того, как могут работать свойства. Несколько примеров:
 *
 * Вычисление значения свойства при первом доступе к нему (ленивые свойства)
 * Чтение из ассоциативного списка с помощью заданного ключа
 * Доступ к базе данных
 * Оповещение listener'а в момент доступа и т.п.
 *
 * {@author} Stanislav Tretyakov
 * 31.03.2020
 */
class PropExample {

    // lateinit var - used to avoid null checks, when variable is null during compilation, but initialized on startup.
    lateinit var lateInitField: String

    //  val prop cannot have a setter
    val customGetterVal: Int = 5
        get() = field * (Math.random() * 9).toInt()

    var customSetterVar: Int = 5
        set(value) {
            field = value * (Math.random() * 9).toInt()
        }

    val lazyField: String by lazy {
        println("Now lazyField is requested for the 1st time and it will be initialized now.")
        CONST
    }
}

// const val - constant, allowed only top-level or object-related Strings and primitives
const val CONST = "SOME_CONSTANT_VALUE"