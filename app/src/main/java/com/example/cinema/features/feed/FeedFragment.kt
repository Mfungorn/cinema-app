package com.example.cinema.features.feed

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.arellomobile.mvp.presenter.InjectPresenter
import com.example.cinema.R
import com.example.cinema.core.adapters.FilmAdapter
import com.example.cinema.core.models.Film
import com.example.cinema.features.BaseFragment
import com.example.cinema.features.MainActivity
import com.google.android.flexbox.*
import kotlinx.android.synthetic.main.fragment_feed.view.*

class FeedFragment : BaseFragment(), FeedView {

    @InjectPresenter
    lateinit var presenter: FeedPresenter

    private lateinit var feedRecyclerView: RecyclerView
    private lateinit var adapter: FilmAdapter
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var progressBar: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(getLayoutId(), container, false)

        progressBar = view.progressBar

        refreshLayout = view.swipeRefreshLayout
        refreshLayout.setOnRefreshListener {
            refreshLayout.isRefreshing = true
            refreshLayout.postDelayed({
                refreshLayout.isRefreshing = false
                presenter.refreshFilms()
            }, 2000)
        }

        val flexboxLayoutManager = FlexboxLayoutManager(context).apply {
            flexWrap = FlexWrap.WRAP
            flexDirection = FlexDirection.ROW
            justifyContent = JustifyContent.SPACE_BETWEEN
            alignItems = AlignItems.STRETCH
        }

        adapter = FilmAdapter(view.context, object : FilmAdapter.OnItemClickListener {
            override fun onGenreCheckChange(genre: String) {
                presenter.sortFilmsByGenre(genre)
            }

            override fun onFilmClick(film: Film) {
                (activity as MainActivity).showFilmDetails(film)
            }
        })

        feedRecyclerView = view.feedRecyclerView
        feedRecyclerView.apply {
            layoutManager = flexboxLayoutManager
            adapter = this@FeedFragment.adapter
        }

        return view
    }

    override fun onFilmsLoading() {
        progressBar.visibility = View.VISIBLE
        feedRecyclerView.visibility = View.GONE
    }

    override fun onFilmsLoaded(films: List<Film>?) {
        films?.let {
            adapter.setFilms(it)
            adapter.setGenres(presenter.genres.toList())
            progressBar.visibility = View.GONE
            feedRecyclerView.visibility = View.VISIBLE
        }
    }

    override fun getLayoutId() = R.layout.fragment_feed

    override fun showMessage(msg: String) {
        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
    }

    companion object {
        fun newInstance() = FeedFragment()
    }
}