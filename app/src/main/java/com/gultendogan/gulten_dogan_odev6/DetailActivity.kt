package com.gultendogan.gulten_dogan_odev6

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.gultendogan.gulten_dogan_odev6.model.Product
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        Glide.with(this)
            .load((intent.getSerializableExtra("productImages")).toString())
            .into(detailView)

        titleText.text = "Title: " + (intent.getSerializableExtra("productTitle")).toString()
        categoryText.text = "Category: " + (intent.getSerializableExtra("productCategory")).toString()
        brandText.text = "Brand: " + (intent.getSerializableExtra("productBrand")).toString()
        stockText.text = "Stock: " + (intent.getSerializableExtra("productStock")).toString()
        ratingText.text = "Rating: " + (intent.getSerializableExtra("productRating")).toString()
        descriptionText.text = "Description: " + (intent.getSerializableExtra("productDescription")).toString()
    }
}