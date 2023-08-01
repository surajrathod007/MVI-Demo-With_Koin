package com.surajrathod.mvidemo.intent

import com.surajrathod.mvidemo.model.UsersItem

sealed class MainIntent{

    object fetchUser : MainIntent()
    data class showMessage(val msg : String?) : MainIntent()

}
