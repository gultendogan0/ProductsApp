package com.gultendogan.gulten_dogan_odev6

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.gultendogan.gulten_dogan_odev6.adapter.RecyclerViewAdapter
import com.gultendogan.gulten_dogan_odev6.model.ApiResponse
import com.gultendogan.gulten_dogan_odev6.model.Product
import com.gultendogan.gulten_dogan_odev6.model.SearchResult
import com.gultendogan.gulten_dogan_odev6.service.Service
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ProductsActivity : AppCompatActivity() {
    private lateinit var productRv: RecyclerView
    private lateinit var adapter: RecyclerViewAdapter
    private lateinit var productService: Service
    private lateinit var araText: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_products)

        // RecyclerView'ı ayarla
        productRv = findViewById(R.id.product_rv)
        productRv.layoutManager = LinearLayoutManager(this)

        // EditText'i ayarla
        araText = findViewById(R.id.araText)
        araText.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                // Arama yapılacak kelimeyi al
                val query = s.toString()

                // Arama yap
                searchProducts(query)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })

        // Gson nesnesini oluştur
        val gson: Gson = GsonBuilder().create()

        // Retrofit nesnesini oluştur ve ProductService'e bağlan
        val retrofit = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        productService = retrofit.create(Service::class.java)

        // Ürünleri çek ve RecyclerView'a ekle
        productService.getProducts(10).enqueue(object : Callback<ApiResponse> {
            override fun onResponse(call: Call<ApiResponse>, response: Response<ApiResponse>) {
                if (response.isSuccessful) {
                    response.body()?.let { apiResponse ->
                        val productList: List<Product> = ArrayList(apiResponse.products)
                        adapter = RecyclerViewAdapter(productList, this@ProductsActivity)
                        productRv.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@ProductsActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<ApiResponse>, t: Throwable) {
                Toast.makeText(this@ProductsActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun searchProducts(query: String) {
        productService.searchProducts(query).enqueue(object : Callback<SearchResult> {
            override fun onResponse(call: Call<SearchResult>, response: Response<SearchResult>) {
                if (response.isSuccessful) {
                    response.body()?.let { searchResult ->
                        val productList: List<Product> = ArrayList(searchResult.products)
                        adapter = RecyclerViewAdapter(productList, this@ProductsActivity)
                        productRv.adapter = adapter
                    }
                } else {
                    Toast.makeText(this@ProductsActivity, "Error: ${response.code()}", Toast.LENGTH_LONG).show()
                }
            }

            override fun onFailure(call: Call<SearchResult>, t: Throwable) {
                Toast.makeText(this@ProductsActivity, "Error: ${t.message}", Toast.LENGTH_LONG).show()
            }
        })
    }

    fun onItemClick(product: Product) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra("productTitle", product.title)
        intent.putExtra("productDescription", product.description)
        intent.putExtra("productRating", product.rating)
        intent.putExtra("productStock", product.stock)
        intent.putExtra("productBrand", product.brand)
        intent.putExtra("productCategory", product.category)
        intent.putExtra("productImages", product.images[0])
        startActivity(intent)
    }
}