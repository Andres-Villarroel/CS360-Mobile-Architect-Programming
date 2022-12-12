package com.zybooks.myproject;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.LinkedList;
import java.util.List;

import android.widget.Toast;

public class SecondDisplayActivityScreen extends AppCompatActivity {

    EditText addNewItemName;
    EditText addNewItemQuantity;
    Button addNewItemBtn;
    private final int REQUEST_WRITE_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_display_screen);


        DatabaseHandler db = new DatabaseHandler(SecondDisplayActivityScreen.this);

        //to be used when adding a new item in the inventory and to check if the
        // edittext fields are empty to enable or disable addNewItemBtn
        addNewItemName = (EditText) findViewById(R.id.itemNamePromptEditText);
        addNewItemQuantity = (EditText) findViewById(R.id.itemQuantityPromptEditText);
        addNewItemBtn = (Button) findViewById(R.id.addNewItemBtn);

        //checking if the editText fields for the item name are empty or not
 /*
        addNewItemName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // N/A
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String userEditText = addNewItemName.getText().toString();

                if(userEditText.length() <= 0 || userEditText == null){
                    Log.i("Tag","First text field is empty");
                    addNewItemBtn.setEnabled(false);
                }
                else{
                    Log.i("Tag","First text field is not empty");
                    addNewItemBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // N/A
            }
        });

        //checking if the editText fields for the item quantity are empty or not
        addNewItemQuantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // N/A
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                String userEditText = addNewItemQuantity.getText().toString();

                if(userEditText.length() <= 0 || userEditText == null){
                    addNewItemBtn.setEnabled(false);
                }
                else{
                    addNewItemBtn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
                // N/A
            }
        });
*/


        //prefill the array with some items to be placed in the inventory
        List<InventoryDataframe> items = new LinkedList<>();
        /*InventoryDataframe dummyFiller1 = new InventoryDataframe("dummy Dog toy", "11");
        InventoryDataframe dummyFiller2 = new InventoryDataframe("dummy Parrot toy", "19");
        items.add(dummyFiller1);
        items.add(dummyFiller2);

        db.addItem(items.get(0).getItemName(), items.get(0).getItemQuantity());
        db.addItem(items.get(1).getItemName(), items.get(1).getItemQuantity());
        Log.i("onCreate[106]", "itemName: " + items.get(0).getItemName());
        Log.i("onCreate[107]", "itemName: " + items.get(1).getItemName());
*/
        //loading all rows from table to items list

        Cursor cursor = db.getAllRows();

        Log.v("Cursor Object", DatabaseUtils.dumpCursorToString(cursor));
        if(cursor.moveToFirst()){
            do {
                //storing the values of cursor into variables to be used in InventoryDataframe holder
                long itemId = cursor.getLong(0);
                String itemName = cursor.getString(1);
                String itemQuant = cursor.getString(2);

                //initializing temporary InventoryDataframe holder
                String stringItemId = String.valueOf(itemId);
                InventoryDataframe tempItemHolder = new InventoryDataframe(itemName, itemQuant);
                tempItemHolder.setItemId(stringItemId);

                //adding tempItemHolder to items list
                items.add(tempItemHolder);
            } while (cursor.moveToNext());
        }

        //Recyclerview required setup
        RecyclerView recyclerView = findViewById(R.id.recyclerViewInventory);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        ItemInventoryAdapter adapter = new ItemInventoryAdapter(items);
        recyclerView.setAdapter(adapter);

        //callback function for the addNewItemBtn
        findViewById(R.id.addNewItemBtn).setOnClickListener(view -> {

            String userInputNewItemName = addNewItemName.getText().toString();
            String userInputNewItemQuantity = addNewItemQuantity.getText().toString();

            long returnedId = db.addItem(userInputNewItemName, userInputNewItemQuantity);
            Log.i("addNewItemBtn Callback[124]", "userInputNewItemName: " + userInputNewItemName);
            Log.i("addNewItemBtn Callback[125]", "userInputNewItemQuantity: " + userInputNewItemQuantity);
            InventoryDataframe newItem = new InventoryDataframe(userInputNewItemName, userInputNewItemQuantity);
            //use a query to get the id and save it to newItem object
            newItem.setItemId(String.valueOf(returnedId));
            items.add(newItem);
            //notifying adapter of new item addition
            adapter.notifyItemInserted(items.size() - 1); //position where item was added

            //clear edittext fields
            addNewItemName.getText().clear();
            addNewItemQuantity.getText().clear();
        });
        db.close();
    }

    //button to test functionality of DatabaseHandler class, currently set to be the callback of the
    // notification button in activity_second_display_screen.xml
    public void notificationButtonCallback(View view) {
        hasNotificationPermissions();
    }

    //permission check
    private boolean hasNotificationPermissions(){
        String writeNotificationPermission = Manifest.permission.SEND_SMS;
        if(ContextCompat.checkSelfPermission(this, writeNotificationPermission)
                != PackageManager.PERMISSION_GRANTED){
            if(ActivityCompat.shouldShowRequestPermissionRationale(this,writeNotificationPermission)){
                ActivityCompat.requestPermissions(this, new String[]{writeNotificationPermission}, REQUEST_WRITE_CODE);
            }
            else{
                ActivityCompat.requestPermissions(this, new String[]{writeNotificationPermission}, REQUEST_WRITE_CODE);
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_WRITE_CODE: {
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    //permission granted
                    Toast.makeText(this, "Successfully enabled SMS notifications", Toast.LENGTH_SHORT).show();
                } else {
                    //Permission denied
                    Toast.makeText(this, "No SMS notifications will be sent", Toast.LENGTH_SHORT).show();
                }
                return;
            }
        }
    }

}