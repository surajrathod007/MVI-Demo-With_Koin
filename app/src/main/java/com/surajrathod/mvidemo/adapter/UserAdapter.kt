package com.surajrathod.mvidemo.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.surajrathod.mvidemo.databinding.UserItemLayoutBinding
import com.surajrathod.mvidemo.model.Users
import com.surajrathod.mvidemo.model.UsersItem

class UserAdapter(private val list: Users,val onClick : (UsersItem) -> Unit = {}) : RecyclerView.Adapter<UserAdapter.UsersViewHolder>() {

    class UsersViewHolder(val binding: UserItemLayoutBinding) : ViewHolder(binding.root) {
        val txtName = binding.txtUserName
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        return UsersViewHolder(
            UserItemLayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        val i = list[position]
        holder.txtName.text = i.name
        holder.txtName.setOnClickListener {
            onClick.invoke(i)
        }
    }
}