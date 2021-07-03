package com.example.cryptotracker.presentation.util

fun String.toSplitCoinPrice() : String {
    val splittedString = this.split(".")

    if (splittedString.size > 1) {
        var indexOfFirstNonZero = 0

        if (splittedString[0].startsWith('0')) {
            for (index in splittedString[1].indices) {
                if (splittedString[1][index] != '0') {
                    indexOfFirstNonZero = index
                    break
                }
            }
        }

        return if (splittedString[1].length > indexOfFirstNonZero + 4)
            splittedString[0] + "." + splittedString[1].subSequence(0, indexOfFirstNonZero + 5)
        else if (splittedString[1].length > indexOfFirstNonZero + 3)
            splittedString[0] + "." + splittedString[1].subSequence(0, indexOfFirstNonZero + 4)
        else if (splittedString[1].length > indexOfFirstNonZero + 2)
            splittedString[0] + "." + splittedString[1].subSequence(0, indexOfFirstNonZero + 3)
        else if (splittedString[1].length > indexOfFirstNonZero + 1)
            splittedString[0] + "." + splittedString[1].subSequence(0, indexOfFirstNonZero + 2)
        else if (splittedString[1].length == indexOfFirstNonZero && indexOfFirstNonZero != 0)
            splittedString[0] + "." + splittedString[1].subSequence(0, indexOfFirstNonZero) + "0"
        else
            splittedString[0] + ".00"
    } else
        return splittedString[0]
}

fun Double.toSplitChangeRate() : String {
    val splittedString = this.toString().split(".")

    return if (splittedString.size > 1) {

        if (splittedString[1].length > 1)
            splittedString[0] + "." + splittedString[1].subSequence(0, 2)
        else if (splittedString[1].length == 1 )
            splittedString[0] + "." + splittedString[1]+ "0"
        else
            splittedString[0] + ".00"
    } else
        splittedString[0]
}

fun Float.toSplitCoinPrice() : String {
    val splittedString = this.toString().split(".")

    return if (splittedString.size > 1) {

        if (splittedString[1].length > 1)
            splittedString[0] + "." + splittedString[1].subSequence(0, 2)
        else if (splittedString[1].length == 1 )
            splittedString[0] + "." + splittedString[1]+ "0"
        else
            splittedString[0] + ".00"
    } else
        splittedString[0]
}