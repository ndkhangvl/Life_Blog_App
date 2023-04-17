package com.example.bloglife;

import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.icu.text.IDNA;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

    public class BlogAdapter extends RecyclerView.Adapter<BlogAdapter.MyViewHolder> {

        Context context;

        ArrayList<InfoBlog> list;

        public BlogAdapter(Context context, ArrayList<InfoBlog> list) {
            this.context = context;
            this.list = list;
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.item_card, parent, false);
            return new MyViewHolder(v);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

            InfoBlog infoBlog = list.get(position);
            holder.blogUname.setText("@"+infoBlog.getUname());
            holder.blogTitle.setText(infoBlog.getTitle());
            holder.blogContent.setText(infoBlog.getContent());
            holder.blogTime.setText(infoBlog.getBdate());
            //holder.blogMail.setText(infoBlog.getIuser());

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    onClickGoToDetail(infoBlog);
                }
            });

            holder.itemView.setOnCreateContextMenuListener(new View.OnCreateContextMenuListener() {
                @Override
                public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
                    contextMenu.setHeaderTitle("Select action");
                    contextMenu.add(0, R.id.update, 0, "Update").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
                        @Override
                        public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                            if(infoBlog.getId() == null) {
                                Toast.makeText(context, "You haven't permission to action", Toast.LENGTH_SHORT).show();
                            } else {
                                Intent i = new Intent(context, UpdateBlog.class);
                                i.putExtra("uname", infoBlog.getUname());
                                i.putExtra("title", infoBlog.getTitle());
                                i.putExtra("content", infoBlog.getContent());
                                i.putExtra("user", infoBlog.getIuser());
                                i.putExtra("id", infoBlog.getId());
                                context.startActivity(i);
                            }
                            return false;
                        }
                    });
                    contextMenu.add(0, R.id.delete, 0, "Delete").setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {

                        @Override
                        public boolean onMenuItemClick(@NonNull MenuItem menuItem) {
                            if(infoBlog.getId() == null){
                                Toast.makeText(context, "You haven't permission to action", Toast.LENGTH_SHORT).show();
                            } else {
                                DatabaseReference databaseReference =
                                    FirebaseDatabase.getInstance().getReference().child("InfoBlog").child(infoBlog.getId());
                            databaseReference.removeValue();
                                list.remove(holder.getAdapterPosition());
                            notifyDataSetChanged();
                            }
                            System.out.println("Print ID Blog: " + infoBlog.getId());
                            System.out.println("Get Email" + infoBlog.getIuser());
                            return false;
                        }
                    });
                }
            });

        }

        //Test DetailBlog
        private void onClickGoToDetail(InfoBlog infoBlog) {
            Intent i = new Intent(context, DetailBlog.class);
            i.putExtra("title", infoBlog.getTitle());
            i.putExtra("content", infoBlog.getContent());
            i.putExtra("user", infoBlog.getIuser());
            i.putExtra("id", infoBlog.getId());
            context.startActivity(i);
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class MyViewHolder extends RecyclerView.ViewHolder {

            TextView blogTitle, blogContent, blogTime, blogMail, blogUname;
            //LinearLayout cardView;
            public MyViewHolder(@NonNull View itemView) {
                super(itemView);
                //cardView = itemView.findViewById(R.id.layoutItem);
                blogUname = itemView.findViewById(R.id.txtUname);
                blogTitle = itemView.findViewById(R.id.txtTittle);
                blogContent = itemView.findViewById(R.id.txtContent);
                blogTime = itemView.findViewById(R.id.txtTime);
                //blogMail = itemView.findViewById(R.id.txtEmail);
            }
        }
    }

