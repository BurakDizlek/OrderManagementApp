package com.bd.ordermanagementapp.screens.login

import com.bd.core.session.UserType

data class LoginUiViewState(
    val loading: Boolean = false,
    val successMessage: String? = null,
    val loggedInUserType: UserType? = null,
    val errorMessage: String? = null,
)