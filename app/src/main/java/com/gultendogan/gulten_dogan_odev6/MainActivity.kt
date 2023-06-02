package com.gultendogan.gulten_dogan_odev6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import com.gultendogan.gulten_dogan_odev6.service.Service
import kotlinx.android.synthetic.main.activity_main.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/*
username: 'kminchelle',
password: '0lelplR',
 */

class MainActivity : AppCompatActivity() {
    private lateinit var usernameText: EditText
    private lateinit var passwordText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        usernameText = findViewById(R.id.usernameText)
        passwordText = findViewById(R.id.passwordText)

        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val loginService = retrofit.create(Service::class.java)

        enterButton.setOnClickListener {
            val username = usernameText.text.toString()
            val password = passwordText.text.toString()

            loginService.login(username, password).enqueue(object : Callback<ResponseBody> {
                override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {
                        startActivity(Intent(this@MainActivity, ProductsActivity::class.java))
                    } else {
                        Toast.makeText(this@MainActivity, "Giriş Başarısız", Toast.LENGTH_LONG).show()
                    }
                }

                override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                    // Giriş işlemi başarısız oldu, hata mesajını kullanıcıya göster
                    Toast.makeText(this@MainActivity, "Giriş Başarısız", Toast.LENGTH_LONG).show()
                }
            })
        }
    }
}