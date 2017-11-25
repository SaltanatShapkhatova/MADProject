package com.example.s_shaphatova.examprep;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;

/**
 * Created by s_shaphatova on 16.11.2017.
 */

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder>{

    List<FolderInfo> data;
    private Context context;

    public MyAdapter(Context context, List<FolderInfo> data) {
        this.context = context;
        this.data = data;
    }

    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.folders_layout, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final FolderInfo current = data.get(position);
        holder.text.setText(current.title);
        holder.img.setImageResource(current.iconId);

        holder.text.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                removeItemDialog(current);
                return true;
            }
        });

        holder.text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void removeItemDialog(final FolderInfo current) {
        final AlertDialog.Builder builder_delete = new AlertDialog.Builder(context)
            .setTitle("Delete a folder")
            .setMessage("Are you sure?")
            .setCancelable(true)
            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        int position = data.indexOf(current);
                        if(position >=0 && position < data.size()){
                            Toast.makeText(context, "removeitem called"+position,Toast.LENGTH_LONG).show();
                            data.remove(position);
                            notifyItemRemoved(position);
                        }
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog_delete = builder_delete.create();
        dialog_delete.show();
    }

    public void add(FolderInfo f) {
        data.add(f);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
        TextView text;
        ImageView img;

        public MyViewHolder(View itemView) {
            super(itemView);
            text = (TextView)itemView.findViewById(R.id.folderNmTxtView);
            img = (ImageView)itemView.findViewById(R.id.iconImg);
        }
    }
}
