package com.dev.today19jan

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.dev.today19jan.room.RoomEntity
import com.google.gson.Gson

class MainActivity : AppCompatActivity(), UserAdapter.OnClickListener {

    lateinit var adapter: UserAdapter
    private lateinit var viewModel: UserViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(UserViewModel::class.java)
        adapter = UserAdapter(ArrayList(), this)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recyclerView.adapter = adapter
        viewModel.getAllData()

        viewModel._data?.observe(this, {
            Log.e("Dev ABC", Gson().toJson(it))
            for (i in 0 until it.size) {
                viewModel.insertUserData(
                    RoomEntity(
                        it[i].userId,
                        it[i].id,
                        it[i].title,
                        it[i].completed
                    )
                )
            }
            adapter.usersData = viewModel.getAllUserData() as ArrayList<RoomEntity>
            adapter.notifyDataSetChanged()
        })

        viewModel._loading.observe(this, {

        })
    }

    override fun onClick(id: Int, completed: Boolean) {
        viewModel.updateUserData(completed, id)
    }
}