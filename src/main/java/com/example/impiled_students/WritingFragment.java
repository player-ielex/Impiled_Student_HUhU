package com.example.impiled_students;

import android.content.Context;
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
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

public class WritingFragment extends Fragment {
    RecyclerView essayView, poemrecycler, shortrecycler, jokesrecycler;
    DatabaseReference writings, features, poems, essays, shorts, jokes;
    FirebaseRecyclerAdapter<WritingModel, WriteHolder> featureRecyclerAdapter, poemRecyclerAdapter, essayRecyclerAdapter, shortRecyclerAdapter, jokeRecyclerAdapter;
    FirebaseRecyclerOptions<WritingModel> options, poemtions, essaytions, shortions, joketions;
    LinearLayoutManager Horizontal, Horizontal2, Horizontal3, Horizontal4, Horizontal5;
    Query feature, poem, essay, story, joke;
    @Nullable
    @Override
    public View onCreateView(@Nullable LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState){
        View rootView = inflater.inflate(R.layout.writing_fragment,container,false);
        writings = FirebaseDatabase.getInstance().getReference("journalism").child("journ_post");


        //Initializing RecyclerView
        essayView = (RecyclerView) rootView.findViewById(R.id.EssayView);

        essayView.setHasFixedSize(true);

        //Initializing Database Reference
        features = writings.child("Feature");
        poems = writings.child("Poem");
        essays = writings.child("Essay");
        shorts = writings.child("ShortStory");
        jokes = writings.child("Joke");

        //Initializing Queries
        feature = features.limitToFirst(10);
        poem = poems.limitToFirst(10);
        essay = essays.limitToFirst(10);
        story = shorts.limitToFirst(10);
        joke = jokes.limitToFirst(10);


        //LinearLayoutManager
        Horizontal = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        Horizontal2 =  new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        Horizontal3 =  new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        Horizontal4 =  new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        Horizontal5 =  new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);

        //Calling Function
        showEssaydata();

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if(essayRecyclerAdapter != null){
            essayRecyclerAdapter.startListening();
        }
    }

    public void showEssaydata(){
        essaytions = new FirebaseRecyclerOptions.Builder<WritingModel>().setQuery(essay, WritingModel.class).build();
        essayRecyclerAdapter =  new FirebaseRecyclerAdapter<WritingModel, WriteHolder>(essaytions) {
            @Override
            protected void onBindViewHolder(@NonNull WriteHolder viewHolder, int i, @NonNull WritingModel model) {
                viewHolder.setDetails(getActivity(), model.journ_title, model.journ_content, model.journ_photo);
            }

            @NonNull
            @Override
            public WriteHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.writing_card, parent, false);
                WriteHolder writeHolder = new WriteHolder(itemView);
                return writeHolder;
            }
        };
        essayView.setLayoutManager(Horizontal);
        essayRecyclerAdapter.startListening();
        essayView.setAdapter(essayRecyclerAdapter);
    }

    public void showFeaturesdata(){}

    public void showPoemdata(){}

    public void showStorydata(){}

    public void showJokesdata(){}

    public static class WriteHolder extends RecyclerView.ViewHolder{
        View mView;

        public WriteHolder(View itemView){
            super(itemView);
            mView = itemView;
        }

        public void setDetails(final Context ctx, String journ_title, String journ_content, final String journ_photo){
            TextView title = (TextView) mView.findViewById(R.id.title);
            TextView content = (TextView) mView.findViewById(R.id.content);
            final ImageView what = (ImageView) mView.findViewById(R.id.photo);

            title.setText(journ_title);
            content.setText(journ_content);
            Picasso.with(ctx).load(journ_photo).networkPolicy(NetworkPolicy.OFFLINE).into(what, new Callback() {
                @Override
                public void onSuccess() {

                }

                @Override
                public void onError() {
                    Picasso.with(ctx).load(journ_photo).into(what);
                }
            });
        }

    }
}
