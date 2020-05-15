package com.example.myapplication;

import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.cepheuen.elegantnumberbutton.view.ElegantNumberButton;
import com.example.myapplication.Common.Common;
import com.example.myapplication.Database.Database;
import com.example.myapplication.Model.Food;
import com.example.myapplication.Model.Order;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class FoodDetail extends AppCompatActivity {

    TextView food_name,food_price,food_description;
    ImageView food_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;
    ElegantNumberButton numberButton;


    String foodId="";
    FirebaseDatabase database;
    DatabaseReference food;

    Food currentFood;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_detail);

        //.Firebase
        database =FirebaseDatabase.getInstance();
        food=database.getReference("Food");

        numberButton = (ElegantNumberButton) findViewById(R.id.number_button);
        btnCart= (FloatingActionButton) findViewById(R.id.btnCart);


        btnCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Database(getBaseContext()).addToChart(new Order(


                        foodId,
                        currentFood.getName(),
                        numberButton.getNumber(),
                        currentFood.getPrice(),
                        currentFood.getDiscount()



                ));

                Toast.makeText(FoodDetail.this,"Sepete eklendi",Toast.LENGTH_SHORT).show();
            }
        });

        food_description=(TextView)findViewById(R.id.food_description);
        food_name=(TextView)findViewById(R.id.food_name);
        food_price=(TextView)findViewById(R.id.food_price);
        food_image=(ImageView)findViewById(R.id.img_food);

        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if(getIntent()!=null)
            foodId=getIntent().getStringExtra("FoodId");
        if(!foodId.isEmpty())
            {
                if(Common.isConnectedToInternet(getBaseContext()))
                    getDetailFood(foodId);
                else {
                    Toast.makeText(FoodDetail.this, "İnternet bağlantınızı kontrol ediniz", Toast.LENGTH_SHORT).show();
                    return;
                }
            }



    }



    private void getDetailFood(String foodId) {

        food.child(foodId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
              Food food=dataSnapshot.getValue(Food.class);

                Picasso.with(getBaseContext()).load(food.getImage()).into(food_image);

                collapsingToolbarLayout.setTitle(food.getName());

                food_price.setText(food.getPrice());

                food_name.setText(food.getName());

                food_description.setText(food.getDescription());


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
