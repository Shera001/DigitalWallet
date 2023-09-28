package digitalwallet.feature.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.AssistChip
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize(8F)
            .padding(12.dp)
    ) {
        HomeToolbar(Modifier.weight(1F))
        Balance(Modifier.weight(2F))
        TransactionHistory(
            modifier = Modifier.weight(4F),
            itemsMap = getTransactions()
        )
        BottomCard(modifier = Modifier.weight(1F))
    }
}

@Composable
internal fun HomeToolbar(modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
            .fillMaxWidth()
            .padding(bottom = 32.dp),
    ) {
        Image(
            modifier = Modifier.weight(1F),
            painter = painterResource(R.drawable.round_person_24),
            contentDescription = "Account",
            alignment = Alignment.CenterStart
        )

        Text(
            modifier = Modifier.weight(1F),
            text = "Digital Wallet",
            color = Color.Gray,
            fontSize = 18.sp
        )

        Row(
            modifier = Modifier.weight(1F),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                painter = painterResource(R.drawable.baseline_access_time_24),
                contentDescription = "Support"
            )

            Text(text = "Support", color = Color.Gray)
        }
    }
}

@Composable
internal fun Balance(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Image(painter = painterResource(id = R.drawable.round__eye_24), contentDescription = "Hide")
        Spacer(modifier = Modifier.height(8.dp))
        Row {
            Text(text = "10,000", fontSize = 28.sp)
            Spacer(modifier = Modifier.width(4.dp))
            Text(text = "ETHB", color = Color.Cyan, fontSize = 28.sp)
        }
    }
}

@Composable
internal fun TransactionHistory(
    modifier: Modifier = Modifier,
    itemsMap: Map<String, List<Transaction>>
) {
    Column(modifier = modifier) {
        Text(text = "Latest transaction", fontSize = 18.sp)
        Spacer(modifier = Modifier.height(8.dp))
        FilterTransaction()
        TransactionList(grouped = itemsMap)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun FilterTransaction() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.fillMaxWidth()
    ) {
        AssistChip(
            onClick = { },
            label = { Text(text = "Category") },
            trailingIcon = {
                DropDownIcon()
            }
        )
        AssistChip(
            onClick = { },
            label = { Text(text = "Status") },
            trailingIcon = {
                DropDownIcon()
            }
        )
        AssistChip(
            onClick = { },
            label = { Text(text = "Date") },
            leadingIcon = {
                Icon(
                    painter = painterResource(id = R.drawable.round_date_range_24),
                    contentDescription = "Date"
                )
            }
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun TransactionList(grouped: Map<String, List<Transaction>>) {
    LazyColumn {
        grouped.forEach { (header, body) ->
            stickyHeader {
                Text(text = header, modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                )
            }

            items(body) { transaction: Transaction ->
                ItemTransaction(item = transaction)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun ItemTransaction(item: Transaction) {
    ListItem(
        leadingContent = {
            Icon(
                painter = painterResource(id = item.icon),
                contentDescription = null
            )
        },
        headlineText = { Text(text = item.merchantName) },
        supportingText = { Text(text = item.category) },
        trailingContent = {
            Row {
                Text(text = item.amount)
                Text(text = "eTHB", color = item.amountColor)
            }
        }
    )
}

@Composable
fun BottomCard(modifier: Modifier = Modifier) {
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 6.dp
        ),
        modifier = modifier
            .fillMaxWidth()
            .padding(12.dp),
        colors = CardDefaults.cardColors(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.round_settings),
                contentDescription = "Settings",
                tint = Color.Gray
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.round_qr_code),
                    contentDescription = "Send",
                    tint = Color.Cyan
                )
                Spacer(modifier = Modifier.width(5.dp))
                Text(text = "send")
            }
            Icon(
                painter = painterResource(id = R.drawable.round_location_on),
                contentDescription = "Location",
                tint = Color.Gray
            )
        }
    }
}

@Composable
internal fun DropDownIcon() {
    Icon(
        painter = painterResource(id = R.drawable.outline_keyboard_arrow_down_24),
        contentDescription = null,
        tint = Color.Cyan
    )
}

private fun getTransactions(): Map<String, List<Transaction>> {
    return mapOf(
        "Today" to listOf(
            Transaction(
                0,
                R.drawable.round__eye_24,
                "Merchant",
                "Category",
                "-1.000",
                amountColor = Color.Magenta
            ),
            Transaction(
                0,
                R.drawable.round__eye_24,
                "Merchant",
                "Category",
                "+10.000",
                amountColor = Color.Cyan
            ),
            Transaction(
                0,
                R.drawable.round__eye_24,
                "Merchant",
                "Category",
                "+10.000",
                amountColor = Color.Cyan
            )
        ),
        "January 15, Sat" to listOf(
            Transaction(
                0,
                R.drawable.round_settings,
                "Merchant",
                "Category",
                "-100",
                amountColor = Color.Magenta
            ),
            Transaction(
                0,
                R.drawable.round_settings,
                "Merchant",
                "Category",
                "-100",
                amountColor = Color.Magenta
            ),
            Transaction(
                0,
                R.drawable.round_settings,
                "Merchant",
                "Category",
                "-100",
                amountColor = Color.Magenta
            ),
            Transaction(
                0,
                R.drawable.round_settings,
                "Merchant",
                "Category",
                "-100",
                amountColor = Color.Magenta
            )
        ),
        "January 1, Tue" to listOf(
            Transaction(
                0,
                R.drawable.baseline_qr_code,
                "Merchant",
                "Category",
                "100",
                amountColor = Color.Magenta
            ),
            Transaction(
                0,
                R.drawable.baseline_qr_code,
                "Merchant",
                "Category",
                "100",
                amountColor = Color.Magenta
            )
        )
    )
}