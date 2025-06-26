package com.whiskeybook

data class Whiskey( val whiskeyName: String,
                    val age: Int,
                    val alcoholPercentage: Int,
                    val notes: String,
                    val imageUri: String? = null // Optional image path or URI
)
{
}