package com.example.bakelsfirebase;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.FirebaseDatabase;
import com.orhanobut.dialogplus.DialogPlus;
import com.orhanobut.dialogplus.ViewHolder;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

public class ProductAdapter extends FirebaseRecyclerAdapter<ProductModel, ProductAdapter.ProductViewHolder> {


    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public ProductAdapter(@NonNull FirebaseRecyclerOptions<ProductModel> options) {
        super(options);
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_card, parent,false);

        return new ProductViewHolder(view);
    }

    @Override
    protected void onBindViewHolder(@NonNull ProductViewHolder holder, int position, @NonNull ProductModel model) {
        holder.name.setText(model.getName());
        holder.brand.setText(model.getBrand());
        holder.category.setText(model.getCategory());
        holder.stock.setText("En stock: "+model.getStock()+"");
        holder.price.setText(model.getPrice()+"");

        Glide.with(holder.img.getContext())
                .load(model.getImagen())
                .placeholder(R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.img);

        holder.edit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final DialogPlus dialogPlus = DialogPlus.newDialog(holder.img.getContext())
                        .setContentHolder(new ViewHolder(R.layout.update_popup))
                        .setExpanded(true, 1400)
                        .create();

                View viewUpdate = dialogPlus.getHolderView();
                EditText name = viewUpdate.findViewById(R.id.name_edit_txt);
                EditText brand = viewUpdate.findViewById(R.id.brand_edit_txt);
                EditText category = viewUpdate.findViewById(R.id.category_edit_txt);
                EditText stock = viewUpdate.findViewById(R.id.stock_edit_txt);
                EditText price = viewUpdate.findViewById(R.id.price_edit_txt);
                EditText imagen = viewUpdate.findViewById(R.id.imagen_edit_txt);

                name.setText(model.getName());
                brand.setText(model.getBrand());
                category.setText(model.getCategory());
                stock.setText(model.getStock()+"");
                price.setText(model.getPrice()+"");
                imagen.setText(model.getImagen());

                Button btnUpdate = (Button) viewUpdate.findViewById(R.id.update_btn);
                int newpos = holder.getAdapterPosition();
                btnUpdate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Map<String,Object> map = new HashMap<>();
                        map.put("name", name.getText().toString());
                        map.put("brand", brand.getText().toString());
                        map.put("category", category.getText().toString());
                        map.put("stock", Integer.parseInt(stock.getText().toString()));
                        map.put("price", Double.parseDouble(price.getText().toString()));
                        map.put("imagen", imagen.getText().toString());

                        FirebaseDatabase.getInstance().getReference().child("Data")
                                .child(getRef(newpos).getKey()).updateChildren(map)
                                .addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(holder.name.getContext(), "Datos del producto actualizados exitosamente.", Toast.LENGTH_SHORT).show();
                                        dialogPlus.dismiss();
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Toast.makeText(holder.name.getContext(), "Error al actualizar data.", Toast.LENGTH_SHORT).show();
                                    }
                                });
                    }
                });

//                btnUpdate.setOnClickListener(new View.OnClickListener() {
//                    @Override
//                    public void onClick(View view) {
//                        Map<String,Object> map = new HashMap<>();
//                        map.put("name", name.getText().toString());
//                        map.put("brand", brand.getText().toString());
//                        map.put("category", category.getText().toString());
//                        map.put("stock", stock.getText().toString());
//                        map.put("price", price.getText().toString());
//                        map.put("imagen", imagen.getText().toString());
//
//                        FirebaseDatabase.getInstance().getReference().child("Data")
//                                .child(getRef(position).getKey()).updateChildren(map)
//                                .addOnSuccessListener(new OnSuccessListener<Void>() {
//                                    @Override
//                                    public void onSuccess(Void unused) {
//                                        Toast.makeText(holder.name.getContext(), "Datos del producto actualizados exitosamente.", Toast.LENGTH_SHORT).show();
//                                        dialogPlus.dismiss();
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Toast.makeText(holder.name.getContext(), "Error al actualizar data.", Toast.LENGTH_SHORT).show();
//                                    }
//                                });
//
//
//                    }
//                });

                dialogPlus.show();

            }
        });

    }


    class ProductViewHolder extends RecyclerView.ViewHolder {
        CircleImageView img;
        TextView name, brand, category, stock, price;
        Button edit_btn;

        public ProductViewHolder(@NonNull View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.circle_img_view);
            name = itemView.findViewById(R.id.name_text);
            brand = itemView.findViewById(R.id.brand_text);
            category = itemView.findViewById(R.id.category_text);
            stock = itemView.findViewById(R.id.stock_text);
            price = itemView.findViewById(R.id.price_text);

            edit_btn = (Button) itemView.findViewById(R.id.edit_btn);
        }
    }
}
