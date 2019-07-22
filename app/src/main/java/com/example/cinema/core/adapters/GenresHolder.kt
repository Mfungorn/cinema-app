package com.example.cinema.core.adapters

import android.view.ContextThemeWrapper
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import kotlinx.android.synthetic.main.film_list_genres.view.*

class GenresHolder(view: View, private val listener: (String) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private val genres = ArrayList<String>()
    private var checkedGenre: String = ""

    private var chipGroup: ChipGroup = view.genresChipGroup

    internal fun rebindGenres(newGenres: ArrayList<String>) {
        if ((chipGroup.childCount <= 0) or !genres.containsAll(newGenres)) {
            genres.clear()
            genres.addAll(newGenres)
            bind()
        }
    }

    private fun bind() {
        genres.forEach { genre ->
            val chip = Chip(
                ContextThemeWrapper(
                    chipGroup.context,
                    R.style.Widget_MaterialComponents_Chip_Filter
                )
            ).apply {
                isClickable = true
                isCheckable = true
                text = genre
            }
            chipGroup.addView(chip)
        }

        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            checkedGenre =
                if (checkedId > 0) group.findViewById<Chip>(checkedId).text.toString() else ""
            listener.invoke(checkedGenre)
        }
    }
}

