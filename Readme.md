#Skittles

A simple,clean api for adding PushBullet style skittles to your app for more material design glory.

<img src="art/Skittle.gif" width=196 height=350/>
<img src="art/TextSkittle.gif" width=196 height=350/>

##Guide

First some housekeeping code:

Use **snow.skittles.SkittleLayout** as a root view in your layouts

```
<snow.skittles.SkittleLayout xmlns:android="http://schemas.android.com/apk/res/android"
xmlns:tools="http://schemas.android.com/tools"
android:id="@+id/skittleLayout"
android:layout_width="match_parent"
android:layout_height="match_parent"
tools:context=".MainActivity">

<android.support.v7.widget.Toolbar
android:id="@+id/toolbar"
android:layout_width="match_parent"
android:layout_height="?attr/actionBarSize"
android:layout_alignParentTop="true"
android:background="?attr/colorPrimary"
android:minHeight="?attr/actionBarSize" />

</snow.skittles.SkittleLayout>
```

Some further housekeeping...

Declare a SkittleBuilder,used to add skittles and pass the root SkittleLayout to it

```
SkittleLayout skittleLayout = (SkittleLayout) findViewById(R.id.skittleLayout);
SkittleBuilder skittleBuilder = new SkittleBuilder(this, skittleLayout);

```

Now for the fun part

Add skittles to your activity/fragment

```
skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_link_white_18dp));

skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_add_white_18dp));

skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_create_white_18dp));
```

A bit more work for adding Text Skittle

```
TextSkittle textSkittle = skittleBuilder.makeTextSkittle(getResources().getDrawable(R.drawable.ic_link_white_18dp), "Jon is alive");
skittleBuilder.addTextSkittle(textSkittle);

textSkittle=skittleBuilder.makeTextSkittle(getResources().getDrawable(R.drawable.ic_add_white_18dp), "Boltons will die");
skittleBuilder.addTextSkittle(textSkittle);

textSkittle=skittleBuilder.makeTextSkittle(getResources().getDrawable(R.drawable.ic_create_white_18dp), "Cleganebowl");
skittleBuilder.addTextSkittle(textSkittle);
```

Sweet methods for modifying the look

```
textSkittle.setIcon() //Pass Drawable
textSkittle.setTextColor() //Pass @ColorRes
textSkittle.setTextBackground() //Pass Drawable
textSkittle.setTextBackgroundColor() //Pass TextBackground Color
```

##Upcoming
+ Better support for animations
+ Option for adding skittles by xml (inspired by Navigation View)
+ Test on more devices
+ Upload to Maven Central

**Currently in heavy dev, good enough for playing around**


#Taste the rainbow
![Skittles](art/Taste the rainbow.jpg)
