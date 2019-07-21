package com.example.cinema.core.adapters

import android.content.Context
import android.view.ContextThemeWrapper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.NonNull
import androidx.recyclerview.widget.RecyclerView
import com.example.cinema.R
import com.example.cinema.core.models.Film
import com.google.android.flexbox.FlexboxLayoutManager
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.film_list_genres.view.*
import kotlinx.android.synthetic.main.film_list_item.view.*


class FilmAdapter(context: Context, private val listener: OnItemClickListener) :
    RecyclerView.Adapter<FilmAdapter.FilmHolder>() {

    private val films = ArrayList<Film>()
    private val genres = ArrayList<String>()
    var checkedGenre: String = ""
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    override fun getItemCount() = (films.size + 1)

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) 1 else 2
    }

    @NonNull
    override fun onCreateViewHolder(@NonNull parent: ViewGroup, viewType: Int): FilmHolder {
        val itemView = if (viewType == 1) {
            inflater.inflate(R.layout.film_list_genres, parent, false)
        } else {
            inflater.inflate(R.layout.film_list_item, parent, false)
        }
        return FilmHolder(viewType, itemView, listener)
    }

    override fun onBindViewHolder(@NonNull holder: FilmHolder, position: Int) {
        if (position == 0) {
            holder.rebindGenres()
        } else {
            holder.bind(films[position - 1])
        }
    }

    fun setFilms(list: List<Film>) {
        films.clear()
        films.addAll(list)
        notifyDataSetChanged()
    }

    fun setGenres(list: List<String>) {
        genres.clear()
        genres.addAll(list)
        notifyDataSetChanged()
    }

    inner class FilmHolder(viewType: Int, view: View, private val OnItemClickListener: OnItemClickListener) :
        RecyclerView.ViewHolder(view) {

        private var icon: ImageView? = if (viewType == 2) view.filmImageView else null
        private var name: TextView? = if (viewType == 2) view.nameTextView else null
        private var chipGroup: ChipGroup? = if (viewType == 1) view.genresChipGroup else null

        internal fun bind(film: Film) {
            val params = icon?.layoutParams
            if (params is FlexboxLayoutManager.LayoutParams) {
                params.flexGrow = 1f
            }
            if (!film.imageUrl.isNullOrBlank() and (icon != null)) {
                Picasso.get()
                    .load(film.imageUrl)
                    .fit()
                    .centerCrop()
                    .into(icon)
            }
            name?.text = film.localizedName

            itemView.setOnClickListener { OnItemClickListener.onFilmClick(film) }
        }

        internal fun rebindGenres() = chipGroup?.let {
            if (it.childCount <= 0) {
                bindGenres()
            }
        }

        private fun bindGenres() = chipGroup?.let {
            genres.forEach { genre ->
                val chip = Chip(
                    ContextThemeWrapper(
                        it.context,
                        R.style.Widget_MaterialComponents_Chip_Filter
                    )
                ).apply {
                    isClickable = true
                    isCheckable = true
                    text = genre
                }
                it.addView(chip)
            }

            it.setOnCheckedChangeListener { group, checkedId ->
                checkedGenre =
                    if (checkedId > 0) group.findViewById<Chip>(checkedId).text.toString() else ""
                OnItemClickListener.onGenreCheckChange(checkedGenre)
            }
        }
    }

    interface OnItemClickListener {
        fun onFilmClick(film: Film)
        fun onGenreCheckChange(genre: String)
    }

}