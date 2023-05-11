package com.example.myapplication

class ModalCategory {

    //variables ,match as firebase
    var id:String = ""
    var category :String= ""
    var timestamp:Long=0
    var uid:String = ""

    // empty constructor , required by firebase
    constructor()
    constructor(id: String, category: String, timestamp: Long, uid: String) {
        this.id = id
        this.category = category
        this.timestamp = timestamp
        this.uid = uid
    }

    //parameterized constructor


}