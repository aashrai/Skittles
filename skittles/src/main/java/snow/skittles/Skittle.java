package snow.skittles;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by aashrai on 2/2/16.
 */
public class Skittle implements BaseSkittle {
  private @ColorInt int color;
  private Drawable drawable;
  private final @SkittleType int type;

  private Skittle(@ColorInt int color, Drawable drawable, int type) {
    this.color = color;
    this.drawable = drawable;
    this.type = type;
  }

  public static Skittle newInstance(@ColorInt int color, Drawable drawable) {
    return new Skittle(color, drawable, Constants.SKITTLE);
  }

  static Skittle newMainSkittleInstance(@ColorInt int color, Drawable drawable) {
    return new Skittle(color, drawable, Constants.MAIN_SKITTLE);
  }

  @Override public void setIconDrawable(Drawable drawable) {
    this.drawable = drawable;
  }

  @Override public Drawable getIcon() {
    return drawable;
  }

  @Override public void setColor(@ColorInt int color) {
    this.color = color;
  }

  @Override @ColorInt public int getColor() {
    return color;
  }

  @Override public int getType() {
    return type;
  }
}
