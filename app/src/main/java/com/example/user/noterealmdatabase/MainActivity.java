package com.example.user.noterealmdatabase;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.Sort;

public class MainActivity extends AppCompatActivity {

    private EditText edBook;
    private ImageView imgAddBook;
    private Realm mRealm;

    private RealmResults<MyBook> mListBook;
    //    private List<MyBook> mListBook = new ArrayList<>();
    private RecyclerView recyclerView;
    private BookAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Realm.init(this);
        mRealm = Realm.getDefaultInstance();

        setContentView(R.layout.activity_main);

        addControl();
        init();
        addEvent();

    }

    private void addEvent() {
        imgAddBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edBook.getText().toString().length() > 0) {
                    createBook(edBook.getText().toString());
                    mAdapter.notifyDataSetChanged();
                    edBook.setText("");
                }
            }
        });
    }

    private void addControl() {
        edBook = (EditText) findViewById(R.id.edBook);
        imgAddBook = (ImageView) findViewById(R.id.imgAddBook);
    }

    private void init() {

        recyclerView = (RecyclerView) findViewById(R.id.recycleViewBook);
        mListBook = mRealm.where(MyBook.class).findAllSorted("id", Sort.DESCENDING);
        mAdapter = new BookAdapter(this, mListBook);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mRealm.close();
    }

    public void createBook(String title) {
        mRealm.beginTransaction();
        MyBook myBook = mRealm.createObject(MyBook.class);
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
        myBook.setId(df.format(new Date()));
        myBook.setTitle(title);
        mRealm.commitTransaction();
    }

}
