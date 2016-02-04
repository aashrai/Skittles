package snow.skittles;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * A contract to be implemented by a Skittle class
 */
public interface BaseSkittle {

  void setIconDrawable(Drawable drawable);

  Drawable getIcon();

  void setColor(@ColorInt int color);

  @ColorInt int getColor();

  @SkittleType int getType();
}
