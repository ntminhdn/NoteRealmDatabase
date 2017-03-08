package com.example.user.noterealmdatabase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmChangeListener;
import io.realm.RealmResults;
import io.realm.Sort;

/**
 * Created by User on 08/03/2017.
 */

public class BookAdapter extends RecyclerView.Adapter<MyBookViewHolder> implements RealmChangeListener {

    private RealmResults<MyBook> mBooks;
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public BookAdapter(Context context, RealmResults<MyBook> mBooks) {
        this.mContext = context;
        mLayoutInflater = LayoutInflater.from(context);
        this.mBooks = mBooks;
        mBooks.addChangeListener(this);
    }

    @Override
    public MyBookViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View item = mLayoutInflater.inflate(R.layout.item_recycle_book, parent, false);
        return new MyBookViewHolder(item);
    }

    @Override
    public void onBindViewHolder(final MyBookViewHolder holder, final int position) {
        MyBook book = mBooks.get(position);
        holder.tvBookId.setText(book.getId());
        holder.tvBookTitle.setText(book.getTitle());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editName(position);
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                    @Override
                    public void execute(Realm realm) {
                        // remove a single object
                        MyBook book = mBooks.get(position);
                        book.deleteFromRealm();
                    }
                });
            }
        });

        sortByDate();
    }

    private void sortByDate() {
        mBooks = Realm.getDefaultInstance().where(MyBook.class).findAllSorted("id", Sort.DESCENDING);
    }

    private void editName(final int position) {
        LayoutInflater layoutInflater = LayoutInflater.from(mContext);
        View promptView = layoutInflater.inflate(R.layout.dialog_newname, null);
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(mContext);
        alertDialogBuilder.setView(promptView);

        final EditText edNewName = (EditText) promptView.findViewById(R.id.edNewName);

        alertDialogBuilder.setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        Realm.getDefaultInstance().executeTransaction(new Realm.Transaction() {
                            @Override
                            public void execute(Realm realm) {

                                MyBook book = mBooks.get(position);
                                DateFormat df = new SimpleDateFormat("dd-MM-yyyy hh:mm");
                                book.setId(df.format(new Date()));
                                book.setTitle(edNewName.getText().toString());
                            }
                        });
                    }
                })
                .setNegativeButton("Cancel",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });

        AlertDialog myAlertDialog = alertDialogBuilder.create();
        myAlertDialog.show();
    }


    @Override
    public int getItemCount() {
        return mBooks.size();
    }


    @Override
    public void onChange(Object element) {
        notifyDataSetChanged();
    }
}
