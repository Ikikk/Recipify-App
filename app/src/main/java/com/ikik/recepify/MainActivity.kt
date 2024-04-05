package com.ikik.recepify

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var rvRecipe: RecyclerView
    private val list = ArrayList<Recipe>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        rvRecipe = findViewById(R.id.rv_recipe)
        rvRecipe.setHasFixedSize(true)

        list.addAll(getListRecipe())
        showRecyclerList()

    }



    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_list -> {
                rvRecipe.layoutManager = LinearLayoutManager(this)
            }
            R.id.action_grid -> {
                rvRecipe.layoutManager = GridLayoutManager(this, 2)
            }
            R.id.action_profile -> {
                val profile = Intent(this@MainActivity, ProfileActivity::class.java)
                startActivity(profile)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListRecipe(): ArrayList<Recipe> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_descriptions)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listRecipe = ArrayList<Recipe>()
        for (i in dataName.indices) {
            val recipe = Recipe(
                dataName[i],
                dataDescription[i],
                dataPhoto.getResourceId(i, -1)
            )
            listRecipe.add(recipe)
        }
        dataPhoto.recycle()

        return listRecipe
    }

    private fun showRecyclerList() {
        rvRecipe.layoutManager = LinearLayoutManager(this)
        val listRecipeAdapter = ListRecipeAdapter(list)
        rvRecipe.adapter = listRecipeAdapter

        listRecipeAdapter.setOnClickCallback(object : ListRecipeAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Recipe, position: Int) {
                val intentToDetail = Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra("DATA", data)
                    putExtra("POSITION", position)
                }
                showSelectedRecipe(data)
                startActivity(intentToDetail)
            }
        })

    }

    private fun showSelectedRecipe(recipe: Recipe) {
        Toast.makeText(this, "Kamu memilih " + recipe.name, Toast.LENGTH_SHORT).show()
    }

}