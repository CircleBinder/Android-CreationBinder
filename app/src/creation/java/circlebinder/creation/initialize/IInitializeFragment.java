package circlebinder.creation.initialize;

import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.ichigotake.common.app.ActionViewActivityFactory;
import net.ichigotake.common.app.ActivityTripper;
import net.ichigotake.common.app.FragmentFactory;
import net.ichigotake.common.app.OnClickToTrip;

import circlebinder.Legacy;
import circlebinder.common.app.FragmentTripper;
import circlebinder.creation.BaseFragment;
import circlebinder.R;
import circlebinder.creation.app.phone.HomeActivity;

/**
 * 初期化をする
 */
public final class IInitializeFragment extends BaseFragment
        implements IInitializeServiceCallback, Legacy {

    public static FragmentFactory from() {
        return new FragmentFactory() {
            @Override
            public BaseFragment create() {
                return new IInitializeFragment();
            }
        };
    }

    public static FragmentTripper tripper(FragmentManager fragmentManager) {
        return new FragmentTripper(fragmentManager, from());
    }

    private Handler handler;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup parent, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.circlebinder_fragment_initialize, parent, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        View view = getView();

        TextView twitterHashTagView = (TextView) view.findViewById(
                R.id.circlebinder_twitter_official_hash_tag_name
        );
        String twitterHashTagUrl = getString(R.string.circlebinder_twitter_official_hash_tag_url);
        twitterHashTagView.setText(Html.fromHtml(
                "<a href=\"" + twitterHashTagUrl + "\">" +
                        getString(R.string.circlebinder_twitter_official_hash_tag_name) +
                        "</a>"
        ));
        twitterHashTagView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getActivity(),
                        new ActionViewActivityFactory(Uri.parse(twitterHashTagUrl)))
        ));

        TextView twitterScreenNameView = (TextView) view.findViewById(
                R.id.circlebinder_twitter_official_account_screen_name
        );
        String twitterScreenNameUrl = getString(R.string.circlebinder_twitter_official_account_url);
        twitterScreenNameView.setText(Html.fromHtml(
                "<a href=\"" + twitterScreenNameUrl + "\">" +
                        getString(R.string.circlebinder_twitter_official_account_screen_name) +
                        "</a>"
        ));
        twitterScreenNameView.setOnClickListener(new OnClickToTrip(
                new ActivityTripper(
                        getActivity(),
                        new ActionViewActivityFactory(Uri.parse(twitterScreenNameUrl)))
        ));

        View finishedView = view.findViewById(R.id.circlebinder_fragment_initialize_finished);
        finishedView.setOnClickListener(new OnClickToTrip(
                HomeActivity.tripper(getActivity()).withFinish()
        ));
        handler = new InitializeHandler(
                view.findViewById(R.id.circlebinder_fragment_initialize_progress),
                finishedView
        );

        getActivity().startService(new Intent(getActivity(), DatabaseInitializeService.class));
    }

    @Override
    public void initializeCompleted() {
        Message message = Message.obtain();
        handler.sendMessage(message);
    }

    @Override
    public IBinder asBinder() {
        return null;
    }

    private static class InitializeHandler extends Handler {

        private final View progressView;
        private final View finishedView;

        private InitializeHandler(View progressView, View finishedView) {
            this.progressView = progressView;
            this.finishedView = finishedView;
        }

        @Override
        public void handleMessage(Message message) {
            progressView.setVisibility(View.GONE);
            finishedView.setVisibility(View.VISIBLE);
        }
    }

}