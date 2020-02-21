package com.example.impiled_students;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;


public class HomeFragment extends Fragment {
    RecyclerView recyclerView;
    DatabaseReference databaseReference;
    Query query;
    FirebaseRecyclerAdapter<BulletinModel, ViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<BulletinModel> options;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.home_fragment,container,false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        databaseReference = FirebaseDatabase.getInstance().getReference( "announcement");
        query = databaseReference.orderByKey();
        showData();

        return rootview;

    }

    public void showData(){
        options = new FirebaseRecyclerOptions.Builder<BulletinModel>().setQuery(query, BulletinModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<BulletinModel, ViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull ViewHolder viewHolder, int i, @NonNull BulletinModel model) {
                viewHolder.setDetails(model.getAnnoun_date(), model.getAnnoun_name(), model.getAnnoun_what());
            }

            @NonNull
            @Override
            public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.bulletin_card, parent,false);
                ViewHolder viewHolder = new ViewHolder(itemView);
                return viewHolder;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{
        View mView;

        public ViewHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setDetails(String announ_date, String announ_name, String announ_what){
            TextView date = (TextView) mView.findViewById(R.id.date);
            TextView name = (TextView) mView.findViewById(R.id.name);
            TextView what = (TextView) mView.findViewById(R.id.what);

            date.setText(announ_date);
            name.setText(announ_name);
            what.setText(announ_what);
        }

    }
}
