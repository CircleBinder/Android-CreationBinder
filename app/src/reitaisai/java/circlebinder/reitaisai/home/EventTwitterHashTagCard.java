package circlebinder.reitaisai.home;

import android.content.Context;
import android.content.Intent;

import com.dmitriy.tarasov.android.intents.IntentUtils;

import circlebinder.R;
import circlebinder.common.card.HomeCard;

public final class EventTwitterHashTagCard implements HomeCard {

    private final String label;
    private final String caption;

    public EventTwitterHashTagCard(Context context) {
        this.label = context.getString(R.string.app_event_twitter_label);
        this.caption = context.getString(R.string.app_event_twitter_hash_tag_label);
    }

    @Override
    public CharSequence getLabel() {
        return label;
    }

    @Override
    public CharSequence getCaption() {
        return caption;
    }

    @Override
    public int getBackgroundResource() {
        return R.color.common_card_twitter_background;
    }

    @Override
    public Intent createTransitIntent(Context context) {
        return IntentUtils.openLink(context.getString(R.string.app_event_twitter_hash_tag_url));
    }


}