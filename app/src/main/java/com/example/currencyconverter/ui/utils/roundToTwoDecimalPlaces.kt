package com.example.currencyconverter.ui.utils

import java.math.BigDecimal
import java.math.RoundingMode

fun Double.roundToTwoDecimalPlaces(): Double {
    return BigDecimal(this).setScale(2, RoundingMode.HALF_UP).toDouble()
}