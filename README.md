themes vs theme overlays
---

The activity theme doesn't behave as a theme overlay on top of the app theme.

Consider we have the following themes:

```xml
<style name="NoParentTheme" parent="" />

<style name="Theme.Demo" parent="Base.Theme.Demo">
    <item name="colorPrimary">@color/material_red_500</item>
    ...
```

If we set the `NoParentTheme` on the activity:

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="com.example">

    <application
        android:allowBackup="true"
        android:icon="@drawable/ic_launcher"
        android:label="@string/app_name"
        android:theme="@style/Theme.Demo">

        <activity android:name="com.example.MyActivity"
            android:theme="@style/NoParentTheme"/>
            ...
```

then a view in that activity that's looking for `colorPrimary` won't be able to find it, and the app will crash.
