package com.example.suitmediatest

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.suitmediatest.network.User

class UserAdapter(
    private val userList: List<User>,
    private val onItemClick: (User) -> Unit
) : RecyclerView.Adapter<UserAdapter.UserViewHolder>() {

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val avatar: ImageView = itemView.findViewById(R.id.avatarImageView)
        val fullName: TextView = itemView.findViewById(R.id.fullNameTextView)
        val email: TextView = itemView.findViewById(R.id.emailTextView)

        fun bind(user: User) {
            fullName.text = "${user.first_name} ${user.last_name}"
            email.text = user.email
            Glide.with(itemView.context).load(user.avatar).into(avatar)

            itemView.setOnClickListener {
                onItemClick(user)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
