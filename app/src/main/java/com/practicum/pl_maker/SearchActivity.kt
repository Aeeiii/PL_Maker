package com.practicum.pl_maker

import android.content.Context
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.MenuItem
import android.view.View
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class SearchActivity : AppCompatActivity(), TrackHolder.Listener {


    private val iTunesBaseUrl = "https://itunes.apple.com"

    private val retrofit =
        Retrofit.Builder().baseUrl(iTunesBaseUrl).addConverterFactory(GsonConverterFactory.create())
            .build()

    private val iTunesService = retrofit.create(ITunesSearchApi::class.java)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search)

        setSupportActionBar(findViewById(R.id.my_toolbar))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        val queryInput = findViewById<EditText>(R.id.search)
        val cleanButton = findViewById<ImageView>(R.id.clean_icon)
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        val searchErrorImage = findViewById<ImageView>(R.id.search_image_error)
        val searchErrorText = findViewById<TextView>(R.id.search_text_error)
        val updateButton = findViewById<Button>(R.id.update_button)
        val errorLayout: LinearLayout = findViewById(R.id.error_layout)
        val hintMessage = findViewById<TextView>(R.id.hint_message)
        val cleanHistoryButton = findViewById<Button>(R.id.clean_history_button)

        val sharedPrefs = getSharedPreferences(SEARCH_HISTORY, MODE_PRIVATE)
        var searchHistory = SearchHistory(sharedPrefs)

        val trackAdapter = TrackAdapter(trackList, this)


        fun showSavedTracks() {
            searchHistory = SearchHistory(sharedPrefs)
            val savedTrackAdapter = TrackAdapter(searchHistory.getSavedTracks(), this)
            recyclerView.setItemViewCacheSize(searchHistory.savesTracks.size)
            recyclerView.adapter = savedTrackAdapter
            hintMessage.visibility = View.VISIBLE
            cleanHistoryButton.visibility = View.VISIBLE
            recyclerView.visibility = View.VISIBLE
        }

        fun hideSavedTracks() {
            hintMessage.visibility = View.GONE
            cleanHistoryButton.visibility = View.GONE
            recyclerView.visibility = View.GONE
        }


        val simpleTextWatcher = object : TextWatcher {

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (queryInput.hasFocus() && s?.isEmpty() == true && searchHistory.savesTracks.size > 0) showSavedTracks() else hideSavedTracks()

                cleanButton.visibility = cleanButtonVisibility(s)
                countValue = s.toString()
            }

            override fun afterTextChanged(s: Editable?) {
            }
        }


        fun showConnectionError() {
            requestStatusFlag = "no connection"
            trackList.clear()
            recyclerView.adapter = trackAdapter
            errorLayout.visibility = View.VISIBLE
            searchErrorImage.setImageResource(R.drawable.no_internet_error)
            searchErrorText.setText(R.string.no_connection)
            updateButton.visibility = View.VISIBLE
        }

        fun showNoResults() {
            requestStatusFlag = "no result"
            recyclerView.adapter = trackAdapter
            searchErrorImage.setImageResource(R.drawable.light_mode)
            searchErrorText.setText(R.string.no_find_error)
            errorLayout.visibility = View.VISIBLE
            updateButton.visibility = View.GONE
        }

        fun response() {
            iTunesService.search(countValue).enqueue(object : Callback<TrackResponse> {
                override fun onResponse(
                    call: Call<TrackResponse>, response: Response<TrackResponse>
                ) {
                    trackList.clear()
                    if (response.code() == 200) {
                        if (response.body()?.results?.isNotEmpty() == true) {
                            requestStatusFlag = "done"
                            errorLayout.visibility = View.GONE
                            trackList.addAll(response.body()?.results!!)
                            recyclerView.setItemViewCacheSize(response.body()!!.resultCount)
                            recyclerView.adapter = trackAdapter
                            recyclerView.visibility = View.VISIBLE
                        }
                        if (trackList.isEmpty()) {
                            showNoResults()
                        }
                    } else {
                        showConnectionError()
                    }
                }

                override fun onFailure(call: Call<TrackResponse>, t: Throwable) {
                    showConnectionError()
                }


            })

        }



        queryInput.setOnFocusChangeListener { view, hasFocus ->
            searchHistory = SearchHistory(sharedPrefs)
            if (hasFocus && queryInput.text.isEmpty() && searchHistory.getSavedTracks().size > 0) showSavedTracks() else hideSavedTracks()
        }

        //отрисовываем с учетом сохраненного состояния

        queryInput.setText(countValue)
        cleanButton.visibility = cleanButtonVisibility(countValue)
        when (requestStatusFlag) {
            "no request" -> {

            }

            "done" -> {
                recyclerView.adapter = trackAdapter
                recyclerView.visibility = View.VISIBLE
            }

            "no result" -> showNoResults()
            "no connection" -> showConnectionError()
        }

        //


        updateButton.setOnClickListener {
            response()
        }

        cleanButton.setOnClickListener {
            queryInput.setText("")
            requestStatusFlag = "no request"
            errorLayout.visibility = View.GONE
            trackList.clear()
            hideSavedTracks()
            val inputMethodManager =
                getSystemService(Context.INPUT_METHOD_SERVICE) as? InputMethodManager
            inputMethodManager?.hideSoftInputFromWindow(queryInput.windowToken, 0)
            queryInput.clearFocus()
        }

        cleanHistoryButton.setOnClickListener {
            searchHistory.cleanHistory()
            hideSavedTracks()
        }

        queryInput.addTextChangedListener(simpleTextWatcher)

        recyclerView.layoutManager = LinearLayoutManager(this)

        queryInput.setOnEditorActionListener { _, actionId, _ ->
            if (actionId == EditorInfo.IME_ACTION_DONE) {
                response()
            }
            false
        }

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
        const val REQUEST_STATUS = "no request"
        const val SEARCH_HISTORY = "Search history"
        private var countValue: String = AMOUNT_DEF
        private var requestStatusFlag: String = REQUEST_STATUS
        private val trackList = ArrayList<Track>()
    }

    override fun onClickTrackHolder(track: Track) {
        val sharedPrefs = getSharedPreferences(SEARCH_HISTORY, MODE_PRIVATE)
        val searchHistory = SearchHistory(sharedPrefs)
        searchHistory.saveTrack(track)
    }
}