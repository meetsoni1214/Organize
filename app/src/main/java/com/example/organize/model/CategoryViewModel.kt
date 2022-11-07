package com.example.organize.model

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.organize.data.SocialMediaAccount

enum class SelectedStatus {YES, NO}

class CategoryViewModel : ViewModel() {


    private val _selectedCategoryld = MutableLiveData<PasswordCategory>()
    val selectedCategoryld: LiveData<PasswordCategory> = _selectedCategoryld

    private val _bankAccount = MutableLiveData<BankAccount>()
    val bankAccount: LiveData<BankAccount> = _bankAccount

    private val _extraBankAccount = MutableLiveData<BankAccountExtra>()
    val extraBankAccount: LiveData<BankAccountExtra> = _extraBankAccount

//    private val _socialMediaAccount = MutableLiveData<SocialMediaAccount>()
//    val socialMediaAccount: LiveData<SocialMediaAccount> = _socialMediaAccount

    private val _facebookAccount = MutableLiveData<FacebookAccount>()
    val facebookAccount: LiveData<FacebookAccount> = _facebookAccount

    private val _instagramAccount = MutableLiveData<InstagramAccount>()
    val instagramAccount: LiveData<InstagramAccount> = _instagramAccount

    private val _snapchatAccount = MutableLiveData<SnapchatAccount>()
    val snapchatAccount: LiveData<SnapchatAccount> = _snapchatAccount

    private val _twitterAccount = MutableLiveData<TwitterAccount>()
    val twitterAccount: LiveData<TwitterAccount> = _twitterAccount

    private val _linkedinAccount = MutableLiveData<LinkedinAccount>()
    val linkedinAccount: LiveData<LinkedinAccount> = _linkedinAccount

    private val _atmCardStatus = MutableLiveData<SelectedStatus>()
    val atmCardStatus: LiveData<SelectedStatus> = _atmCardStatus

    private val _upiStatus = MutableLiveData<SelectedStatus>()
    val upiStatus: LiveData<SelectedStatus> = _upiStatus

    private val _mobileAppStatus = MutableLiveData<SelectedStatus>()
    val mobileAppStatus: LiveData<SelectedStatus> = _mobileAppStatus

    fun setCategory(category: PasswordCategory) {
        _selectedCategoryld.value = category
    }
    init {
        val fbAccount = FacebookAccount()
        val igAccount = InstagramAccount()
        val scAccount = SnapchatAccount()
        val twAccount = TwitterAccount()
        val liAccount = LinkedinAccount()
        _facebookAccount.value = fbAccount
        _instagramAccount.value = igAccount
        _snapchatAccount.value = scAccount
        _twitterAccount.value = twAccount
        _linkedinAccount.value = liAccount
        _atmCardStatus.value = SelectedStatus.YES
        _upiStatus.value = SelectedStatus.YES
        _mobileAppStatus.value = SelectedStatus.YES
    }
    fun setBankAccount(AcHolderName: String, bankName: String, accountType: String, accountNo: String, ifscNo: String, regMobile: String, regEmail: String) {
        val account = BankAccount(bankName, AcHolderName, accountType, accountNo, ifscNo, regMobile, regEmail)
        _bankAccount.value = account
    }
    fun addExtraBankDetails(nameOnCard: String, cardNum: String, expiryDate: String, cardCvv: String, cardAtmPin: String, UpiPin: String, mobileLogin: String, mobilePass: String) {
        val extraDetails = BankAccountExtra(nameOnCard, cardNum, expiryDate, cardCvv, cardAtmPin, UpiPin, mobileLogin, mobilePass)
        _extraBankAccount.value = extraDetails
    }
//    fun setSocialMediaAccount(title: String, loginId: String, password: String, remarks: String) {
//        val account = SocialMediaAccount(title, loginId, password, remarks)
//        _socialMediaAccount.value = account
//    }

    fun setFacebookAccount(title: String, loginId: String, password: String, remarks: String) {
        val account = FacebookAccount(title, loginId, password, remarks)
        _facebookAccount.value = account
    }
    fun setInstagramAccount(title: String, loginId: String, password: String, remarks: String) {
        val account = InstagramAccount(title, loginId, password, remarks)
        _instagramAccount.value = account
    }
    fun setSnapchatAccount(title: String, loginId: String, password: String, remarks: String) {
        val account = SnapchatAccount(title, loginId, password, remarks)
        _snapchatAccount.value = account
    }
    fun setTwitterAccount(title: String, loginId: String, password: String, remarks: String) {
        val account = TwitterAccount(title, loginId, password, remarks)
        _twitterAccount.value = account
    }
    fun setLinkedInAccount(title: String, loginId: String, password: String, remarks: String) {
        val account = LinkedinAccount(title, loginId, password, remarks)
        _linkedinAccount.value = account
    }
    fun noAtmSelected() {
        _atmCardStatus.value = SelectedStatus.NO
    }
    fun yesAtmSelected() {
        _atmCardStatus.value = SelectedStatus.YES
    }
    fun noUpiSelected() {
        _upiStatus.value = SelectedStatus.NO
    }
    fun yesUpiSelected() {
        _upiStatus.value = SelectedStatus.YES
    }
    fun noBankingAppSelected() {
        _mobileAppStatus.value = SelectedStatus.NO
    }
    fun yesBankingAppSelected() {
        _mobileAppStatus.value = SelectedStatus.YES
    }
    fun resetBankAcDetails() {
        _bankAccount.value = BankAccount()
        _extraBankAccount.value = BankAccountExtra()
        yesAtmSelected()
        yesUpiSelected()
        yesBankingAppSelected()
    }
//    fun resetSocialMediaAcDetails() {
//        _socialMediaAccount.value = SocialMediaAccount()
//    }
}