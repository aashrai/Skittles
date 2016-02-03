package snow.skittles;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;

/**
 * Created by aashrai on 3/2/16.
 */
public class SkittleBuilder implements SkittleBuilder_Factory {

  private final SkittleAdapter skittleAdapter;

  private SkittleBuilder(SkittleLayout skittleLayout) {
    this.skittleAdapter = skittleLayout.getSkittleAdapter();
  }

  public static SkittleBuilder newInstance(SkittleLayout skittleLayout) {
    return new SkittleBuilder(skittleLayout);
  }

  @Override public void addSkittle(@NonNull BaseSkittle skittle) {
    skittleAdapter.addSkittle(skittle);
  }

  @Override public BaseSkittle removeSkittle(@IntRange(from = 0) int index) {
    return skittleAdapter.removeSkittle(index + 1);//Adding 1 to prevent removal of main skittle
  }

  @Override public void changeMainSkittleColor(@ColorInt int color) {
    skittleAdapter.changeMainSkittleColor(color);
  }

  @Override public void changeMainSkittleIcon(@NonNull Drawable drawable) {
    skittleAdapter.changeMainSkittleIcon(drawable);
  }

  @Override
  public void changeSkittleAt(@IntRange(from = 0) int index, @NonNull BaseSkittle skittle) {
    skittleAdapter.changeSkittleAt(index + 1, skittle);//Adding 1 to account for main skittle
  }
}
