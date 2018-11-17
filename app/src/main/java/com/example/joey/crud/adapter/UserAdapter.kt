package com.example.joey.crud.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.joey.crud.R
import com.example.joey.crud.data.User

class UserAdapter internal constructor(context: Context): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var users = emptyList<User>()   // Cached copy of users
    private lateinit var clickListener: OnItemClickListener

    inner class UserViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val userId: TextView = itemView.findViewById(R.id.id)
        val userName: TextView = itemView.findViewById(R.id.name)
        val userEmail: TextView = itemView.findViewById(R.id.email)
        val userMajor: TextView = itemView.findViewById(R.id.major)

        init {
            itemView.setOnClickListener {
                val position: Int = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    clickListener.onItemClick(users[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val itemView = inflater.inflate(R.layout.info, parent, false)
        return UserViewHolder(itemView)
    }

    override fun getItemCount() = users.size

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val current = users[position]
        holder.userId.text = current.id
        holder.userName.text = current.name
        holder.userEmail.text = current.email
        holder.userMajor.text = current.major
    }

    internal fun setUsers(users: List<User>) {
        this.users = users
        notifyDataSetChanged()
    }

    fun swipeToDelete(position: Int): User {
        return users[position]
    }

    interface OnItemClickListener {
        fun onItemClick(user: User) {

        }
    }

    fun setOnItemClickListener(clickListener: OnItemClickListener) {
        this.clickListener = clickListener
    }
}
