package ru.oliverhd.moviesearch.view

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ru.oliverhd.moviesearch.databinding.MovieRecyclerItemBinding
import ru.oliverhd.moviesearch.model.MoviePreview

class MovieListRVAdapter(
    private var movieData: List<MoviePreview>,
    private var onItemViewClickListener: MovieListFragment.OnItemViewClickListener?
) : RecyclerView.Adapter<MovieListRVAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val binding = MovieRecyclerItemBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return MovieViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.bind(movieData[position])
    }

    override fun getItemCount() = movieData.size

    inner class MovieViewHolder(private val binding: MovieRecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(moviePreview: MoviePreview) {
            with(binding) {
                imageViewMovie.setOnClickListener {
                    onItemViewClickListener?.onItemViewClick(moviePreview)
                }
                Picasso.get()
                    .load("https://image.tmdb.org/t/p/w342${moviePreview.posterPath}")
                    .into(imageViewMovie)
                textViewMovieName.text = moviePreview.title
                textViewReleaseYear.text = moviePreview.releaseDate?.substring(0, 4)
                textViewRating.text = moviePreview.rating.toString()
            }
        }
    }
}