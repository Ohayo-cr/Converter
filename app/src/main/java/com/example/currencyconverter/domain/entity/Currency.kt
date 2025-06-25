package com.example.currencyconverter.domain.entity

import com.example.currencyconverter.R

enum class Currency(
    val code: String,
    val symbol: String,
    val fullName: String,
    val flagResId: Int
) {
    USD("USD", "$", "US Dollar", R.drawable.flag_us),
    GBP("GBP", "£", "British Pound", R.drawable.flag_gb),
    EUR("EUR", "€", "Euro", R.drawable.flag_eu),
    AUD("AUD", "$", "Australian Dollar", R.drawable.flag_au),
    BGN("BGN", "лв", "Bulgarian Lev", R.drawable.flag_bg),
    BRL("BRL", "R$", "Brazilian Real", R.drawable.flag_br),
    CAD("CAD", "$", "Canadian Dollar", R.drawable.flag_ca),
    CHF("CHF", "Fr.", "Swiss Franc", R.drawable.flag_ch),
    CNY("CNY", "¥", "Chinese Yuan", R.drawable.flag_cn),
    CZK("CZK", "Kč", "Czech Koruna", R.drawable.flag_cz),
    DKK("DKK", "kr", "Danish Krone", R.drawable.flag_dk),
    HKD("HKD", "$", "Hong Kong Dollar", R.drawable.flag_hk),
    HRK("HRK", "kn", "Croatian Kuna", R.drawable.flag_hr),
    HUF("HUF", "Ft", "Hungarian Forint", R.drawable.flag_hu),
    IDR("IDR", "Rp", "Indonesian Rupiah", R.drawable.flag_id),
    ILS("ILS", "₪", "Israeli New Shekel", R.drawable.flag_il),
    INR("INR", "₹", "Indian Rupee", R.drawable.flag_in),
    ISK("ISK", "kr", "Icelandic Króna", R.drawable.flag_is),
    JPY("JPY", "¥", "Japanese Yen", R.drawable.flag_jp),
    KRW("KRW", "₩", "South Korean Won", R.drawable.flag_kr),
    MXN("MXN", "$", "Mexican Peso", R.drawable.flag_mx),
    MYR("MYR", "RM", "Malaysian Ringgit", R.drawable.flag_my),
    NOK("NOK", "kr", "Norwegian Krone", R.drawable.flag_no),
    NZD("NZD", "$", "New Zealand Dollar", R.drawable.flag_nz),
    PHP("PHP", "₱", "Philippine Peso", R.drawable.flag_ph),
    PLN("PLN", "zł", "Polish Złoty", R.drawable.flag_pl),
    RON("RON", "lei", "Romanian Leu", R.drawable.flag_ro),
    RUB("RUB", "₽", "Russian Ruble", R.drawable.flag_ru),
    SEK("SEK", "kr", "Swedish Krona", R.drawable.flag_se),
    SGD("SGD", "$", "Singapore Dollar", R.drawable.flag_sg),
    THB("THB", "฿", "Thai Baht", R.drawable.flag_th),
    TRY("TRY", "₺", "Turkish Lira", R.drawable.flag_tr),
    ZAR("ZAR", "R", "South African Rand", R.drawable.flag_za)
}