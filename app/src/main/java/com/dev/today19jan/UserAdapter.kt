package com.dev.today19jan

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.dev.today19jan.room.RoomEntity

class UserAdapter(var usersData: ArrayList<RoomEntity>, var onClickListener: OnClickListener): RecyclerView.Adapter<UserAdapter.UserViewHolder>() {


    class UserViewHolder(view:View): RecyclerView.ViewHolder(view){
        var userId = view.findViewById<TextView>(R.id.userid)
        var id = view.findViewById<TextView>(R.id.id)
        var title = view.findViewById<TextView>(R.id.title)
        var completedBtn = view.findViewById<CheckBox>(R.id.completed)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.user_adapter, parent, false)
        return UserViewHolder(view)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        holder.userId.text = usersData[position].userId.toString()
        holder.id.text = usersData[position].id.toString()
        holder.title.text = usersData[position].title
        holder.completedBtn.isChecked = usersData[position].completed
        holder.completedBtn.setOnCheckedChangeListener { buttonView, isChecked ->
            holder.completedBtn.isChecked = isChecked
            onClickListener.onClick(usersData[position].id, isChecked)
        }
    }

    override fun getItemCount(): Int {
        return usersData.size
    }

    interface OnClickListener{
        fun onClick(id: Int, completed: Boolean)
    }

}