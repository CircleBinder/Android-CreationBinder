package circlebinder.common.circle;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import circlebinder.R;

public final class CircleViewHolder {

    private final TextView circleName;
    private final TextView penName;
    private final TextView genre;
    private final ImageView checklist;
    private final TextView space;
    private final View spaceContainer;

    public CircleViewHolder(View container) {
        this.circleName = (TextView)container.findViewById(R.id.common_circle_list_item_name);
        this.penName = (TextView)container.findViewById(R.id.common_circle_list_item_pen_name);
        this.genre = (TextView)container.findViewById(R.id.common_circle_list_item_genre);
        this.checklist = (ImageView) container.findViewById(R.id.common_circle_list_checklist);
        this.space = (TextView)container.findViewById(R.id.common_circle_list_item_space);
        this.spaceContainer = container.findViewById(R.id.common_circle_list_item_space_container);
    }

    public TextView getCircleName() {
        return circleName;
    }

    public TextView getPenName() {
        return penName;
    }

    public TextView getGenre() {
        return genre;
    }

    public ImageView getChecklist() {
        return checklist;
    }

    public TextView getSpace() {
        return space;
    }

    public View getSpaceContainer() {
        return spaceContainer;
    }

}
