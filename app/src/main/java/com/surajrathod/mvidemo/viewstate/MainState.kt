package com.surajrathod.mvidemo.viewstate

import com.surajrathod.mvidemo.model.Users

sealed class MainState{
    object Idle : MainState()
    object Loading : MainState()
    data class Users(val users: com.surajrathod.mvidemo.model.Users) : MainState()
    data class Error(val error: String?) : MainState()
    data class ShowMsg(val msg : String?) : MainState()
}
