package circlebinder.creation.search;

import android.content.Context;

import circlebinder.common.circle.CircleAdapter;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.creation.event.CircleTable;

public final class CircleSearchContainer {

    private final CircleSearchViewHolder holder;
    private final CircleAdapter adapter;

    public CircleSearchContainer(Context context, CircleSearchViewHolder holder) {
        this.holder = holder;
        this.adapter = new CircleAdapter(
                context,
                CircleTable.get(),
                new CircleCursorCreator());
        this.holder.getCircles().setAdapter(adapter);
        holder.getCircles().setEmptyView(holder.getEmptyView());
    }

    public CircleSearchViewHolder getViewHolder() {
        return holder;
    }

    public void reload(CircleSearchOption searchOption) {
        adapter.setFilterQueryProvider(new CircleQueryProvider(searchOption));
        adapter.getFilter().filter("");
    }

    public void reload() {
        adapter.getFilter().filter("");
    }

}
