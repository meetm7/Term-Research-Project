package com.example.voyavue.ui.home;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.voyavue.CustomPosterAdapter;
import com.example.voyavue.NewPostActivity;
import com.example.voyavue.R;
import com.example.voyavue.models.Post;

import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.R)
public class HomeFragment extends Fragment {

    private HomeViewModel homeViewModel;
    private RecyclerView recyclerView;

    private CustomPosterAdapter.RecyclerViewClickListener mListner;

    private final List<Post> allPosts = new ArrayList();

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        setOnClickListner();

        final CustomPosterAdapter customPosterAdapter = new CustomPosterAdapter(allPosts, mListner);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(root.getContext());

        recyclerView = root.findViewById(R.id.recyclerViewPosts);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(customPosterAdapter);

        homeViewModel.getPosts().observe(getViewLifecycleOwner(), posts -> customPosterAdapter.ChangeData(posts));

        return root;
    }

    @Override
    public void onResume() {
        super.onResume();
        homeViewModel.fecthPosts();
    }

    private void setOnClickListner() {
        mListner = (v, id) -> {
            Intent i = new Intent(v.getContext(), NewPostActivity.class);
            i.putExtra("postId", id);
            i.putExtra("isEditable", true);

            startActivity(i);
        };
    }
}