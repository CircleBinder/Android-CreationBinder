package circlebinder.common.app.phone;

import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Configuration;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.view.View;

import net.ichigotake.common.app.ActivityNavigation;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.common.circle.CircleDetailHeaderView;
import circlebinder.common.event.Circle;
import circlebinder.common.search.CircleSearchOption;
import circlebinder.common.app.BaseActivity;
import circlebinder.R;
import circlebinder.common.circle.CircleDetailFragment;
import circlebinder.common.circle.OnCirclePageChangeListener;
import circlebinder.common.search.CircleCursorConverter;
import circlebinder.common.search.CircleLoader;

public final class CircleDetailActivity extends BaseActivity
        implements Legacy, OnCirclePageChangeListener, LoaderManager.LoaderCallbacks<Cursor> {

    private static final String EXTRA_KEY_SEARCH_OPTION = "search_option";
    private static final String EXTRA_KEY_POSITION = "position";

    public static Intent createIntent(Context context, CircleSearchOption searchOption, int position) {
        Intent intent = new Intent(context, CircleDetailActivity.class);
        Bundle extras = new Bundle();
        extras.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        extras.putInt(EXTRA_KEY_POSITION, position);
        intent.putExtras(extras);
        return intent;
    }

    private CircleSearchOption searchOption;
    private int currentPosition;
    private CircleDetailHeaderView inlineHeaderViewHolder;
    private CircleDetailHeaderView actionBarHeaderView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActionBar actionBar = ActivityNavigation.getSupportActivity(this).getSupportActionBar();
        actionBar.setDisplayShowTitleEnabled(false);
        actionBar.setDisplayShowCustomEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.common_activity_circle_detail);

        inlineHeaderViewHolder =
                (CircleDetailHeaderView) findViewById(R.id.common_activity_circle_detail_header);
        actionBarHeaderView = new CircleDetailHeaderView(this);
        actionBar.setCustomView(actionBarHeaderView);

        Bundle bundle = BundleMerger.merge(getIntent(), savedInstanceState);
        searchOption = bundle.getParcelable(EXTRA_KEY_SEARCH_OPTION);
        currentPosition = bundle.getInt(EXTRA_KEY_POSITION);

        orientationConfig(getResources().getConfiguration());
        getLoaderManager().initLoader(0, bundle, this);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        return ActivityNavigation.back(this, menuItem)
                || super.onOptionsItemSelected(menuItem);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(EXTRA_KEY_SEARCH_OPTION, searchOption);
        outState.putInt(EXTRA_KEY_POSITION, currentPosition);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        orientationConfig(newConfig);
    }

    @Override
    public void onCirclePageChanged(Circle circle) {
        this.actionBarHeaderView.setCircle(circle);
        this.inlineHeaderViewHolder.setCircle(circle);
        orientationConfig(getResources().getConfiguration());
        invalidateOptionsMenu();
    }

    private void orientationConfig(Configuration configuration) {
        ActionBar actionBar = ActivityNavigation.getSupportActionBar(this);
        if (configuration.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            actionBar.setDisplayHomeAsUpEnabled(true);
            actionBar.setDisplayShowCustomEnabled(true);
            inlineHeaderViewHolder.setVisibility(View.GONE);
        } else {
            actionBar.setDisplayHomeAsUpEnabled(false);
            actionBar.setDisplayShowCustomEnabled(false);
            inlineHeaderViewHolder.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {
        return new CircleLoader(getApplicationContext(), searchOption);
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToPosition(currentPosition);
        final Circle circle = new CircleCursorConverter().create(data);
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                new FragmentTripper(getSupportFragmentManager(), CircleDetailFragment.factory(circle))
                        .setAddBackStack(false)
                        .setLayoutId(R.id.common_activity_circle_detail_item)
                        .trip();
            }
        });
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

    }

}
