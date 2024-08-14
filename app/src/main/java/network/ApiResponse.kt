package com.example.suitmediatest.network

data class ApiResponse<T>(
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int,
    val data: List<T>
)
