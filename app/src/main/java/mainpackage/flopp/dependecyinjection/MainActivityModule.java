package mainpackage.flopp.dependecyinjection;

import dagger.Module;
import dagger.android.ContributesAndroidInjector;
import mainpackage.flopp.view.MainActivity;

/**
 * Created by deeppandya
 * On 2019-02-24.
 */
@Module
public abstract class MainActivityModule {
    @ContributesAndroidInjector(modules = AppModule.class)
    abstract MainActivity contributeMainActivity();
}
