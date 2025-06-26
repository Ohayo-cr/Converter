package com.example.currencyconverter.ui.screen.currency.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.currencyconverter.domain.entity.ExchangeRate
import com.example.currencyconverter.ui.utils.rounderNumber

@Composable
fun RateItem(
    rate: ExchangeRate,
    baseCurrency: String,
    onClick: () -> Unit,
    onAmountChange: (String) -> Unit,
    amount: String,
) {

    val isDoubleMode = amount != "1"
    val isSelected = rate.secondaryCurrency.code == baseCurrency
    val painter = painterResource(id = rate.secondaryCurrency.flagResId)
    if (isDoubleMode && !isSelected && (rate.balanceAccount ?: 0.0) < rate.secondaryValue) {
        Box(modifier = Modifier
            .height(0.dp)
            .alpha(0f)) {}
        return
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(2.dp)
            .clickable { onClick() }
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
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal
                )
                Text(
                    text = rate.secondaryCurrency.fullName,
                    fontWeight = FontWeight.Normal,
                    color = Color.DarkGray.copy(alpha = .6f)
                )
                rate.balanceAccount?.let {
                    Text(
                        text = "Balance: ${rate.secondaryCurrency.symbol} ${rate.balanceAccount.rounderNumber()}",
                        fontWeight = FontWeight.Normal,
                        color = Color.DarkGray.copy(alpha = .6f)
                    )
                }
            }

            Row(
                modifier = Modifier
                    .weight(1f)
                    .wrapContentWidth(Alignment.End),
                horizontalArrangement = Arrangement.End,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = rate.secondaryCurrency.symbol,
                    fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                    modifier = Modifier.padding(end = 4.dp),

                    )
                val allowedChars = Regex("^[0-9.]*$")

                BasicTextField(
                    value = if (isSelected) amount else rate.secondaryValue.rounderNumber(),
                    onValueChange = { input ->
                        if (input.matches(allowedChars)) {
                            onAmountChange(input)
                        }
                    },
                    enabled = isSelected,
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    textStyle = LocalTextStyle.current.copy(textAlign = TextAlign.Start),
                    modifier = Modifier.width(IntrinsicSize.Min)

                )
                if (isSelected && isDoubleMode) {
                    Spacer(modifier = Modifier.width(8.dp))
                    Box(
                        modifier = Modifier
                            .size(18.dp)
                            .clip(CircleShape)
                            .background(Color.Red.copy(alpha = 0.9f))
                            .clickable { onAmountChange("1") }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Close,
                            contentDescription = "Sum = 1",
                            tint = Color.White,
                            modifier = Modifier
                                .padding(2.dp)
                                .size(14.dp)
                        )
                    }
                }
            }
        }
    }
    HorizontalDivider(Modifier.height(1.dp), color = Color.LightGray)
}
