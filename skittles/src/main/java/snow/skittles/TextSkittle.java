package snow.skittles;

import android.graphics.Color;
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
  private @ColorInt int textColor;

  private TextSkittle(String text, int color, Drawable drawable, int textBackground,
      int textColor) {
    this.text = text;
    this.color = color;
    this.icon = drawable;
    this.textBackground = textBackground;
    this.textColor = textColor;
  }

  public static class Builder {
    private final String text;
    private final @ColorInt int color;
    private final Drawable icon;
    private @ColorInt int textBackground;
    private @ColorInt int textColor;

    public Builder(String text, int color, Drawable drawable) {
      this.text = text;
      this.color = color;
      icon = drawable;
      textBackground = Color.parseColor("#ffffff");
      textColor = Color.parseColor("#000000");
    }

    public Builder setTextBackground(@ColorInt int textBackground) {
      this.textBackground = textBackground;
      return this;
    }

    public Builder setTextColor(@ColorInt int textColor) {
      this.textColor = textColor;
      return this;
    }

    public TextSkittle build() {
      return new TextSkittle(text, color, icon, textBackground, textColor);
    }
  }

  public @ColorInt int getTextColor() {
    return textColor;
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
