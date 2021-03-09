package business.adapters;

import android.view.View;

/**
 * Generic interface for click listeners
 */
public interface RecyclerClickListener {
    /**
     * method that will be called when item is clicked
     * @param view view that was clicked
     * @param position position of the view in the listing
     */
    void onItemClick(View view, int position);
}
