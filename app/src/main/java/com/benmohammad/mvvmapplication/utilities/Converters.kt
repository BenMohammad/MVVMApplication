package com.benmohammad.mvvmapplication.utilities

import java.util.*

fun dateToMillis(day: Int, month: Int, year: Int, minute: Int, hour: Int): Long {
    val calendar = Calendar.getInstance()
    calendar.set(year, month, day, hour, minute, 0)
    return calendar.timeInMillis
}

fun convertMillis(timeInMilliSeconds: Long): List<Int> {
    val dateValues = arrayListOf<Int>()
    val date = Date(timeInMilliSeconds)
    val calendar = Calendar.getInstance()
    calendar.time = date

    dateValues.add(calendar.get(Calendar.DAY_OF_MONTH))
    dateValues.add(calendar.get(Calendar.MONTH))
    dateValues.add(calendar.get(Calendar.YEAR))
    dateValues.add(calendar.get(Calendar.HOUR_OF_DAY))
    dateValues.add(calendar.get(Calendar.MINUTE))

    return dateValues

}


fun convertNumberToMonthName(month: Int): String {
    return when(month) {
        0 -> "January"
        1 -> "February"
        2 -> "March"
        3 -> "April"
        4 -> "May"
        5 -> "June"
        6 -> "July"
        7 -> "August"
        8 -> "September"
        9 -> "October"
        10 -> "November"
        11 -> "December"
        else -> "Invalid"
    }
}

fun stringToAscii(title: String): Int {
    var ascii = 0

    for(item in title) {
        ascii += item.toInt()
    }
    return ascii
}