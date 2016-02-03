package snow.skittles.rehash;

import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;

/**
 * Created by aashrai on 2/2/16.
 */
public class TextSkittle implements BaseSkittle {

  private String text;
  private @ColorInt int color;
  private Drawable icon;
  private @ColorInt int textBackground;

  private TextSkittle(String text, int color, Drawable drawable, int textBackground) {
    this.text = text;
    this.color = color;
    this.icon = drawable;
    this.textBackground = textBackground;
  }

  public static class Builder {
    private final String text;
    private final @ColorInt int color;
    private final Drawable icon;
    private @ColorInt int textBackground;

    public Builder(String text, int color, Drawable drawable) {
      this.text = text;
      this.color = color;
      icon = drawable;
      textBackground = 0x7f0b0014;//light background
    }

    public Builder setTextBackground(@ColorInt int textBackground) {
      this.textBackground = textBackground;
      return this;
    }

    public TextSkittle build() {
      return new TextSkittle(text, color, icon, textBackground);
    }
  }

  public void setTextBackground(@ColorInt int color) {
    textBackground = color;
  }

  @Override public void setIconDrawable(Drawable drawable) {
    this.icon = drawable;
  }

  @Override public Drawable getIcon() {
    return icon;
  }

  @Override public void setColor(@ColorInt int color) {
    this.color = color;
  }

  @Override public int getColor() {
    return color;
  }

  @Override public int getType() {
    return Constants.TEXT_SKITTLE;
  }

  public int getTextBackground() {
    return textBackground;
  }

  public String getText() {
    return text;
  }

  public void setText(String text) {
    this.text = text;
  }
}
