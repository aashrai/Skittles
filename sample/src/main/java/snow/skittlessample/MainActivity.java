package snow.skittlessample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import snow.skittles.Skittle;
import snow.skittles.SkittleBuilder;
import snow.skittles.SkittleLayout;
import snow.skittles.TextSkittle;

public class MainActivity extends AppCompatActivity implements SkittleBuilder.SkittleClickListener {

    SkittleLayout skittleLayout;
    SkittleBuilder skittleBuilder;
    int skittleCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skittleLayout = (SkittleLayout) findViewById(R.id.skittleLayout);
        skittleBuilder = new SkittleBuilder(this, skittleLayout);
        skittleBuilder.setSkittleListener(this);

        addSkittles();
    }

    private void addSkittles() {

        skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_android_white_18dp));
        skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_android_white_18dp));

        TextSkittle textSkittle = skittleBuilder.makeTextSkittle(getResources()
                .getDrawable(R.drawable.ic_android_white_18dp), "Android");
        textSkittle.setTextColor(R.color.material_blue_grey_900);
        skittleBuilder.addTextSkittle(textSkittle);
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

    @Override
    public void onSkittleClick(Skittle skittle) {
        Toast.makeText(this, "Pressed " + skittle.getPosition(), Toast.LENGTH_LONG).show();

    }

    @Override
    public void onTextSkittleClick(TextSkittle textSkittle) {
        Toast.makeText(this, "Pressed " + textSkittle.getPosition(), Toast.LENGTH_LONG).show();

    }
}
