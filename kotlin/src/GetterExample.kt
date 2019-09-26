class GetterExample {

    var example:String = ""
        get(){
        if (field == null)
        return "Lazy Initialized"
        else return field
    }
}