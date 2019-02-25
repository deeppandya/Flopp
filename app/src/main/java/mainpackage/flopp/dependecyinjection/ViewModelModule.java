package mainpackage.flopp.dependecyinjection;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import dagger.Binds;
import dagger.Module;
import dagger.multibindings.IntoMap;
import mainpackage.flopp.viewmodel.MovieDbViewModel;
import mainpackage.flopp.viewmodel.ViewModelFactory;

@Module
abstract class ViewModelModule {
    @Binds
    @IntoMap
    @ViewModelKey(MovieDbViewModel.class)
    abstract ViewModel bindMovieDbViewModel(MovieDbViewModel movieDbViewModel);

    @Binds
    abstract ViewModelProvider.Factory bindViewModelFactory(ViewModelFactory factory);
}
