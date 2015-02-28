package net.ichigotake.common.rx;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import net.ichigotake.common.app.broadcast.BroadcastManager;

import net.ichigotake.common.app.broadcast.Broadcaster;
import rx.Observable;
import rx.android.lifecycle.LifecycleEvent;
import rx.subjects.BehaviorSubject;

public abstract class RxActionBarActivity extends ActionBarActivity {

    private final BehaviorSubject<LifecycleEvent> lifecycleSubject = BehaviorSubject.create();
    private final BroadcastManager broadcastManager = new BroadcastManager();

    public Observable<LifecycleEvent> lifecycle() {
        return lifecycleSubject.asObservable();
    }
    
    public void registerReceiver(Broadcaster broadcaster) {
        broadcastManager.registerReceiver(broadcaster);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        lifecycleSubject.onNext(LifecycleEvent.CREATE);
        broadcastManager.onCreate(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        lifecycleSubject.onNext(LifecycleEvent.START);
    }

    @Override
    protected void onResume() {
        super.onResume();
        lifecycleSubject.onNext(LifecycleEvent.RESUME);
    }

    @Override
    protected void onPause() {
        lifecycleSubject.onNext(LifecycleEvent.PAUSE);
        super.onPause();
    }

    @Override
    protected void onStop() {
        lifecycleSubject.onNext(LifecycleEvent.STOP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        broadcastManager.onDestroy();
        lifecycleSubject.onNext(LifecycleEvent.DESTROY);
        super.onDestroy();
    }

}