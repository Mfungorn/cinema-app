package com.example.cinema.features

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.cinema.App
import com.example.cinema.R
import com.example.cinema.core.models.Film
import com.example.cinema.features.details.DetailsFragment
import com.example.cinema.features.feed.FeedFragment


class MainActivity : BaseActivity() {

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(getContainerId(), FeedFragment.newInstance(), "FEED")
            .commit()
    }

    fun showFilmDetails(film: Film) {
        supportFragmentManager.beginTransaction()
            .add(getContainerId(), DetailsFragment.newInstance(film), "FILM")
            .addToBackStack("FILM")
            .commit()
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun getContainerId() = R.id.main_container

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
    }
}
