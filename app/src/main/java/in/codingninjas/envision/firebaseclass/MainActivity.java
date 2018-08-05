package in.codingninjas.envision.firebaseclass;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView listView;
    ArrayList<String> items = new ArrayList<>();
    ArrayAdapter<String> adapter;
    ArrayList<Expense> expenses = new ArrayList<>();
    DatabaseReference expensesRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listView = findViewById(R.id.listview);
        adapter = new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,items);
        listView.setAdapter(adapter);


        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.setPersistenceEnabled(true);
        DatabaseReference rootRef = database.getReference();
        expensesRef = rootRef.child("expenses");

        expensesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                items.clear();
                for(DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Expense expense = snapshot.getValue(Expense.class);
                    items.add(expense.getName());
                    expense.setId(snapshot.getKey());
                    expenses.add(expense);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Expense expense = expenses.get(i);
                DatabaseReference reference = expensesRef.child(expense.getId());
                reference.removeValue();

            }
        });

//        expensesRef.addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
//
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//
//            }
//        });



    }

    public void onButtonPressed(View view){
        Expense expense = Expense.getRandomExpense();
        DatabaseReference expenseRef = expensesRef.push();
        expense.setId(expenseRef.getKey());
        expenseRef.setValue(expense);
    }
}
