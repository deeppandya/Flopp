package mainpackage.flopp.view;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.google.firebase.analytics.FirebaseAnalytics;

import java.util.Objects;

import mainpackage.flopp.BuildConfig;
import mainpackage.flopp.R;
import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.utils.Constants;
import mainpackage.flopp.utils.GlideApp;

public class MovieDetailActivity extends AppCompatActivity {

    private MovieModel movieModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        FirebaseAnalytics mFirebaseAnalytics = FirebaseAnalytics.getInstance(this);

        mFirebaseAnalytics.setCurrentScreen(this, MainActivity.class.getName(), null /* class override */);

        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        final Intent intent = getIntent();
        if (intent.hasExtra(Constants.MOVIE)) {
            movieModel = intent.getParcelableExtra(Constants.MOVIE);
            if (movieModel == null) {
                Toast.makeText(this, "Can't open movie", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        setViews();

        Bundle params = new Bundle();
        params.putString("movie_overview", movieModel.getOverview());
        params.putString("released_date", movieModel.getReleaseDate());
        params.putString("title", movieModel.getTitle());
        mFirebaseAnalytics.logEvent("detailed_movie", params);
    }

    private void setViews() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(movieModel.getTitle());

        ImageView imgBackgroungPhoto = findViewById(R.id.img_background_photo);
        TextView tvMovieOverview = findViewById(R.id.tv_movie_overview);
        TextView tvMovieName = findViewById(R.id.tv_movie_name);

        GlideApp.with(this)
                .load(BuildConfig.IMAGE_END_POINT_500 + movieModel.getBackdropPath())
                .placeholder(R.drawable.ic_placeholder)
                .error(R.drawable.ic_error)
                .fallback(R.drawable.ic_error)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .into(imgBackgroungPhoto);
        tvMovieOverview.setText(movieModel.getOverview());
    }
}
