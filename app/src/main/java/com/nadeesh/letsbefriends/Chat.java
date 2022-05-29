package com.nadeesh.letsbefriends;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.nadeesh.letsbefriends.data_adapters.messagesAdapter;
import com.nadeesh.letsbefriends.dbHelpers.messagesHelper;
import com.nadeesh.letsbefriends.models.messages_model;

import java.io.ByteArrayOutputStream;
import java.text.ParseException;
import java.util.ArrayList;

public class Chat extends AppCompatActivity {
    
    EditText messageMessage;
    Button storeComment;
    private messagesHelper DB;

    ListView messageList;
    ArrayList<messages_model> arrayList;
    messagesAdapter adapter;

    TextView tempHolder;

    boolean isSelected = false;
    ArrayList<Integer> selectedItems = new ArrayList<>();
    ImageView message_image;
    String postCode;
    String _email;
    private static final int GALLERY_REQUEST_CODE = 123;

    Boolean isattached;

    SharedPreferences sh;
    FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        postCode = getIntent().getStringExtra("chat_room");


        sh = getSharedPreferences("MySharedPref", MODE_PRIVATE);
        _email = sh.getString("username", "");
        
        messageMessage = findViewById(R.id.messageMessage);
        storeComment = findViewById(R.id.storeComment);
        messageList = findViewById(R.id.messageList);

        tempHolder = findViewById(R.id.tempHolder);
        message_image = findViewById(R.id.message_img);
        DB =new messagesHelper(this);

        fab = findViewById(R.id.fab);

