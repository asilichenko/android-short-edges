# Simple Android edge-to-edge app 

## Default layout

Base theme without action bar:

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
</style>
```

![Default layout](https://github.com/asilichenko/android-short-edges/assets/1503214/ff32bffc-2e91-42bb-9c1a-3f87084a003b)

## Set background color for status and navigation bars

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
  <item name="android:statusBarColor">@android:color/holo_purple</item>
  <item name="android:navigationBarColor">@android:color/holo_red_dark</item>
</style>
```

![Colored status and navigation bars](https://github.com/asilichenko/android-short-edges/assets/1503214/4db9578c-4623-4461-896e-2d9d43c0ccae)

## Make status and navigation bars transparent

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
  <item name="android:statusBarColor">@android:color/transparent</item>
  <item name="android:navigationBarColor">@android:color/transparent</item>
</style>
```

![Transparent status and navigation bars](https://github.com/asilichenko/android-short-edges/assets/1503214/c8606c90-3072-4e50-b98f-2bf1493dcd99)

## Make status bar text color contrasted

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
  <item name="android:windowLightStatusBar">true</item>
  ...
</style>
```

`windowLightStatusBar = true` -> status bar will be drawn compatible with a light background

![Status bar text color compatible with a light background](https://github.com/asilichenko/android-short-edges/assets/1503214/87b98566-f652-4449-926e-87597e755607)

## Make the layout fit the screen edge-to-edge

```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  ...
  WindowCompat.setDecorFitsSystemWindows(getWindow(), false);
}
```

![Layout fits the screen edge-to-edge](https://github.com/asilichenko/android-short-edges/assets/1503214/9c0bf722-c2ae-420c-a550-918282b83d8b)

## Layout is letterboxed in landscape mode

![Layout is cutout in landscape mode](https://github.com/asilichenko/android-short-edges/assets/1503214/2bda2b3f-a1d2-4293-90b8-145176be3cf2)

This 'bug' is present even in Google products like Maps and Google Earth; Sky Map is even displayed in full cutout mode.

## Make layout fit the screen edge-to-edge in landscape mode

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
  ...
  <item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
</style>
```

`windowLayoutInDisplayCutoutMode` can take one of three values:
* `default`: content renders into the cutout area when displayed in portrait mode, but is letterboxed when in landscape mode
* `shortEdges`: content always renders into the cutout area
* `never`: content never renders into the cutout area

![Layout edge-to-edge in landscape mode](https://github.com/asilichenko/android-short-edges/assets/1503214/470688e5-e728-416e-afb6-aa66e1c0886d)

`windowLayoutInDisplayCutoutMode` requires API level 27, so if your app supports lower API level, then extract it into "values-v27/themes.xml".

* values/themes.xml:

```
<style name="Base.Theme.ShortEdges" parent="Theme.Material3.Light.NoActionBar">
  ...
</style>

<style name="Theme.ShortEdges" parent="Base.Theme.ShortEdges" />
```

* values-v27/themes.xml:

```
<style name="Theme.ShortEdges" parent="Base.Theme.ShortEdges">
  <item name="android:windowLayoutInDisplayCutoutMode">shortEdges</item>
</style>
```

## How to determine safe region

```
@Override
protected void onCreate(@Nullable Bundle savedInstanceState) {
  ...
  ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.my_view), this::onApplyWindowInsets);
}

@NonNull
public WindowInsetsCompat onApplyWindowInsets(@NonNull View v, @NonNull WindowInsetsCompat windowInsets) {
  final Insets displayCutoutInsets = windowInsets.getInsets(WindowInsetsCompat.Type.displayCutout());
  final Insets systemBarsInsets = windowInsets.getInsets(WindowInsetsCompat.Type.systemBars());
  
  final Insets safeInsets = Insets.of(
    max(displayCutoutInsets.left, systemBarsInsets.left),
    max(displayCutoutInsets.top, systemBarsInsets.top),
    max(displayCutoutInsets.right, systemBarsInsets.right),
    max(displayCutoutInsets.bottom, systemBarsInsets.bottom)
  );

  return WindowInsetsCompat.CONSUMED;
}
```

## Set component view margins

```
final ViewGroup.MarginLayoutParams mlp = 
  (ViewGroup.MarginLayoutParams) view.getLayoutParams();

mlp.leftMargin = safeInsets.left;
mlp.topMargin = safeInsets.top;
mlp.bottomMargin = safeInsets.bottom;
mlp.rightMargin = safeInsets.right;

view.setLayoutParams(mlp);
```

![The button is in the safe zone](https://github.com/asilichenko/android-short-edges/assets/1503214/ff24098b-d2f6-4f49-8e16-eb7767f006ce)

## Source Code References
* [AndroidManifest.xml](app/src/main/AndroidManifest.xml)
* [MainActivity.java](app/src/main/java/ua/in/asilichenko/shortedges/MainActivity.java)
* [activity_main.xml](app/src/main/res/layout/activity_main.xml)
* [values/themes.xml](app/src/main/res/values/themes.xml)
* [values-v27/themes.xml](app/src/main/res/values-v27/themes.xml)

## References
* Display content edge-to-edge in your app: https://developer.android.com/develop/ui/views/layout/edge-to-edge
* Support display cutouts: https://developer.android.com/develop/ui/views/layout/display-cutout
