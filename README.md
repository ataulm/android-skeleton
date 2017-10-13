intro to chromecast sender
==========================

### Links

- updating dependencies: https://developers.google.com/cast/docs/android_sender_setup
- integrating v3 cast sdk: https://developers.google.com/cast/docs/android_sender_integrate

- standalone codelab for android: https://codelabs.developers.google.com/codelabs/cast-videos-android/#0

### Notes

#### Commits

- Initial video catalog with local playback 62ccdcaebcd40b983f424bc60db3b140df9784a0

#### Overview

The **Sender** is the mobile device or laptop that you're casting content from. It consists of the
_sender app_ and _sender framework_. Your app is the sender app. The library and its resources are
the framework.

The **Receiver** is the cast device that displays content. The receiver (in the context of your content
being cast to the device) is controlled by an HTML app (the receiver app), which contains your
business logic and controls what's being displayed.

- sender framework auto-starts MediaRouter device discovery (based on the Activity lifecycle)
- user clicks on the Cast button, the framework presents the Cast dialog with the list of discovered
Cast devices
- user selects Cast device as a route, the framework attempts to launch the receiver app on the Cast
device
- sender framework tells sender app that receiver launched
- sender framework creates a communication channel between the sender and receiver apps
- sender framework uses this channel to load and control media playback on the receiver
- sender framework synchronizes the media playback state between sender and receiver (e.g. user
actions triggering seek on receiver or seekbar UI updates on the sender device)
- user disconnects from the Cast device, the framework will disconnect the sender app from the
receiver

#### CastContext

There's a singleton `CastContext` which is used to coordinate the framework's interactions with the
app.

`CastContext` is initialized with `CastOptions` which you must provide:

```java
public class MyCastOptionsProvider implements OptionsProvider {
    @Override
    public CastOptions getCastOptions(Context context) {
        return new CastOptions.Builder()
                .setReceiverApplicationId("myreceiverid")
                .build();
    }

    @Override
    public List<SessionProvider> getAdditionalSessionProviders(Context context) {
        return null;
    }
}
```

and register in the AndroidManifest - you won't instantiate it directly:

```xml
<application>
    ...
    <meta-data
        android:name="com.google.android.gms.cast.framework.OPTIONS_PROVIDER_CLASS_NAME"
        android:value="com.example.MyCastOptionsProvider" />
</application>
```

```java
public class MyActivity {
    @Override
    public void onCreate() {
        // lazily initialized when `getSharedInstance()` is called for the first time
        CastContext castContext = CastContext.getSharedInstance(this);
    }
}
```