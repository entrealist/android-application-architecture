package com.rosberry.pagination.ui

import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.rosberry.pagination.R
import com.rosberry.pagination.data.MoviesApi
import com.rosberry.pagination.data.MoviesRepository
import com.rosberry.pagination.presentation.MoviesPresenter
import com.rosberry.pagination.presentation.MoviesView
import com.rosberry.pagination.system.MovieItems
import com.rosberry.pagination.system.gone
import com.rosberry.pagination.system.show
import kotlinx.android.synthetic.main.activity_movies.*
import moxy.MvpAppCompatActivity
import moxy.presenter.InjectPresenter
import moxy.presenter.ProvidePresenter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class MoviesActivity : MvpAppCompatActivity(), MoviesView {

    @InjectPresenter
    lateinit var presenter: MoviesPresenter

    private val viewHandler = Handler()

    private val moviesLayoutManager by lazy {
        androidx.recyclerview.widget.LinearLayoutManager(this,
                androidx.recyclerview.widget.LinearLayoutManager.VERTICAL, false)
    }
    private val moviesAdapter by lazy { MoviesAdapter(emptyList()) }
    private val moviesEndlessScrollListener by lazy { MoviesEndlessScrollListener() }

    @ProvidePresenter
    fun providePresenter(): MoviesPresenter {
        val api = Retrofit.Builder()
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://afisha-omsk.ru")
            .build()
            .create(MoviesApi::class.java)

        val repository = MoviesRepository(api)
        return MoviesPresenter(repository)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movies)
        moviesListView.adapter = moviesAdapter
        moviesListView.layoutManager = moviesLayoutManager
        moviesListView.addOnScrollListener(moviesEndlessScrollListener)

        refreshMoviesView.setOnRefreshListener { presenter.onRefresh() }
        retryButton.setOnClickListener { presenter.onRefresh() }
    }

    override fun showEmptyProgress(show: Boolean) {
        progressView.visible(show)

        refreshMoviesView.visible(!show)
        viewHandler.post { refreshMoviesView.isRefreshing = false }

        moviesEndlessScrollListener.resetState()
    }

    override fun showEmptyError(show: Boolean, errorText: String?) {
        errorTextView.text = errorText
        errorTextView.visible(show)
        retryButton.visible(show)
    }

    override fun showEmptyView(show: Boolean) {
        emptyImageView.visible(show)
    }

    override fun showError(errorDescription: String?) {
        Toast.makeText(baseContext, errorDescription, Toast.LENGTH_LONG)
            .show()
    }

    override fun showData(show: Boolean, data: MovieItems) {
        if (show) moviesAdapter.showData(data)
        moviesListView.visible(show)
    }

    override fun showRefreshProgress(show: Boolean) {
        viewHandler.post { refreshMoviesView.isRefreshing = show }
        moviesEndlessScrollListener.resetState()
    }

    override fun showPageProgress(show: Boolean) {
        viewHandler.post { moviesAdapter.showProgress(show) }
    }

    private fun View.visible(show: Boolean) {
        if (show) this.show()
        else this.gone()
    }

    private inner class MoviesEndlessScrollListener :
            EndlessRecyclerViewScrollListener(moviesLayoutManager) {

        override fun onLoadMore(page: Int, totalItemsCount: Int, view: androidx.recyclerview.widget.RecyclerView) {
            presenter.loadNextPage()
        }

    }
}
