package com.example.organize.model

import androidx.lifecycle.*
import com.example.organize.data.SocialMediaAccount
import com.example.organize.data.SocialMediaDao
import kotlinx.coroutines.launch
import java.lang.IllegalArgumentException

class SocialMediaViewModel(private val socialMediaDao: SocialMediaDao): ViewModel() {

    val allSocialMediaAccounts: LiveData<List<SocialMediaAccount>> = socialMediaDao.getItems().asLiveData()

    private fun insertItem(socialMediaAccount: SocialMediaAccount) {
        viewModelScope.launch {
            socialMediaDao.insert(socialMediaAccount)
        }
    }
    fun insertItems(socialMediaAccounts: List<SocialMediaAccount>) {
        viewModelScope.launch {
            socialMediaDao.insertAll(socialMediaAccounts)
        }
    }
    fun deleteItem(socialMediaAccount: SocialMediaAccount) {
        viewModelScope.launch {
            socialMediaDao.delete(socialMediaAccount)
        }
    }
    private fun getNewSocialMediaEntry(title: String, username: String, password: String, remarks: String): SocialMediaAccount {
        return SocialMediaAccount(accountTitle = title, accountLogin = username, accountPassword = password, accountRemarks = remarks)
    }
    fun addNewAccount(title: String, username: String, password: String, remarks: String) {
        val newAccount = getNewSocialMediaEntry(title, username, password, remarks)
        insertItem(newAccount)
    }
    fun retrieveAccount(id: Int): LiveData<SocialMediaAccount> {
        return socialMediaDao.getItem(id).asLiveData()
    }
    private fun updateAccount(socialMediaAccount: SocialMediaAccount) {
        viewModelScope.launch {
            socialMediaDao.update(socialMediaAccount)
        }
    }
    private fun getUpdatedEntry(accountId: Int, title: String, username: String, password: String, remarks: String, iconId: Int): SocialMediaAccount {
        return SocialMediaAccount(id = accountId, accountTitle = title, accountLogin = username, accountPassword = password, accountRemarks = remarks, accountIcon = iconId)
    }
    fun updateAccount(accountId: Int, title: String, username: String, password: String, remarks: String, iconId: Int) {
        val updatedAccount = getUpdatedEntry(accountId, title, username, password, remarks, iconId)
        updateAccount(updatedAccount)
    }
    fun isEntryValid(title: String, username: String, password: String, remarks: String): Boolean {
        return !(title.isBlank() && username.isBlank() && password.isBlank() && remarks.isBlank())
    }
}

class SocialMediaViewModelFactory(private val socialMediaDao: SocialMediaDao): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SocialMediaViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SocialMediaViewModel(socialMediaDao) as T
        }
        throw IllegalArgumentException("Unknown ViewModel Class")
    }
}