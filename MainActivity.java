package com.example.s_shaphatova.examprep;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Build;
import android.os.Environment;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageButton add;
    public  String folderName;
    public ArrayList<String> folderNames;
    RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<FolderInfo> data;
    View view;
    RelativeLayout rl;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add = (ImageButton) findViewById(R.id.add);
        recyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        folderNames = new ArrayList<>();
        data = new ArrayList<>();
        rl = (RelativeLayout)findViewById(R.id.layout);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new MyAdapter(this, data);
        recyclerView.setAdapter(adapter);
        File[] files = this.getFilesDir().listFiles();
        if(files.length > 0)
        {
            for (File file:files) {
                String fileName = file.getName();
                adapter.add(new FolderInfo(fileName));
                adapter.notifyDataSetChanged();
            }
        }
        else
        {
            RelativeLayout.LayoutParams lparams = new RelativeLayout.LayoutParams(
                    RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
            TextView tv = new TextView(this);
            tv.setText("Here your folders for different subjects will be shown");
            tv.setLayoutParams(lparams);
            rl.addView(tv);
        }
    }

    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                LayoutInflater inflater = this.getLayoutInflater();
                view = inflater.inflate(R.layout.add_dialog, null);
                builder.setView(view)
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                EditText et = (EditText) view.findViewById(R.id.name);
                                folderName = et.getText().toString();
                                adapter.add(new FolderInfo(folderName));
                                adapter.notifyDataSetChanged();
                                createNewFile(folderName);
                            }
                        })
                        .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                AlertDialog dialog_add = builder.create();
                dialog_add.show();
                break;
        }
    }

    private void createNewFile(String folderName) {
        File file = getBaseContext().getFileStreamPath(folderName);
        if(!file.exists()){
            File newFile = new File(this.getFilesDir(), folderName);
        }
    }

}
