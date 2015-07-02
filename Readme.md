#Skittles
[![Android Arsenal](https://img.shields.io/badge/Android%20Arsenal-Skittles-brightgreen.svg?style=flat)](http://android-arsenal.com/details/1/2076)

A simple,clean api for adding PushBullet style skittles to your app for more material design glory.This library uses the <a href="https://developer.android.com/reference/android/support/design/widget/FloatingActionButton.html">FloatingActionButton</a> provided in the android design support library

<img src="art/Skittle.gif" width=196 height=350/>
<img src="art/TextSkittle.gif" width=196 height=350/>

##Guide ([Sample](sample/src/main/java/snow/skittlessample/MainActivity.java))

First some housekeeping code:

Use
[SkittleLayout](library/src/main/java/snow/skittles/SkittleLayout.java) as a root view in your layouts

```
<snow.skittles.SkittleLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:app="http://schemas.android.com/apk/res-auto"
xmlns:tools="http://schemas.android.com/tools"
android:id="@+id/skittleLayout"
android:layout_width="match_parent"
android:layout_height="match_parent"
android:fitsSystemWindows="true"
app:mainSkittleColor="@color/material_deep_teal_500"
app:mainSkittleIcon="@drawable/ic_android_white_18dp"
tools:context=".MainActivity">

<android.support.design.widget.AppBarLayout
android:id="@+id/appbar"
android:layout_width="match_parent"
android:layout_height="@dimen/appBarMaxHeight"
android:fitsSystemWindows="true"
android:theme="@style/ThemeOverlay.AppCompat.Dark.ActionBar">
...

</snow.skittles.SkittleLayout>
```

Some further housekeeping...

Declare a [SkittleBuilder](library/src/main/java/snow/skittles/SkittleBuilder.java),used to add skittles and pass the root SkittleLayout to it

```
SkittleLayout skittleLayout = (SkittleLayout) findViewById(R.id.skittleLayout);
SkittleBuilder skittleBuilder = new SkittleBuilder(this, skittleLayout, false, R.color.material_deep_purple_500, R.color.material_deep_teal_500);

```

Now for the fun part

Add skittles to your activity/fragment

```
skittleBuilder.addSkittle(R.drawable.lannister_icon, R.color.lannister);
skittleBuilder.addSkittle(R.drawable.barratheon_icon, R.color.barratheon);
skittleBuilder.addSkittle(R.drawable.stark_icon, R.color.stark);
```

A bit more work for adding [Text Skittle](library/src/main/java/snow/skittles/TextSkittle.java)

```
TextSkittle textSkittle = skittleBuilder.makeTextSkittle
(R.drawable.lannister_icon, getResources().getString(R.string.house_lannister), R.color.lannister);
textSkittle.setTextBackgroundColor(R.color.textBackground);
textSkittle.setTextColor(android.R.color.black);
skittleBuilder.addTextSkittle(textSkittle);

```

Flexible callback for clicks:

+ Add a click listener(SkittleBuilder.SkittleClickListener) to the [SkittleBuilder](library/src/main/java/snow/skittles/SkittleBuilder.java) object
`skittleBuilder.setSkittleListener(this);`

+ This exposes two methods for [Skittle](library/src/main/java/snow/skittles/Skittle.java) and [TextSkittle](library/src/main/java/snow/skittles/TextSkittle.java) click events for convenience

```
void onSkittleClick(Skittle skittle);

void onTextSkittleClick(TextSkittle textSkittle);
```

Use `skittle.getPosition()` which return the position of the clicked
skittle starting from **1** and starting from **bottom**

```
public void onSkittleClick(Skittle skittle) {

  switch (skittle.getPosition()) {
    case 1:
    Toast.makeText(this, "Skittle 1", Toast.LENGTH_LONG).show();
    break;
    case 2:
    Toast.makeText(this, "Skittle 2", Toast.LENGTH_LONG).show();
    break;
  }

}
```

Similarly for [Text Skittle](library/src/main/java/snow/skittles/TextSkittle.java)
```
public void onTextSkittleClick(TextSkittle textSkittle) {

  switch (textSkittle.getPosition()) {
    case 1:
    Toast.makeText(this, "Skittle 1", Toast.LENGTH_LONG).show();
    break;
    case 2:
    Toast.makeText(this, "Skittle 2", Toast.LENGTH_LONG).show();
    break;
  }
}
```

##Gradle
```
dependencies{
compile 'com.rlj.library:skittles:0.0.1-beta'
}
```

See the **[Sample](sample/src/main/java/snow/skittlessample/MainActivity.java)** and **[JavaDoc](http://aashrairavooru.github.io/Skittles/)** for further guidance

##Sample Screenshots
<img src="art/Sample1.png" width=196 height=350/>
<img src="art/Sample2.png" width=196 height=350/>
<img src="art/Sample3.png" width=196 height=350/>

##Upcoming
+ Better support for animations
+ Option for adding skittles by xml (inspired by Navigation View)
+ Test on more devices
+ Upload to Maven Central

**Currently in heavy dev, good enough for playing around**


#Taste the rainbow
![Skittles](art/Taste the rainbow.jpg)
