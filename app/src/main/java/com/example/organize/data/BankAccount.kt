package com.example.organize.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "bank_account")
data class BankAccount (
 @PrimaryKey(autoGenerate = true) val id: Int = 0,
 @ColumnInfo(name = "bank_name") val bankName: String,
 @ColumnInfo(name = "account_holder_name") val accountHolderName: String,
 @ColumnInfo(name = "account_type") val accountType: String,
 @ColumnInfo(name = "account_no") val accountNo: String,
 @ColumnInfo(name = "ifsc_no") val ifscNo: String,
 @ColumnInfo(name = "reg_mobile_no") val regMobileNo: String,
 @ColumnInfo(name = "reg_email_id") val regEmailId: String,
 @ColumnInfo(name = "remarks") val accountRemarks: String,
 @ColumnInfo(name = "name_on_card") val nameOnCard: String,
 @ColumnInfo(name = "card_number") val cardNumber: String,
 @ColumnInfo(name = "expiry_date") val cardExpiryDate: String,
 @ColumnInfo(name = "card_cvv")val cardCVV: String,
 @ColumnInfo(name = "card_atm_pin")val cardAtmPin: String,
 @ColumnInfo(name = "upi_pin") val upiPin: String,
 @ColumnInfo(name = "mobile_login_pin")val mobileLoginPin: String,
 @ColumnInfo(name = "transaction_pin")val transactionPin: String,
 @ColumnInfo(name = "have_card")val haveCard: Boolean,
 @ColumnInfo(name = "have_upi")val haveUpi: Boolean,
 @ColumnInfo(name = "have_app")val haveApp: Boolean
)