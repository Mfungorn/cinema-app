package com.example.cinema.features.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cinema.R
import com.example.cinema.core.models.Film
import com.example.cinema.features.BaseFragment

class DetailsFragment(
    private val film: Film
) : BaseFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(getLayoutId(), container, false)

        return view
    }

    override fun getLayoutId() = R.layout.fragment_details

    companion object {
        fun newInstance(film: Film) = DetailsFragment(film)
    }
}