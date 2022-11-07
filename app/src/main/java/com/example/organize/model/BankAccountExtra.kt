package com.example.organize.model

data class BankAccountExtra(
    val nameOnCard: String? = "",
    val cardNumber: String? = "",
    val cardExpiryDate: String? = "",
    val cardCVV: String? = "",
    val cardAtmPin: String? = "",
    val upiPin: String? = "",
    val mobileLoginPin: String? = "",
    val transactionPin: String? = ""
)
