package com.example.rezan.ui.adapters;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.rezan.data.db.Product;
import com.example.rezan.R;
import com.example.rezan.ui.fragments.shop.ShopFragment;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;


public class ProductsAdapter extends FirebaseRecyclerAdapter<Product, ProductsAdapter.MyViewHolder> {


    public ProductsAdapter(@NonNull FirebaseRecyclerOptions<Product> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MyViewHolder holder, int position, @NonNull Product product) {
        holder.name.setText(product.getName());
        holder.cost.setText(product.getCost() + " рублей");
        Glide.with(holder.image.getContext()).load(product.getPhoto()).into(holder.image);

        holder.constraintProduct.setOnClickListener(view -> {
            Bundle bundle = new Bundle();
            bundle.putSerializable("name", product.getName());
            bundle.putSerializable("photo", product.getPhoto());
            bundle.putSerializable("cost", product.getCost());
            Navigation.findNavController(ShopFragment.binding.getRoot()).navigate(R.id.action_navigation_shop_to_productFragment, bundle);
        });

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.shop_recycler_item, parent,false);
        return new MyViewHolder(view);
    }

    class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView image;
        TextView name, cost;
        ConstraintLayout constraintProduct;
        public MyViewHolder(@NonNull View itemView)
        {
            super(itemView);
            image = itemView.findViewById(R.id.productImage);
            name = itemView.findViewById(R.id.productName);
            cost = itemView.findViewById(R.id.productCost);
            constraintProduct = itemView.findViewById(R.id.constraintProduct);
        }
    }

}
