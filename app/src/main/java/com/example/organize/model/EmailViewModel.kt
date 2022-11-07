package com.example.organize.model

import androidx.lifecycle.*
import com.example.organize.data.EmailAccount
import com.example.organize.data.EmailDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

enum class HasEmail {YES, NO}

class EmailViewModel(private val emailDao: EmailDao): ViewModel() {

    val allEmails: LiveData<List<EmailAccount>> = emailDao.getItems().asLiveData()

    private fun insertItem(emailAccount: EmailAccount) {
        viewModelScope.launch {
            emailDao.insert(emailAccount)
        }
    }
    fun deleteItem(emailAccount: EmailAccount) {
        viewModelScope.launch {
            emailDao.delete(emailAccount)
        }
    }
     private fun getNewEmailEntry(title: String, email: String, password: String, remarks: String): EmailAccount {
        return EmailAccount(accountTitle = title, accountEmail = email, accountPassword = password, accountRemarks = remarks)
    }
    fun addNewEmail(title: String, email: String, password: String, remarks: String){
        val newEmailAccount = getNewEmailEntry(title, email, password, remarks)
        insertItem(newEmailAccount)
    }
    fun retrieveEmail(id: Int): LiveData<EmailAccount> {
        return emailDao.getItem(id).asLiveData()
    }
    private fun updateEmailAccount(emailAccount: EmailAccount) {
        viewModelScope.launch {
            emailDao.update(emailAccount)
        }
    }
    private fun getUpdatedEntry(emailId: Int, title: String, email: String, password: String, remarks: String): EmailAccount {
        return EmailAccount(id = emailId, accountTitle = title, accountEmail = email, accountPassword = password, accountRemarks = remarks)
    }
    fun updateEmailAccount(emailId: Int, title: String, email: String, password: String, remarks: String) {
        val updatedEmailAccount = getUpdatedEntry(emailId, title, email, password, remarks)
        updateEmailAccount(updatedEmailAccount)
    }
    fun isEntryValid(title: String, email: String, password: String, remarks: String): Boolean {
        if (title.isBlank() && email.isBlank() && password.isBlank() && remarks.isBlank()) {
            return false
        }
        return true
    }
}
class EmailViewModelFactory(private val emailDao: EmailDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EmailViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EmailViewModel(emailDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}