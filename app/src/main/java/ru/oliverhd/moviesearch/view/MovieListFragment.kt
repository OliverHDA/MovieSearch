package ru.oliverhd.moviesearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import ru.oliverhd.moviesearch.AppState
import ru.oliverhd.moviesearch.R
import ru.oliverhd.moviesearch.databinding.FragmentMovieListBinding
import ru.oliverhd.moviesearch.model.MoviePreview
import ru.oliverhd.moviesearch.viewmodel.MovieListViewModel

class MovieListFragment : Fragment() {

    private var _binding: FragmentMovieListBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MovieListViewModel by viewModels()
    private lateinit var recyclerView: RecyclerView
    private lateinit var movieAdapter: MovieListRVAdapter
    private var data: List<MoviePreview> = listOf()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner) { renderData(it) }
        viewModel.getPopularMovieList()
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessList -> {
                binding.movieListFragmentLoadingLayout.visibility = View.GONE
                data = appState.movieData
                initRecycler()
            }
            is AppState.Loading -> {
                binding.movieListFragmentLoadingLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.movieListFragmentLoadingLayout.visibility = View.GONE
                Snackbar
                    .make(
                        binding.movieListFragmentView,
                        SERVER_ERROR,
                        Snackbar.LENGTH_INDEFINITE
                    )
                    .setAction(RELOAD) {
                        viewModel.getPopularMovieList()
                    }
                    .show()
            }
        }
    }

    private fun initRecycler() {
        movieAdapter = MovieListRVAdapter(data, object : MovieListFragment.OnItemViewClickListener {
            override fun onItemViewClick(moviePreview: MoviePreview) {
                activity?.supportFragmentManager?.beginTransaction()
                    ?.replace(R.id.container, MovieDetailFragment.newInstance(moviePreview.id))
                    ?.addToBackStack("")?.commitAllowingStateLoss()
            }
        })

        recyclerView = binding.movieListRecyclerView

        recyclerView.apply {
            layoutManager = GridLayoutManager(activity, 2)
            adapter = movieAdapter
        }
    }

    interface OnItemViewClickListener {
        fun onItemViewClick(moviePreview: MoviePreview)
    }

    companion object {
        fun newInstance() = MovieListFragment()
        private const val SERVER_ERROR = "Ошибка сервера"
        private const val RELOAD = "Повторная загрузка"
    }
}