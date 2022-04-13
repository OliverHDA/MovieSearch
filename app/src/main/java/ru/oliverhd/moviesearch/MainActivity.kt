package ru.oliverhd.moviesearch

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import ru.oliverhd.moviesearch.view.MovieListFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, MovieListFragment.newInstance())
                .commitNow()
        }
    }
}