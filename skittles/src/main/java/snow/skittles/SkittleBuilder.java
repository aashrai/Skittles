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
    implements SkittleBuilder_Factory, SkittleAdapter.OnSkittleClickListener {

  private final SkittleAdapter skittleAdapter;
  private final List<BaseSkittle> skittles;
  boolean miniSkittlesAdded = false;
  OnSkittleClickListener skittleClickListener;

  public interface OnSkittleClickListener {
    void onSkittleClick(BaseSkittle skittle, int position);

    void onMainSkittleClick();
  }

  private SkittleBuilder(SkittleLayout skittleLayout) {
    this.skittleAdapter = skittleLayout.getSkittleAdapter();
    this.skittleAdapter.setMainSkittleClickListener(this);
    skittles = new ArrayList<>();
  }

  public static SkittleBuilder newInstance(SkittleLayout skittleLayout) {
    return new SkittleBuilder(skittleLayout);
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
}
