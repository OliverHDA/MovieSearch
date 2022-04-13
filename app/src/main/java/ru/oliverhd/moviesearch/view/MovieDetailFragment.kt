package ru.oliverhd.moviesearch.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import com.google.android.material.snackbar.Snackbar
import com.squareup.picasso.Picasso
import ru.oliverhd.moviesearch.AppState
import ru.oliverhd.moviesearch.databinding.FragmentMovieDetailBinding
import ru.oliverhd.moviesearch.model.MovieDetails
import ru.oliverhd.moviesearch.viewmodel.MovieDetailViewModel

class MovieDetailFragment : Fragment() {

    private var _binding: FragmentMovieDetailBinding? = null
    private val binding get() = _binding!!
    private val movieID: Int by lazy {
        arguments?.getInt(BUNDLE_EXTRA) ?: 0
    }
    private val viewModel: MovieDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMovieDetailBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getLiveData().observe(viewLifecycleOwner, Observer {
            renderData(it)
        })
        viewModel.getMovieInfoById(movieID)
    }

    private fun renderData(appState: AppState) {
        when (appState) {
            is AppState.SuccessDetail -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingMovieDetailLayout.visibility = View.GONE
                setMovieDetail(appState.movieData)
            }
            is AppState.Loading -> {
                binding.mainView.visibility = View.GONE
                binding.loadingMovieDetailLayout.visibility = View.VISIBLE
            }
            is AppState.Error -> {
                binding.mainView.visibility = View.VISIBLE
                binding.loadingMovieDetailLayout.visibility = View.GONE
                Snackbar.make(binding.mainView, SERVER_ERROR, Snackbar.LENGTH_INDEFINITE)
                    .setAction(RELOAD) {
                        viewModel.getMovieInfoById(movieID)
                    }.show()
            }
        }
    }

    private fun setMovieDetail(movieDetails: MovieDetails) {
        with(binding) {

            textViewMovieName.text = movieDetails.title
            var genres: String = "${textViewGenre.text.toString()}: "
            for (genre in movieDetails.genres) {
                genres = if (genre == movieDetails.genres.last()) {
                    "$genres${genre.name}."
                } else {
                    "$genres${genre.name}, "
                }

            }
            textViewGenre.text = genres
            textViewRating.text = movieDetails.rating.toString()

            val date: String = "${textViewReleaseDate.text.toString()}: ${movieDetails.releaseDate}"
            textViewReleaseDate.text = date

            textViewOverview.text = movieDetails.overview
            Picasso
                .get()
                .load("https://image.tmdb.org/t/p/w780${movieDetails.posterPath}")
                .into(imageView)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    companion object {

        private const val SERVER_ERROR = "Ошибка сервера"
        private const val RELOAD = "Повторная загрузка"
        private const val BUNDLE_EXTRA = "movieInfo"

        fun newInstance(movieID: Int): MovieDetailFragment {
            return MovieDetailFragment().apply {
                arguments = bundleOf(Pair(BUNDLE_EXTRA, movieID))
            }
        }
    }
}