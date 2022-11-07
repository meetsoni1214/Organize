package com.example.organize.data

import com.example.organize.R
import com.example.organize.model.PasswordCategory
import com.example.organize.model.SocialMediaCard

object DataSource {
    val Categories = listOf<PasswordCategory>(
        PasswordCategory(R.string.bank_category, R.drawable.bank_category_image),
        PasswordCategory(R.string.social_media_category, R.drawable.social_media_category_image),
        PasswordCategory(R.string.email_category, R.drawable.email_account_category_image)
    )
    val SocialMediaPlatforms = listOf<SocialMediaCard>(
        SocialMediaCard(R.string.facebook, R.drawable.facebook_icon),
        SocialMediaCard(R.string.instagram, R.drawable.ig_icon),
        SocialMediaCard(R.string.snapchat, R.drawable.snapchat),
        SocialMediaCard(R.string.twitter, R.drawable.twitter_icon),
        SocialMediaCard(R.string.linkedin, R.drawable.linkedin_icon)
    )
}
