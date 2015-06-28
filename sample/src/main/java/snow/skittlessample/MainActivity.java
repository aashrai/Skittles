package snow.skittlessample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
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
    Toolbar toolbar;
    int skittleCount = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        skittleLayout = (SkittleLayout) findViewById(R.id.skittleLayout);
        skittleBuilder = new SkittleBuilder(this, skittleLayout);
        skittleBuilder.setSkittleListener(this);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        addSkittles();
    }

    private void addSkittles() {

        skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_link_white_18dp));
        skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_add_white_18dp));
        skittleBuilder.addSkittle(getResources().getDrawable(R.drawable.ic_create_white_18dp));
//        TextSkittle textSkittle = skittleBuilder.makeTextSkittle(getResources()
//                .getDrawable(R.drawable.ic_link_white_18dp), "Jon is alive");
//        skittleBuilder.addTextSkittle(textSkittle);
//        textSkittle = skittleBuilder.makeTextSkittle(getResources()
//                .getDrawable(R.drawable.ic_add_white_18dp), "Boltons will die");
//        skittleBuilder.addTextSkittle(textSkittle);
//        textSkittle = skittleBuilder.makeTextSkittle(getResources()
//                .getDrawable(R.drawable.ic_create_white_18dp), "Cleganebowl");
//        skittleBuilder.addTextSkittle(textSkittle);
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

        switch (skittle.getPosition()) {
            case 1:
                Toast.makeText(this, "Skittle 1", Toast.LENGTH_LONG).show();
                break;
            case 2:
                Toast.makeText(this, "Skittle 2", Toast.LENGTH_LONG).show();
                break;
        }

    }

    @Override
    public void onTextSkittleClick(TextSkittle textSkittle) {

        switch (textSkittle.getPosition()) {
            case 3:
                Toast.makeText(this, "Skittle 3", Toast.LENGTH_LONG).show();
        }
    }
}
