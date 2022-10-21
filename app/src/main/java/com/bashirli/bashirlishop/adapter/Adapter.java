package com.bashirli.bashirlishop.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bashirli.bashirlishop.databinding.RecyclerBinding;
import com.bashirli.bashirlishop.model.Model;
import com.bashirli.bashirlishop.view.FeedFragmentDirections;
import com.squareup.picasso.Picasso;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;

public class Adapter extends RecyclerView.Adapter<Adapter.AdapterHolder> {
    ArrayList<Model> arrayList;

    public Adapter(ArrayList<Model> arrayList) {
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public AdapterHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecyclerBinding recyclerBinding=RecyclerBinding.inflate(LayoutInflater.from(parent.getContext()),parent,false);
        return new AdapterHolder(recyclerBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull AdapterHolder holder, int position) {
    holder.recyclerBinding.textView5.setText(arrayList.get(position).getName());
    holder.recyclerBinding.textView6.setText("Qiymət : "+Double.parseDouble(arrayList.get(position).getPrice())+" AZN");
        Picasso.get().load(arrayList.get(position).getImage()).into(holder.recyclerBinding.imageView2);
        holder.recyclerBinding.button4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FeedFragmentDirections.ActionFeedFragmentToShopFragment action=FeedFragmentDirections.actionFeedFragmentToShopFragment();
                action.setInfo("old");
                action.setAbout(arrayList.get(position).getAbout());
                action.setImage(arrayList.get(position).getImage());
                action.setPrice(arrayList.get(position).getPrice());
                action.setName(arrayList.get(position).getName());
                Navigation.findNavController(view).navigate(action);
            }
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class AdapterHolder extends RecyclerView.ViewHolder{
        private RecyclerBinding recyclerBinding;
        public AdapterHolder(@NonNull RecyclerBinding recyclerBinding) {
            super(recyclerBinding.getRoot());
            this.recyclerBinding=recyclerBinding;
        }
    }
}
