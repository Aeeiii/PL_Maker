package com.practicum.pl_maker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class SearchActivity : AppCompatActivity() {

    private var countValue: String = AMOUNT_DEF


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val inputEditText = findViewById<EditText>(R.id.search)
        val cleanButton = findViewById<ImageView>(R.id.clean_icon)

        val jsonString: String = assets.open("Tracks.json").bufferedReader().use { it.readText() }
        val trackList = Gson().fromJson<ArrayList<Track>>(
            jsonString,
            object : TypeToken<ArrayList<Track>>() {}.type
        )

        inputEditText.setText(countValue)


        cleanButton.setOnClickListener {
            inputEditText.setText("")
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(inputEditText.windowToken, 0)
        }


        val simpleTextWatcher = object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                cleanButton.visibility = cleanButtonVisibility(s)
                countValue = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }
        inputEditText.addTextChangedListener(simpleTextWatcher)

        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val trackAdapter = TrackAdapter(trackList)
        recyclerView.adapter = trackAdapter

    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString(PRODUCT_AMOUNT, countValue)
    }


    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
        countValue = savedInstanceState.getString(PRODUCT_AMOUNT, AMOUNT_DEF)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) finish()
        return super.onOptionsItemSelected(item)
    }

    private fun cleanButtonVisibility(s: CharSequence?): Int {
        return if (s.isNullOrEmpty()) {
            View.GONE
        } else {
            View.VISIBLE
        }
    }

    private companion object {
        const val PRODUCT_AMOUNT = "TEXT"
        const val AMOUNT_DEF = ""
    }
}