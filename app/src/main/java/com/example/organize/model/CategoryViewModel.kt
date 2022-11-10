package com.example.organize.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel


//enum class SelectedStatus {YES, NO}

class CategoryViewModel : ViewModel() {


    private val _selectedCategoryld = MutableLiveData<PasswordCategory>()
    val selectedCategoryld: LiveData<PasswordCategory> = _selectedCategoryld
//
//    private val _bankAccount = MutableLiveData<BankAccount>()
//    val bankAccount: LiveData<BankAccount> = _bankAccount

//    private val _extraBankAccount = MutableLiveData<BankAccountExtra>()
//    val extraBankAccount: LiveData<BankAccountExtra> = _extraBankAccount
//



//    private val _atmCardStatus = MutableLiveData<SelectedStatus>()
//    val atmCardStatus: LiveData<SelectedStatus> = _atmCardStatus
//
//    private val _upiStatus = MutableLiveData<SelectedStatus>()
//    val upiStatus: LiveData<SelectedStatus> = _upiStatus
//
//    private val _mobileAppStatus = MutableLiveData<SelectedStatus>()
//    val mobileAppStatus: LiveData<SelectedStatus> = _mobileAppStatus

    fun setCategory(category: PasswordCategory) {
        _selectedCategoryld.value = category
    }
//    init {
//        _atmCardStatus.value = SelectedStatus.YES
//        _upiStatus.value = SelectedStatus.YES
//        _mobileAppStatus.value = SelectedStatus.YES
//    }
//    fun setBankAccount(AcHolderName: String, bankName: String, accountType: String, accountNo: String, ifscNo: String, regMobile: String, regEmail: String) {
//        val account = BankAccount(bankName, AcHolderName, accountType, accountNo, ifscNo, regMobile, regEmail)
//        _bankAccount.value = account
//    }
//    fun addExtraBankDetails(nameOnCard: String, cardNum: String, expiryDate: String, cardCvv: String, cardAtmPin: String, UpiPin: String, mobileLogin: String, mobilePass: String) {
//        val extraDetails = BankAccountExtra(nameOnCard, cardNum, expiryDate, cardCvv, cardAtmPin, UpiPin, mobileLogin, mobilePass)
//        _extraBankAccount.value = extraDetails
//    }
//
//    fun noAtmSelected() {
//        _atmCardStatus.value = SelectedStatus.NO
//    }
//    fun yesAtmSelected() {
//        _atmCardStatus.value = SelectedStatus.YES
//    }
//    fun noUpiSelected() {
//        _upiStatus.value = SelectedStatus.NO
//    }
//    fun yesUpiSelected() {
//        _upiStatus.value = SelectedStatus.YES
//    }
//    fun noBankingAppSelected() {
//        _mobileAppStatus.value = SelectedStatus.NO
//    }
//    fun yesBankingAppSelected() {
//        _mobileAppStatus.value = SelectedStatus.YES
//    }
//    fun resetBankAcDetails() {
//        _bankAccount.value = BankAccount()
//        _extraBankAccount.value = BankAccountExtra()
//        yesAtmSelected()
//        yesUpiSelected()
//        yesBankingAppSelected()
//    }
}