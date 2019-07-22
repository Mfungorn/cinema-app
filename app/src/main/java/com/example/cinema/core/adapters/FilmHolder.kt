package com.example.cinema.core.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.core.models.Film
import com.google.android.flexbox.FlexboxLayoutManager
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_list_item.view.*

class FilmHolder(view: View, private val listener: (Film) -> Unit) :
    RecyclerView.ViewHolder(view) {

    private var icon: ImageView = view.filmImageView
    private var name: TextView = view.nameTextView

    internal fun bind(film: Film) {
        val params = icon.layoutParams
        if (params is FlexboxLayoutManager.LayoutParams) {
            params.flexGrow = 1f
        }
        if ( !film.imageUrl.isNullOrBlank() ) {
            Picasso.get()
                .load(film.imageUrl)
                .fit()
                .centerCrop()
                .into(icon)
        }
        name.text = film.localizedName

        itemView.setOnClickListener { listener.invoke(film) }
    }

}