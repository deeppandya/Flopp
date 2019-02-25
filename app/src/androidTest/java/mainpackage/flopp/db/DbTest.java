package mainpackage.flopp.db;

import android.arch.persistence.room.Room;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;

import mainpackage.flopp.database.MovieDbDatabase;

/**
 * Created by deeppandya
 * On 2019-02-25.
 */


@RunWith(AndroidJUnit4.class)
abstract class DbTest {

    MovieDbDatabase movieDbDatabase;

    @Before
    public void initDB() {
        movieDbDatabase = Room.inMemoryDatabaseBuilder(InstrumentationRegistry.getInstrumentation().getContext(), MovieDbDatabase.class).build();
    }

    @After
    public void closeDB() {
        movieDbDatabase.close();
    }
}
