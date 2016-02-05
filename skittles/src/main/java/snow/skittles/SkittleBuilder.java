package snow.skittles;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashrai on 3/2/16.
 */
public class SkittleBuilder
    implements SkittleBuilder_Factory, SkittleAdapter.OnSkittleClickListener,
    SkittleLayout.OnSkittleContainerClickListener {

  private final SkittleAdapter skittleAdapter;
  private final SkittleContainer skittleContainer;
  private final List<BaseSkittle> skittles;
  private final boolean closeOnTap;
  boolean miniSkittlesAdded = false;
  OnSkittleClickListener skittleClickListener;

  public interface OnSkittleClickListener {
    void onSkittleClick(BaseSkittle skittle, int position);

    void onMainSkittleClick();
  }

  private SkittleBuilder(SkittleLayout skittleLayout, boolean closeOnTap) {
    this.closeOnTap = closeOnTap;
    this.skittleAdapter = skittleLayout.getSkittleAdapter();
    this.skittleAdapter.setMainSkittleClickListener(this);
    this.skittleContainer = skittleLayout.getSkittleContainer();
    skittleLayout.setSkittleContainerClickListener(this);
    skittles = new ArrayList<>();
  }

  /**
   * Returns a new instance of {@link SkittleBuilder}
   *
   * @param skittleLayout The root container of your layouts
   * @param closeOnTap Should the skittle menu be closed on tap or other touch events on the screen
   */
  public static SkittleBuilder newInstance(SkittleLayout skittleLayout, boolean closeOnTap) {
    return new SkittleBuilder(skittleLayout, closeOnTap);
  }

  @Override public void addSkittle(@NonNull BaseSkittle skittle) {
    skittles.add(skittle);
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

  private void onMainSkittleClick() {
    if (!miniSkittlesAdded) {
      skittleAdapter.addAllSkittles(skittles);
    } else {
      skittleAdapter.removeAllMiniSkittles();
    }
    miniSkittlesAdded = !miniSkittlesAdded;
    skittleContainer.setSkittlesAdded(closeOnTap && miniSkittlesAdded);
    if (skittleClickListener != null) skittleClickListener.onMainSkittleClick();
  }

  public void setSkittleClickListener(OnSkittleClickListener skittleClickListener) {
    this.skittleClickListener = skittleClickListener;
  }

  @Override public void onSkittleClick(BaseSkittle skittle, int position) {
    if (position == 0) {
      onMainSkittleClick();
    } else {
      //Subtract the position to make it start from the first mini skittle instead of the main skittle
      skittleClickListener.onSkittleClick(skittle, position - 1);
    }
  }

  @Override public void onSkittleContainerClick() {
    if (miniSkittlesAdded) onMainSkittleClick();
  }
}
