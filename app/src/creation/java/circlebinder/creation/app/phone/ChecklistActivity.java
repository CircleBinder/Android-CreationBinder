package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.Tripper;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.R;
import circlebinder.common.checklist.ChecklistColor;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.checklist.ChecklistFragment;

public final class ChecklistActivity extends BaseActivity {

    private static final String KEY_CHECKLIST_COLOR = "checklist_color";
    public static Tripper tripper(Context context, ChecklistColor checklistColor) {
        return new ActivityTripper(context, new ChecklistActivityTripper(checklistColor));
    }

    private static class ChecklistActivityTripper implements ActivityFactory {

        private final ChecklistColor checklistColor;

        private ChecklistActivityTripper(ChecklistColor checklistColor) {
            this.checklistColor = checklistColor;
        }

        @Override
        public Intent create(Context context) {
            Intent intent = new Intent(context, ChecklistActivity.class);
            Bundle map = new Bundle();
            map.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
            intent.putExtras(map);
            return intent;
        }
    }

    private ChecklistColor checklistColor;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.acticity_checklist);
        checklistColor = (ChecklistColor) BundleMerger.merge(getIntent(), savedInstanceState)
                .getSerializable(KEY_CHECKLIST_COLOR);
        ChecklistFragment
                .tripper(getFragmentManager(), checklistColor)
                .setLayoutId(R.id.activity_checklist_container)
                .setAddBackStack(false)
                .trip();
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable(KEY_CHECKLIST_COLOR, checklistColor);
    }
}