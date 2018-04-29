package com.pdfdocumentreader;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.pdfdocumentreader.utils.GridSpacingItemDecoration;

import java.io.File;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements PdfListAdapter.AdapterListener {
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        File sdcardObj = new File(Environment.getExternalStorageDirectory().getAbsolutePath());
        listFiles(sdcardObj);
        prepareList();
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

    /****PREPARE DATA****/
    ArrayList<FileModel> fileModels = new ArrayList<>();
    PdfListAdapter adapter;

    //private Animator spruceAnimator;
    private void prepareList() {
        GridLayoutManager mLayoutManager = new GridLayoutManager(this, GridSpacingItemDecoration.COLUMNS);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(MainActivity.this,
                GridSpacingItemDecoration.COLUMNS, GridSpacingItemDecoration.PADDING, true));
        recyclerView.setItemAnimator(new DefaultItemAnimator());

        adapter = new PdfListAdapter(MainActivity.this, fileModels, this);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onRowClicked(int position) {
        Intent intent = new Intent(MainActivity.this, PdfViewerActivity.class);
        intent.putExtra("FileModel", fileModels.get(position));
        startActivity(intent);
    }

    @Override
    public void onRowLongClicked(int position) {

    }
    /****PREPARE DATA****/

    private void listFiles(File sdcard) {
        if (sdcard.isDirectory()) {
            File[] files = sdcard.listFiles();

            try {
                for (File f : files) {
                    if (!f.isDirectory()) {
                        String fileName = f.getName();
                        if (fileName.endsWith(".pdf") || fileName.endsWith(".PDF")) {
                            // Log.d(" FILES",f.getName());
                            fileModels.add(new FileModel(fileName, f.getAbsolutePath(), getFileSize(f)));
                        }
                    } else {
                        this.listFiles(f);
                    }
                }
            } catch (Exception e) {
                // TODO Auto-generated catch block
                //e.printStackTrace();
            }
        }
    }

    String getFileSize(File file) {
        // Get length of file in bytes
        long fileSizeInBytes = file.length();
// Convert the bytes to Kilobytes (1 KB = 1024 Bytes)
        long fileSizeInKB = fileSizeInBytes / 1024;
// Convert the KB to MegaBytes (1 MB = 1024 KBytes)
        long fileSizeInMB = fileSizeInKB / 1024;
        return (fileSizeInMB > 1) ? "" + fileSizeInMB + " MB" : "" + fileSizeInKB + " KB";
    }
}
