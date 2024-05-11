package com.example.vpet_tam.model

//класс задействуется при первой генерации событий
data class EventDetails(val eventState : String,
                        val x_number :Double, val ev_time:Int, val stat:String, val type:String) {
}
