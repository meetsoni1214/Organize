package com.example.organize.data

import com.example.organize.R
import com.example.organize.model.PasswordCategory

object DataSource {
    val Categories = listOf<PasswordCategory>(
        PasswordCategory(R.string.bank_category, R.drawable.bank_category_image),
        PasswordCategory(R.string.social_media_category, R.drawable.social_media_category_image),
        PasswordCategory(R.string.email_category, R.drawable.email_account_category_image)
    )
}
