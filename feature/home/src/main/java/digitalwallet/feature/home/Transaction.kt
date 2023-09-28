package digitalwallet.feature.home

import androidx.compose.ui.graphics.Color

data class Transaction(
    val id: Int,
    val icon: Int,
    val merchantName: String,
    val category: String,
    val amount: String,
    val amountColor: Color
)
