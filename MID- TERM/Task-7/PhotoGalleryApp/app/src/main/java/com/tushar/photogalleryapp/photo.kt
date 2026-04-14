package com.tushar.photogalleryapp

data class Photo(
    val id: Int,
    val imageRes: Int,
    val title: String,
    val category: String,
    var isSelected: Boolean = false
)
