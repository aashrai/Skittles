package snow.skittles;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by aashrai on 3/2/16.
 */
public interface SkittleBuilder_Factory {

  /**
   * Add skittle at the top
   *
   * @param skittle Skittle to be added, you can pass {@link Skittle}
   * or {@link TextSkittle}
   */
  void addSkittle(@NonNull BaseSkittle skittle);

  /**
   * Removes the skittle at the given index
   * <br/>
   * <b>NOTE:</b> this does not and cannot remove the main Skittle"
   *
   * @param index the index starts from 0 and does not include the main skittle
   * @return The removed skittle
   */
  BaseSkittle removeSkittle(@IntRange(from = 0) int index);

  void changeMainSkittleColor(@ColorInt int color);

  void changeMainSkittleIcon(@NonNull Drawable drawable);

  /**
   * Replace the Skittle at the given index
   *
   * @param index index to change the Skittle at
   * @param skittle new skittle to be replaced with the current one, you can pass {@link Skittle}
   * or {@link TextSkittle}
   * @throws IndexOutOfBoundsException
   */
  void changeSkittleAt(@IntRange(from = 0) int index, @NonNull BaseSkittle skittle);
}
