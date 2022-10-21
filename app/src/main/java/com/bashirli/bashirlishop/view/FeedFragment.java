package com.bashirli.bashirlishop.view;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bashirli.bashirlishop.R;
import com.bashirli.bashirlishop.adapter.Adapter;
import com.bashirli.bashirlishop.databinding.RecyclerBinding;
import com.bashirli.bashirlishop.model.Model;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Map;

public class FeedFragment extends Fragment {

FirebaseAuth auth;
FirebaseFirestore firestore;
Adapter adapter;
ArrayList<Model> arrayList;
RecyclerView recyclerView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_feed, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    recyclerView=view.findViewById(R.id.recyclerView);
        auth=FirebaseAuth.getInstance();
        firestore=FirebaseFirestore.getInstance();

        arrayList=new ArrayList<>();

 getData();
recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
adapter=new Adapter(arrayList);
recyclerView.setAdapter(adapter);
    }

    public void getData(){
        CollectionReference collectionReference=firestore.collection("Object");
        collectionReference.orderBy("date", Query.Direction.DESCENDING).addSnapshotListener(new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null){
                    Toast.makeText(requireContext(), error.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
                if(value!=null){
                    for(DocumentSnapshot snapshot:value.getDocuments()){
                        Map<String,Object> data=snapshot.getData();
                        String name=(String)data.get("name");
                        String price=(String)data.get("price");
                        String image=(String)data.get("image");
                        String about=(String)data.get("about");

                        Model model=new Model(price,name,about,image);
                        arrayList.add(model);
                    }
                    adapter.notifyDataSetChanged();
                }

            }
        });

    }


}