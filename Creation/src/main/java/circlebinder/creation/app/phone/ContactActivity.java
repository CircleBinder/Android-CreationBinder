package circlebinder.creation.app.phone;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;

import net.ichigotake.common.app.ActivityFactory;
import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.app.ActivityTripper;

import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseActivity;
import circlebinder.creation.R;
import circlebinder.creation.system.ContactFragment;

public final class ContactActivity extends BaseActivity {

    public static ActivityTripper tripper(Context context) {
        return new ActivityTripper(context, factory());
    }

    public static ActivityFactory factory() {
        return new ActivityFactory() {
            @Override
            public Intent create(Context context) {
                return new Intent(context, ContactActivity.class);
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.circlebinder_activity_basic);
        FragmentTripper.firstTrip(
                getSupportFragmentManager(),
                ContactFragment.factory()
        ).trip();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

}
