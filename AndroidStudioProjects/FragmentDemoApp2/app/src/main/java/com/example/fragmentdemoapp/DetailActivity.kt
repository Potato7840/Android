package com.example.fragmentdemoapp

import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class DetailActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val tvDetail = findViewById<TextView>(R.id.tvDetail)

        val userData = intent.getParcelableExtra<UserData>("user_data")

        val detailText = userData?.let {
            """
            |用户详细信息:
            |姓名: ${it.name}
            |年龄: ${it.age}
            |邮箱: ${it.email}
            |学生: ${if (it.isStudent) "是" else "否"}
            |来自: MainActivity
            """.trimMargin()
        } ?: "未接收到用户数据"

        tvDetail.text = detailText
    }
}