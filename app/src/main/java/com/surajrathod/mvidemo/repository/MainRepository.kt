package com.surajrathod.mvidemo.repository

import com.surajrathod.mvidemo.model.Users

interface MainRepository {

    suspend fun loadUsers() : Users

}