package snow.skittles;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.support.annotation.ColorInt;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by aashrai on 2/2/16.
 */
class SkittleAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

  private final List<BaseSkittle> skittles = new ArrayList<>();

  /**
   * @param color color of the main skittle
   * @param icon icon of the main skittle
   */
  public SkittleAdapter(@ColorInt int color, @NonNull Drawable icon) {
    //This ensures that no matter what there is always one main skittle available
    addSkittle(Skittle.newMainSkittleInstance(color, icon));
  }

  @Override public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
    switch (viewType) {
      case Constants.MAIN_SKITTLE:
        return new MainSkittleViewHolder(getInflatedView(R.layout.main_skittle, parent));
      case Constants.TEXT_SKITTLE:
        return new TextSkittleViewHolder(getInflatedView(R.layout.text_skittle, parent));
      default:
        return new SkittleViewHolder(getInflatedView(R.layout.skittle, parent));
    }
  }

  public void addSkittle(@NonNull BaseSkittle skittle) {
    int previousSize = skittles.size();
    skittles.add(skittle);
    notifyItemInserted(previousSize);
  }

  public void addAllSkittles(@NonNull List<BaseSkittle> skittles) {
    int previousSize = skittles.size();
    this.skittles.addAll(skittles);
    notifyItemRangeInserted(previousSize, this.skittles.size());
  }

  public BaseSkittle removeSkittle(int position) {
    final BaseSkittle skittle = this.skittles.remove(position);
    notifyItemRemoved(position);
    return skittle;
  }

  private View getInflatedView(@LayoutRes int id, ViewGroup parent) {
    return LayoutInflater.from(parent.getContext()).inflate(id, parent, false);
  }

  @Override public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
    if (holder instanceof MainSkittleViewHolder) {
      configureMainSkittle((MainSkittleViewHolder) holder, position);
    } else if (holder instanceof TextSkittleViewHolder) {
      configureTextSkittle((TextSkittleViewHolder) holder, position);
    } else if (holder instanceof SkittleViewHolder) {
      configureSkittle((SkittleViewHolder) holder, position);
    }
  }

  private void configureMainSkittle(MainSkittleViewHolder holder, int position) {
    configureSkittle(holder, position);
  }

  private void configureTextSkittle(TextSkittleViewHolder holder, int position) {
    TextSkittle textSkittle = (TextSkittle) skittles.get(position);
    holder.text.setText(textSkittle.getText());
    holder.text.setBackgroundColor(textSkittle.getTextBackground());
    holder.skittle.setBackgroundTintList(ColorStateList.valueOf(textSkittle.getColor()));
    holder.skittle.setImageDrawable(textSkittle.getIcon());
  }

  private void configureSkittle(SkittleViewHolder holder, int position) {
    holder.skittle.setBackgroundTintList(ColorStateList.valueOf(skittles.get(position).getColor()));
    holder.skittle.setImageDrawable(skittles.get(position).getIcon());
  }

  @Override public int getItemViewType(int position) {
    return skittles.get(position).getType();
  }

  @Override public int getItemCount() {
    return skittles.size();
  }

  public void changeMainSkittleColor(@ColorInt int color) {
    skittles.get(0).setColor(color);
    notifyItemChanged(0);
  }

  public void changeMainSkittleIcon(Drawable drawable) {
    skittles.get(0).setIconDrawable(drawable);
    notifyItemChanged(0);
  }

  public void changeSkittleAt(int index, BaseSkittle skittle) {
    skittles.set(index, skittle);
    notifyItemChanged(index);
  }

  class MainSkittleViewHolder extends SkittleViewHolder {

    public MainSkittleViewHolder(View itemView) {
      super(itemView);
    }
  }

  class SkittleViewHolder extends RecyclerView.ViewHolder {

    FloatingActionButton skittle;

    public SkittleViewHolder(View itemView) {
      super(itemView);
      skittle = (FloatingActionButton) itemView;
    }
  }

  class TextSkittleViewHolder extends RecyclerView.ViewHolder {
    TextView text;
    FloatingActionButton skittle;

    public TextSkittleViewHolder(View itemView) {
      super(itemView);
      text = (TextView) itemView.findViewById(R.id.tv_skittle);
      skittle = (FloatingActionButton) itemView.findViewById(R.id.skittle);
    }
  }
}