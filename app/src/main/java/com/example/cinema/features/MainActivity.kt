package com.example.cinema.features

import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import com.example.cinema.App
import com.example.cinema.R
import com.example.cinema.core.models.Film
import com.example.cinema.features.details.DetailsFragment
import com.example.cinema.features.feed.FeedFragment


class MainActivity : BaseActivity(), NavigationListener {

    init {
        App.INSTANCE.getAppComponent().inject(this)
    }

    lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)
        toolbar.apply {
            setSupportActionBar(this)
            title = getString(R.string.app_name)
        }

        supportFragmentManager.beginTransaction()
            .replace(getContainerId(), FeedFragment.newInstance(), "FEED")
            .commit()
    }

    override fun onFilmDetailsNavigate(film: Film) {
        supportFragmentManager.beginTransaction()
            .add(getContainerId(), DetailsFragment.newInstance(film), "FILM")
            .addToBackStack("FILM")
            .commit()
        toolbar.title = film.localizedName
        toggleBackButton(true)
    }

    override fun getLayoutId() = R.layout.activity_main

    override fun getContainerId() = R.id.main_container

    private fun toggleBackButton(isEnabled: Boolean) {
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(isEnabled)
            setDisplayShowHomeEnabled(isEnabled)
        }
    }

    override fun onBackPressed() {
        supportFragmentManager.popBackStack()
        toggleBackButton(false)
        toolbar.title = getString(R.string.app_name)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}
