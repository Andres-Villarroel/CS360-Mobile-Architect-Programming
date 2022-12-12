package com.zybooks.myproject;

import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import org.w3c.dom.Text;

import java.util.List;


/*
I had quite a bit of trouble with implementing a recyclerview but i figured that it would be best for
this kind of app.
That being said, I mainly relied on one particular video for the general framework of the recyclerview
in this project.

Video from youtube: https://youtu.be/LQmGU3UCOPQ

same video from google drive in case the link above does not work:
https://drive.google.com/file/d/1Z6WYBiOzPhVdpZb2oEXI0ZqqOxAiy96U/view?usp=sharing

 */
public class ItemInventoryAdapter extends RecyclerView.Adapter<InventoryViewHolder> {


    List<InventoryDataframe> itemsList;
    public ItemInventoryAdapter(List<InventoryDataframe> itemsList) {
        this.itemsList = itemsList;
    }

    @NonNull
    @Override
    public InventoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.inventory_items_row, parent,false);
        return new InventoryViewHolder(view).linkAdapter(this);
    }

    //this is where the InventoryDataframe's itemName, itemQuantity and itemId are initialized
    @Override
    public void onBindViewHolder(@NonNull InventoryViewHolder holder, int position) {
        holder.itemNameTextView.setText(itemsList.get(position).getItemName());
        holder.itemQuantityTextView.setText(itemsList.get(position).getItemQuantity());
        //no longer needed as the database handler should deal with the item id
        //holder.itemIdTextView.setText(itemsList.get(position).getItemId());
    }

    @Override
    public int getItemCount() {
        return itemsList.size();
    }
}

class InventoryViewHolder extends RecyclerView.ViewHolder{

    //initialize UI elements here such as textViews
    TextView itemNameTextView;
    TextView itemQuantityTextView;

    private ItemInventoryAdapter adapter;

    public InventoryViewHolder(@NonNull View itemView) {
        super(itemView);

        itemNameTextView = (TextView)itemView.findViewById(R.id.itemNameTextView);
        itemQuantityTextView = (TextView)itemView.findViewById(R.id.quantityTextView);
        //itemIdTextView = (TextView)itemView.findViewById(R.id.<error, no such TextView exists in inventory_items_row.xml>);

        //delete button callback
        itemView.findViewById(R.id.deleteItemBtn).setOnClickListener(view -> {
            adapter.itemsList.remove(getAdapterPosition());
            adapter.notifyItemRemoved(getAdapterPosition());
        });

        //edit item button callback
        itemView.findViewById(R.id.editQuantityBtn).setOnClickListener(view -> {
            DatabaseHandler db = new DatabaseHandler(itemView.getContext());

            //creating custom dialog
            AlertDialog.Builder builder = new AlertDialog.Builder(view.getRootView().getContext());
            builder.setTitle("Enter quantity");

            //custom layout
            final View customLayout = LayoutInflater.from(view.getRootView().getContext()).inflate(R.layout.custom_dialog_layout, null);
            builder.setView(customLayout);

            //adding button
            builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    EditText dialogEditText = customLayout.findViewById(R.id.dialogEditText);
                    String dialogText = dialogEditText.getText().toString();

                    //do something with dialogEditText here
                    adapter.itemsList.get(getAdapterPosition()).setItemQuantity(dialogText);    //updating the item quantity datafield in list
                    int tempId = Integer.parseInt(adapter.itemsList.get(getAdapterPosition()).getItemId());
                    int tempQuantity = Integer.parseInt(adapter.itemsList.get(getAdapterPosition()).getItemQuantity());
                    db.updateItem(tempId, tempQuantity);
                    adapter.notifyItemChanged(getAdapterPosition());
                    Log.i("DialogData[98]", "current item quantity changed to: " + adapter.itemsList.get(getAdapterPosition()).getItemQuantity());
                }
            });
            AlertDialog dialog = builder.create();
            dialog.show();
            //adapter.itemsList.get(getAdapterPosition()).setItemQuantity();
        });

    }

    public InventoryViewHolder linkAdapter(ItemInventoryAdapter adapter){
        this.adapter = adapter;
        return this;
    }
}
