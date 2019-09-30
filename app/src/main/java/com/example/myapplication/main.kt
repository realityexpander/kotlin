package com.example.myapplication

import com.example.javatest.AutocompleteMain

//fun main() {
//    println("hello world")
//
//    val program = AutocompleteMain()
//    program.start()
//
//}

import java.time.LocalDate
import java.time.chrono.ChronoLocalDate
import java.time.temporal.ChronoUnit

fun main() {
    var date: ChronoLocalDate? = LocalDate.now()    // 1
    date = date?.plus(1, ChronoUnit.YEARS)

    if (date != null) {
        println(date.isLeapYear)                    // 2
    }

    if (date != null && date.isLeapYear) {          // 3
        println("It's a leap year!")
    }

    if (date == null || !date.isLeapYear) {         // 4
        println("There's no Feb 29 this year...")
    }

    if (date is LocalDate) {
        val month = date.monthValue                 // 5
        println(month)
    }
}