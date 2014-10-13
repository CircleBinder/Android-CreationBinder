package circlebinder.common.search;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.content.ContentReloader;
import net.ichigotake.common.os.BundleMerger;

import circlebinder.common.circle.CircleAdapter;
import circlebinder.common.event.BlockBuilder;
import circlebinder.common.app.BaseFragment;
import circlebinder.R;
import circlebinder.common.app.phone.CircleDetailActivity;
import se.emilsjolander.stickylistheaders.StickyListHeadersListView;

/**
 * サークルの検索をする
 */
public final class CircleSearchFragment extends BaseFragment
        implements OnCircleSearchOptionListener, ContentReloader {

    public static FragmentFactory<CircleSearchFragment> factory(CircleSearchOption searchOption) {
        return new CircleSearchFragmentFactory(searchOption);
    }

    private static class CircleSearchFragmentFactory implements FragmentFactory<CircleSearchFragment> {

        private final CircleSearchOption searchOption;

        public CircleSearchFragmentFactory(CircleSearchOption searchOption) {
            this.searchOption = searchOption;
        }

        @Override
        public CircleSearchFragment create() {
            CircleSearchFragment fragment = new CircleSearchFragment();
            Bundle args = new Bundle();
            args.putParcelable(KEY_SEARCH_OPTION_BUILDER, new CircleSearchOptionBuilder(searchOption));
            fragment.setArguments(args);
            return fragment;
        }
    }

    private static final String KEY_SEARCH_OPTION_BUILDER = "search_option_builder";
    private CircleSearchOptionBuilder searchOptionBuilder;
    private CircleAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        searchOptionBuilder = BundleMerger.merge(getArguments(), savedInstanceState)
                .getParcelable(KEY_SEARCH_OPTION_BUILDER);
        if (searchOptionBuilder == null) {
            searchOptionBuilder = new CircleSearchOptionBuilder()
                    .setBlock(new BlockBuilder().setName("全").setId(-1).build());
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        ViewGroup view = (ViewGroup)inflater.inflate(R.layout.common_fragment_circle_search, parent, false);
        StickyListHeadersListView circlesView = (StickyListHeadersListView)view
                .findViewById(R.id.common_fragment_circle_search_list);
        circlesView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                new ActivityTripper(
                        getActivity(),
                        CircleDetailActivity.createIntent(
                                getActivity(), searchOptionBuilder.build(), position
                        )
                ).trip();
            }
        });
        adapter = new CircleAdapter(getActivity(), new CircleCursorConverter());
        circlesView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        setSearchOption(searchOptionBuilder.build());
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(KEY_SEARCH_OPTION_BUILDER, searchOptionBuilder);
    }

    @Override
    public void setSearchOption(CircleSearchOption searchOption) {
        searchOptionBuilder.set(searchOption);
        adapter.setFilterQueryProvider(new CircleQueryProvider(searchOption));
        adapter.getFilter().filter("");
    }

    @Override
    public void reload() {
        adapter.reload();
    }

}