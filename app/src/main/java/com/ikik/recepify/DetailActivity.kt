package com.ikik.recepify

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class DetailActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_detail)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.detail)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val recipeData = intent.getParcelableExtra<Recipe>("DATA")
        val position = intent.getIntExtra("POSITION", 0)

        val ingredientsArray = resources.getStringArray(R.array.data_ingredient)
        val stepsArray = resources.getStringArray(R.array.data_step)

        if (position in ingredientsArray.indices && position in stepsArray.indices) {
            val ingredients = ingredientsArray[position]
            val steps = stepsArray[position]

            findViewById<TextView>(R.id.tv_item_ingredient).text = ingredients
            findViewById<TextView>(R.id.tv_item_step).text = steps
        }

        recipeData?.let {
            findViewById<ImageView>(R.id.img_item_photo).setImageResource(it.photo)
            findViewById<TextView>(R.id.tv_item_name).text = it.name
            findViewById<TextView>(R.id.tv_item_description).text = it.description
        }

        val shareButton: Button = findViewById(R.id.btn_share)

        shareButton.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, "Berbagi dari aplikasi saya.")
                type = "text/plain"
            }

            val chooser = Intent.createChooser(shareIntent, "Bagikan dengan...")
            startActivity(chooser)
        }
    }

}