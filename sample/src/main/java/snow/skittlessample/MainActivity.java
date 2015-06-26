package snow.skittlessample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import snow.skittles.SkittleLayout;
import snow.skittles.Skittles;

public class MainActivity extends AppCompatActivity {

    SkittleLayout skittleLayout;
    Skittles skittles;
    int skittleCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skittleLayout = (SkittleLayout) findViewById(R.id.skittleLayout);
        skittles = new Skittles(this, skittleLayout);

        addSkittles();
    }

    private void addSkittles() {

        Skittles.Skittle skittle = new Skittles.Skittle(R.id.skittle_main + 1, getResources()
                .getDrawable(R.drawable.ic_android_white_18dp));
        skittles.addSkittle(skittle);

        skittle = new Skittles.Skittle(R.id.skittle_main + 2, getResources()
                .getDrawable(R.drawable.ic_android_white_18dp));
        skittles.addSkittle(skittle);

        Skittles.TextSkittle textSkittle = new Skittles.TextSkittle(R.id.skittle_main + 3, getResources()
                .getDrawable(R.drawable.ic_android_white_18dp), "Android");
        skittles.addTextSkittle(textSkittle);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