        try {
            loadComments(postCode);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        isattached = false;

        fab.setOnClickListener(view -> {
            Intent intent2 = new Intent();
            intent2.setType("image/*");
            intent2.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(Intent.createChooser(intent2, "Pick an image"), GALLERY_REQUEST_CODE);
            message_image.setVisibility(View.VISIBLE);
            isattached = true;
        });

        storeComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String messageBody = messageMessage.getText().toString();
                String btnTitle = storeComment.getText().toString();
                message_image.invalidate();
                BitmapDrawable drawable = (BitmapDrawable) message_image.getDrawable();
                Bitmap post_image_upload = drawable.getBitmap();
                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
                post_image_upload.compress(Bitmap.CompressFormat.PNG, 100, byteArray);


                byte[] img  ={10} ;
                if(isattached){
                    img = byteArray.toByteArray();
                }
                message_image.setVisibility(View.GONE);

                if(!messageBody.equals("")) {

                    if(btnTitle.equals("send")) {

                        boolean messageOperation = DB.newComment(_email, postCode, messageBody,img);
                        message_image.setVisibility(View.GONE);
                        isattached = false;
                        if (messageOperation) {
                            try {
                                loadComments(postCode);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Chat.this, "Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Chat.this, "Commenting Failed", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        String oldComment = tempHolder.getText().toString();

                        boolean messageUpdate = DB.updateComment(postCode,oldComment, messageBody);
                        if (messageUpdate) {
                            try {
                                loadComments(postCode);
                            } catch (ParseException e) {
                                e.printStackTrace();
                            }
                            Toast.makeText(Chat.this, "Update Success", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(Chat.this, "Update Failed", Toast.LENGTH_SHORT).show();
                        }
                    }

                    messageMessage.setText("");

            }else{
                Toast.makeText(Chat.this, "Field Cant be empty!", Toast.LENGTH_SHORT).show();
            }
                
            }
        });
        messageList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                if(isSelected){
                    if(selectedItems.contains(i)){
                        Log.d("remove selected",String.valueOf(i));
                        messageList.getChildAt(i).setBackgroundColor(Color.TRANSPARENT);
                        selectedItems.remove(i);
                        Log.d("array", selectedItems.toString());

                    }else{
                        Log.d("add selected",String.valueOf(i));
                        messageList.getChildAt(i).setBackgroundColor(Color.GRAY);
                        selectedItems.add(i);
                        Log.d("array", selectedItems.toString());
                       // Log.d();
                    }

                }else{
                    Log.d("slected", String.valueOf(i) );

                    messages_model tmp = arrayList.get(i);
                    String _message = tmp.getComment();
                    Log.d("log", _message );
                    String _email = tmp.getEmail();
                    String storedEmail = sh.getString("username", "");
                    AlertDialog alertDialog = new AlertDialog.Builder(Chat.this).create(); //Read Update
                    alertDialog.setTitle("Action");

                    alertDialog.setButton(Dialog.BUTTON_POSITIVE, "Delete", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            boolean Deleted = DB.deleteComment(postCode, _message);

                            if (Deleted) {

                                try {
                                    loadComments(postCode);
                                } catch (ParseException e) {
                                    e.printStackTrace();
                                }
                                Toast.makeText(Chat.this, "Deleted", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(Chat.this, "Delete Failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    alertDialog.setButton(Dialog.BUTTON_NEGATIVE, "Edit", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            storeComment.setText("update");
                            messageMessage.setText(_message);
                            tempHolder.setText(_message);
                        }
                    });
                    alertDialog.setButton(Dialog.BUTTON_NEUTRAL, "Share", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                            sharingIntent.setType("text/plain");
                            sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, _message);
                            sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, _message);
                            startActivity(Intent.createChooser(sharingIntent, "Share via"));
                        }
                    });


                    if (_email.equals(storedEmail)) {
                        alertDialog.show();
                    }
                }

            }

    });


        messageList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {

                    isSelected = true;
                    Log.d("", String.valueOf(i));
                    messageList.getChildAt(i).setBackgroundColor(Color.GRAY);
                    selectedItems.add(i);
                    Log.d("array", selectedItems.toString());
                return true;
            }
        });



    }


    private void loadComments(String postCode) throws ParseException {
        Log.d("", postCode);
        arrayList = DB.getAllData(postCode);
        adapter = new messagesAdapter(this, arrayList);
        messageList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }


    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Context Menu");
        menu.add(0, v.getId(), 0, "Edit");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Share");
    }
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getTitle().equals("Edit"))
        {
            AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
            messages_model c = arrayList.get(menuInfo.position);
            Toast.makeText(this, "Selected String is :-" + c.getComment(), Toast.LENGTH_SHORT).show();
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.message_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;

        switch (item.getItemId()) {
            case R.id.search:
                EditText taskEditText = new EditText(Chat.this);
                AlertDialog dialog2 = new AlertDialog.Builder(Chat.this)
                        .setTitle("Search")
                        .setView(taskEditText)
                        .setPositiveButton("Search", (dialog1, which) -> {
                            Intent intent2;
                            String task = String.valueOf(taskEditText.getText());
                            intent2 = new Intent(getApplicationContext(), Search.class);
                            intent2.putExtra("searched", task);
                            startActivity(intent2);
                        })
                        .setNegativeButton("Cancel", null)
                        .create();
                dialog2.show();
                return true;
            case R.id.delete:
                    if (selectedItems.isEmpty()){
                        Toast.makeText(this, "Please select messages", Toast.LENGTH_SHORT).show();
                    }else {
                        AlertDialog dialog = new AlertDialog.Builder(Chat.this)
                                .setTitle("Delete")
                                .setMessage("Do you want to delete these messages?")
                                .setPositiveButton("Delete", (dialog1, which) -> {

                                    Log.d("size : ", String.valueOf(selectedItems.size()));
                                    for(int j = 0; j< selectedItems.size();j++ ){
                                        messages_model tmp = arrayList.get(j);
                                        String _message = tmp.getComment();

                                        DB.deleteComment(postCode, _message);

                                    }
                                    try {
                                        loadComments(postCode);
                                    } catch (ParseException e) {
                                        e.printStackTrace();
                                    }

                                    Toast.makeText(this, "Deleted", Toast.LENGTH_SHORT).show();


                                })
                                .setNegativeButton("Cancel", null)
                                .create();
                        dialog.show();
                    }
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == GALLERY_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Uri imageData = data.getData();
            message_image.setImageURI(imageData);
        }
    }
}
