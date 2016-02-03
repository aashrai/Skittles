package snow.skittles;

import android.support.annotation.IntDef;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * Created by aashrai on 2/2/16.
 */
@IntDef({ Constants.MAIN_SKITTLE, Constants.SKITTLE, Constants.TEXT_SKITTLE })
@Retention(RetentionPolicy.SOURCE) @interface SkittleType {
}
