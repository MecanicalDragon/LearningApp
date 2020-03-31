package classes

//data class means auto generating equals(), hashCode(), toString(), copy(), componentN()
data class DataClass(val name: String, val amount: Int)

//ѕервичный конструктор должен иметь как минимум один параметр;
//¬се параметры первичного конструктора должны быть отмечены, как val или var;
// лассы данных не могут быть абстрактными, open, sealed или inner;
//ƒата-классы не могут наследоватьс€ от других классов (но могут реализовывать интерфейсы).