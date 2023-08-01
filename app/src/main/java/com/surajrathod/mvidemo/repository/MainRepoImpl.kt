package com.surajrathod.mvidemo.repository

import com.surajrathod.mvidemo.model.Users
import com.surajrathod.mvidemo.network.NetworkInterface

class MainRepoImpl(private val db : NetworkInterface) : MainRepository {

    override suspend fun loadUsers(): Users {
        return db.getUsers()
    }

}