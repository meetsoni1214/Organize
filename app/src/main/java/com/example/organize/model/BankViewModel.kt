package com.example.organize.model

import androidx.lifecycle.*
import com.example.organize.data.BankAccount
import com.example.organize.data.BankAccountDao
import com.example.organize.data.BankRoomDatabase
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class BankViewModel(private val bankAccountDao: BankAccountDao): ViewModel() {

    val allAccounts: LiveData<List<BankAccount>> = bankAccountDao.getItems().asLiveData()

    private fun insertItem(bankAccount: BankAccount) {
        viewModelScope.launch {
            bankAccountDao.insert(bankAccount)
        }
    }
    fun deleteItem(bankAccount: BankAccount) {
        viewModelScope.launch {
            bankAccountDao.delete(bankAccount)
        }
    }
    private fun getNewAccountEntry(bankName: String, holderName: String, type: String, acNumber: String, ifsc: String, mobileNo: String, email: String, remarks: String,  nameOnCard: String, cardNo: String, expDate: String, cvc: String, atmPin: String, upiPin: String, loginPin: String, tPin: String, haveCard: Boolean, haveUpi: Boolean, haveApp: Boolean): BankAccount {
        return BankAccount(bankName = bankName, accountHolderName = holderName, accountType = type, accountNo = acNumber, ifscNo = ifsc, regMobileNo = mobileNo, regEmailId = email, accountRemarks = remarks, nameOnCard = nameOnCard, cardNumber = cardNo, cardCVV = cvc, cardAtmPin = atmPin, upiPin = upiPin, mobileLoginPin = loginPin, transactionPin = tPin, cardExpiryDate = expDate, haveCard = haveCard, haveUpi = haveUpi, haveApp = haveApp)
    }
    fun addNewAccount(bankName: String, holderName: String, type: String, acNumber: String, ifsc: String, mobileNo: String, email: String, remarks: String,  nameOnCard: String, cardNo: String, expDate: String, cvc: String, atmPin: String, upiPin: String, loginPin: String, tPin: String, haveCard: Boolean, haveUpi: Boolean, haveApp: Boolean) {
        val newAccount = getNewAccountEntry(bankName, holderName, type, acNumber, ifsc, mobileNo, email, remarks, nameOnCard, cardNo, expDate, cvc, atmPin, upiPin, loginPin, tPin, haveCard, haveUpi, haveApp)
        insertItem(newAccount)
    }
    fun retrieveAccount(id: Int): LiveData<BankAccount> {
        return bankAccountDao.getItem(id).asLiveData()
    }
    private fun updateAccount(bankAccount: BankAccount) {
        viewModelScope.launch {
            bankAccountDao.update(bankAccount)
        }
    }
    private fun getUpdatedEntry (accountId: Int, bankName: String, holderName: String, type: String, acNumber: String, ifsc: String, mobileNo: String, email: String, remarks: String,  nameOnCard: String, cardNo: String, expDate: String, cvc: String, atmPin: String, upiPin: String, loginPin: String, tPin: String, haveCard: Boolean, haveUpi: Boolean, haveApp: Boolean): BankAccount {
        return BankAccount(id = accountId, bankName = bankName, accountHolderName = holderName, accountType = type, accountNo = acNumber, ifscNo = ifsc, regMobileNo = mobileNo, regEmailId = email, accountRemarks = remarks, nameOnCard = nameOnCard, cardNumber = cardNo, cardCVV = cvc, cardAtmPin = atmPin, upiPin = upiPin, mobileLoginPin = loginPin, transactionPin = tPin, cardExpiryDate = expDate, haveCard = haveCard, haveUpi = haveUpi, haveApp = haveApp)
    }
    fun updateAccount (accountId: Int, bankName: String, holderName: String, type: String, acNumber: String, ifsc: String, mobileNo: String, email: String, remarks: String,  nameOnCard: String, cardNo: String, expDate: String, cvc: String, atmPin: String, upiPin: String, loginPin: String, tPin: String, haveCard: Boolean, haveUpi: Boolean, haveApp: Boolean) {
        val updatedAccount = getUpdatedEntry(accountId, bankName, holderName, type, acNumber, ifsc, mobileNo, email, remarks, nameOnCard, cardNo, expDate, cvc, atmPin, upiPin, loginPin, tPin, haveCard, haveUpi, haveApp)
        updateAccount(updatedAccount)
    }
    fun isEntryValid(bankName: String, holderName: String): Boolean {
        if (bankName.isBlank() || holderName.isBlank()) {
            return false
        }
        return true
    }
}
class BankViewModelFactory(private val bankAccountDao: BankAccountDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(BankViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return BankViewModel(bankAccountDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}