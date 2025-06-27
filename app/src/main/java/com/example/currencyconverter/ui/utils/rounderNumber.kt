package com.example.currencyconverter.ui.utils

fun Double.rounderNumber(maxDecimals: Int = 5): String {
    return if (this == toLong().toDouble()) {
        toLong().toString()
    } else {
        "%.${maxDecimals}f".format(this).trimEnd('0').trimEnd(',')
    }
}