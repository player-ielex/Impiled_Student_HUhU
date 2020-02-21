package com.example.impiled_students;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class RewardsFragment extends Fragment {
    private FirebaseAuth Auth;
    private FirebaseAuth.AuthStateListener mAuthListener;
    DatabaseReference mDatabase, UserDatabase;
    RecyclerView recyclerView;
    Query query;
    FirebaseRecyclerAdapter<RewardsModel, RewardsViewHolder> firebaseRecyclerAdapter;
    FirebaseRecyclerOptions<RewardsModel> options;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootview = inflater.inflate(R.layout.rewards_fragment,container,false);
        recyclerView = (RecyclerView) rootview.findViewById(R.id.recyclerview);
        mDatabase = FirebaseDatabase.getInstance().getReference("reward");
        mDatabase.keepSynced(true);
        query = mDatabase.orderByKey(); //Edit this code when you put the time itself

        UserDatabase = FirebaseDatabase.getInstance().getReference().child("students");
        UserDatabase.keepSynced(true);
        recyclerView.setHasFixedSize(true);
        showData();
        return rootview;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(firebaseRecyclerAdapter != null){
            firebaseRecyclerAdapter.startListening();
        }
    }

    private void showData(){

        options = new FirebaseRecyclerOptions.Builder<RewardsModel>().setQuery(query, RewardsModel.class).build();

        firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<RewardsModel, RewardsViewHolder>(options) {
            @Override
            protected void onBindViewHolder(@NonNull RewardsViewHolder viewHolder, int i, @NonNull RewardsModel model) {
                final String rewards_key = getRef(i).getKey();
                viewHolder.setRewards_name(model.getRewards_name());
                viewHolder.setRewards_description(model.getRewards_description());
                viewHolder.setRewards_photo(getActivity(), model.getRewards_photo());
                viewHolder.mView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }

            @NonNull
            @Override
            public RewardsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.rewards_card, parent,false);
                RewardsViewHolder rewardsViewHolder = new RewardsViewHolder(itemView);
                return rewardsViewHolder;
            }
        };
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        firebaseRecyclerAdapter.startListening();
        recyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    public static class RewardsViewHolder extends RecyclerView.ViewHolder{
        View mView;
        public RewardsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setRewards_name(String name){
            TextView ProdName = (TextView) mView.findViewById(R.id.product_name);
            ProdName.setText(name);
        }

        public void setRewards_description(String desc){
            TextView DesName = (TextView) mView.findViewById(R.id.product_desc);
            DesName.setText(desc);
        }

        public void setRewards_photo(final Context ctx, final String image){
            final ImageView rewards_photo = (ImageView) mView.findViewById(R.id.product_photo);
            Picasso.with(ctx).load(image).networkPolicy(NetworkPolicy.OFFLINE).into(rewards_photo, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(image).into(rewards_photo);
                }
            });
        }
    }
}
