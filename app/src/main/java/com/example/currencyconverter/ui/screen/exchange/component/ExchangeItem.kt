package com.example.currencyconverter.ui.screen.exchange.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.ui.utils.rounderNumber

@Composable
fun ExchangeItem(
    rate: ExchangeRate,
    baseCurrency: String,
    amount: String
) {

    val isSelected = rate.secondaryCurrency.code == baseCurrency
    val painter = painterResource(id = rate.secondaryCurrency.flagResId)

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painter,
                contentDescription = null,
                modifier = Modifier
                    .size(50.dp)

            )
            Spacer(modifier = Modifier.width(8.dp))
            Column {

                Text(
                    text = rate.secondaryCurrency.code,
                    fontWeight =  FontWeight.Bold
                )
                Text(
                    text = rate.secondaryCurrency.fullName,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray.copy(alpha = .6f)
                )

                    Text(
                        text = "Balance: ${rate.secondaryCurrency.symbol} ${rate.balanceAccount ?: 0.0}",
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray.copy(alpha = .6f)
                    )
                }


            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                val sign = if (isSelected) "+" else "-"
                val valueToBD = if (isSelected) amount else rate.secondaryValue.rounderNumber(2)
                Text(
                    text ="$sign${rate.secondaryCurrency.symbol}",
                    fontWeight = FontWeight.Bold ,
                    modifier = Modifier.padding(end = 4.dp),

                    )
                BasicTextField(
                    value = valueToBD,
                    onValueChange = {},
                    enabled = false,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    modifier = Modifier.width(IntrinsicSize.Min)

                )

            }
        }
    }
    HorizontalDivider(Modifier.height(1.dp), color = Color.LightGray)
}