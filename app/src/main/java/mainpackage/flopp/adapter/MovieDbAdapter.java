package mainpackage.flopp.adapter;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import mainpackage.flopp.BuildConfig;
import mainpackage.flopp.R;
import mainpackage.flopp.model.MovieModel;
import mainpackage.flopp.utils.GlideApp;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */
public class MovieDbAdapter extends RecyclerView.Adapter<MovieDbAdapter.ViewHolder> {
    private List<MovieModel> movieModels;
    private MovieDbAdapter.OnItemClickListener onItemClickListener;

    public MovieDbAdapter(List<MovieModel> movieModels, MovieDbAdapter.OnItemClickListener onItemClickListener) {
        this.movieModels = movieModels;
        this.onItemClickListener = onItemClickListener;
    }

    @NonNull
    @Override
    public MovieDbAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.movie_item, parent, false);
        return new MovieDbAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieDbAdapter.ViewHolder holder, int position) {
        MovieModel unsplashPhoto = movieModels.get(position);
        holder.bind(unsplashPhoto);
    }

    @Override
    public int getItemCount() {
        return movieModels.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        ImageView imgPhoto;
        TextView tvMovieName;

        ViewHolder(View itemView) {
            super(itemView);
            imgPhoto = itemView.findViewById(R.id.img_photo);
            tvMovieName = itemView.findViewById(R.id.tv_movie_name);
        }

        void bind(MovieModel movieModel) {
            GlideApp.with(itemView.getContext())
                    .load(BuildConfig.IMAGE_END_POINT_300 + movieModel.getPosterPath())
                    .placeholder(R.drawable.ic_placeholder)
                    .error(R.drawable.ic_error)
                    .fallback(R.drawable.ic_error)
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .into(imgPhoto);
            tvMovieName.setText(movieModel.getTitle());
            itemView.setOnClickListener(view -> onItemClickListener.onItemClick(itemView, movieModel, getAdapterPosition()));
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View view, MovieModel unsplashPhoto, int position);
    }
}
