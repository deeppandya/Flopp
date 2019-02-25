package mainpackage.flopp.runner;

import android.app.Application;
import android.content.Context;
import android.support.test.runner.AndroidJUnitRunner;

/**
 * Created by deeppandya
 * On 2019-02-25.
 */
class AndroidJunitTestRunner extends AndroidJUnitRunner {
    @Override
    public Application newApplication(ClassLoader cl, String className, Context context) throws ClassNotFoundException, IllegalAccessException, InstantiationException {
        return super.newApplication(cl, className, context);
    }
}
