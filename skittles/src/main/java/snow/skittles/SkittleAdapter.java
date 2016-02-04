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
  private OnSkittleClickListener onSkittleClickListener;

  interface OnSkittleClickListener {
    void onSkittleClick(BaseSkittle skittle, int position);
  }

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

  public void removeAllMiniSkittles() {
    int prevSize = this.skittles.size();
    BaseSkittle mainSkittle = this.skittles.get(0);
    this.skittles.clear();
    this.skittles.add(mainSkittle);
    notifyItemRangeRemoved(1, prevSize);
  }

  public List<BaseSkittle> removeAllSkittles(List<BaseSkittle> skittles) {
    int prevSize = this.skittles.size();
    this.skittles.removeAll(skittles);
    notifyItemRangeRemoved(1, prevSize);
    return skittles;
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

  class MainSkittleViewHolder extends SkittleViewHolder implements View.OnClickListener {
    public MainSkittleViewHolder(View itemView) {
      super(itemView);
      itemView.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      if (onSkittleClickListener != null) onSkittleClickListener.onSkittleClick(skittles.get(0), 0);
    }
  }

  class SkittleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    FloatingActionButton skittle;

    public SkittleViewHolder(View itemView) {
      super(itemView);
      skittle = (FloatingActionButton) itemView.findViewById(R.id.skittle);
      skittle.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      if (onSkittleClickListener != null) {
        onSkittleClickListener.onSkittleClick(skittles.get(getAdapterPosition()),
            getAdapterPosition());
      }
    }
  }

  class TextSkittleViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    TextView text;
    FloatingActionButton skittle;

    public TextSkittleViewHolder(View itemView) {
      super(itemView);
      text = (TextView) itemView.findViewById(R.id.tv_skittle);
      skittle = (FloatingActionButton) itemView.findViewById(R.id.skittle);
      text.setOnClickListener(this);
      skittle.setOnClickListener(this);
    }

    @Override public void onClick(View v) {
      if (onSkittleClickListener != null) {
        onSkittleClickListener.onSkittleClick(skittles.get(getAdapterPosition()),
            getAdapterPosition());
      }
    }
  }

  public void setMainSkittleClickListener(OnSkittleClickListener skittleClickListener) {
    this.onSkittleClickListener = skittleClickListener;
  }
}
