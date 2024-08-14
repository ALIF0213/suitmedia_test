package com.example.suitmediatest

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.example.suitmediatest.network.ApiService
import com.example.suitmediatest.network.RetrofitInstance
import com.example.suitmediatest.network.ApiResponse
import com.example.suitmediatest.network.User
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ThirdActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout
    private lateinit var userAdapter: UserAdapter
    private lateinit var btnBack: Button
    private var userList: MutableList<User> = mutableListOf()
    private var currentPage = 1
    private val perPage = 10

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.third_activity)

        recyclerView = findViewById(R.id.recyclerView)
        swipeRefreshLayout = findViewById(R.id.swipeRefreshLayout)
        btnBack = findViewById(R.id.btnBack)

        userAdapter = UserAdapter(userList) { user ->
            val returnIntent = Intent()
            returnIntent.putExtra("selectedUserName", "${user.first_name} ${user.last_name}")
            setResult(RESULT_OK, returnIntent)
            finish()
        }

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = userAdapter

        swipeRefreshLayout.setOnRefreshListener {
            userList.clear()
            currentPage = 1
            fetchUsers()
        }

        fetchUsers()

        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                if (layoutManager.findLastCompletelyVisibleItemPosition() == userList.size - 1) {
                    currentPage++
                    fetchUsers()
                }
            }
        })
        btnBack.setOnClickListener {
            // Kembali ke MainActivity
            val intent = Intent(this, SecondActivity::class.java)
            startActivity(intent)
            finish() // Opsional, untuk menghapus SecondActivity dari stack
        }
    }

    private fun fetchUsers() {
        swipeRefreshLayout.isRefreshing = true

        val apiService = RetrofitInstance.retrofit.create(ApiService::class.java)
        apiService.getUsers(currentPage, perPage).enqueue(object : Callback<ApiResponse<User>> {
            override fun onFailure(call: Call<ApiResponse<User>>, t: Throwable) {
                runOnUiThread {
                    swipeRefreshLayout.isRefreshing = false
                    Toast.makeText(this@ThirdActivity, "Failed to fetch data", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onResponse(call: Call<ApiResponse<User>>, response: Response<ApiResponse<User>>) {
                response.body()?.let { apiResponse ->
                    userList.addAll(apiResponse.data)
                    userAdapter.notifyDataSetChanged()
                    swipeRefreshLayout.isRefreshing = false
                } ?: run {
                    runOnUiThread {
                        swipeRefreshLayout.isRefreshing = false
                        Toast.makeText(this@ThirdActivity, "No data available", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })
    }
}
