package com.example.organize.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class SocialMediaCard(
    @StringRes val stringResourceId: Int,
    @DrawableRes val ImageResourceID: Int
)