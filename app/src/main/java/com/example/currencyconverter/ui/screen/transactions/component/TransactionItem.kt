package com.example.currencyconverter.ui.screen.transactions.component

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun TransactionItem(transaction: TransactionUiModel) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 2.dp),
    ) {
        Row(
            modifier = Modifier
                .padding(vertical = 2.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Row {
                Image(
                    painter = painterResource(id = transaction.from.flagResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )
                Text(
                    text = "${transaction.from.code} ${transaction.from.symbol}${transaction.fromAmount}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )

            }
            Row(
                modifier = Modifier,
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                Text(text = "→")
            }

            Row {
                Image(
                    painter = painterResource(id = transaction.to.flagResId),
                    contentDescription = null,
                    modifier = Modifier.size(24.dp)
                )

                Text(
                    text = "${transaction.to.code} ${transaction.to.symbol}${transaction.toAmount}",
                    modifier = Modifier.padding(horizontal = 8.dp)
                )
            }


        }

        Row(
                modifier = Modifier
                    .padding(vertical = 2.dp)
                    .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
        ) {
            Text(text = transaction.formattedDate)
            Text(text = "Purchased: ${transaction.to.fullName}")
        }
    }
    HorizontalDivider(Modifier.height(1.dp), color = Color.LightGray)
}
