package com.example.parcialsegundo;

import androidx.appcompat.app.AppCompatActivity;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private CRUDRecetas CRUD;
    private ArrayAdapter<String> adaptador;
    private ArrayList<String> listaRecetitas;
    private ListView listaRecetas;
    private EditText editRecipeId, editRecipeTitle, editRecipeDescription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        CRUD = new CRUDRecetas(this);
        listaRecetitas = new ArrayList<>();
        listaRecetas = findViewById(R.id.listarecetas);

        Button btnAddRecipe = findViewById(R.id.btnAddRecipe);
        Button btnEditRecipe = findViewById(R.id.btnEditRecipe);
        Button btnDeleteRecipe = findViewById(R.id.btnDeleteRecipe);

        editRecipeId = findViewById(R.id.editRecipeId);
        editRecipeTitle = findViewById(R.id.editRecipeTitle);
        editRecipeDescription = findViewById(R.id.editRecipeDescription);

        refreshListView();

        btnAddRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title = editRecipeTitle.getText().toString();
                String description = editRecipeDescription.getText().toString();
                if (!title.isEmpty() && !description.isEmpty()) {
                    CRUD.insertarReceta(title, description);
                    refreshListView();
                    clearFields();
                }
            }
        });

        btnEditRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = editRecipeId.getText().toString();
                String title = editRecipeTitle.getText().toString();
                String description = editRecipeDescription.getText().toString();

                if (!idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    if (!title.isEmpty() && !description.isEmpty()) {
                        CRUD.actualizarReceta(id, title, description);
                        refreshListView();
                        clearFields();
                    } else {
                        Cursor c = CRUD.mostrarRecetas();
                        while (c.moveToNext()) {
                            if (c.getInt(0) == id) {
                                editRecipeTitle.setText(c.getString(1));
                                editRecipeDescription.setText(c.getString(2));
                                break;
                            }
                        }
                    }
                }
            }
        });

        btnDeleteRecipe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String idStr = editRecipeId.getText().toString();
                if (!idStr.isEmpty()) {
                    int id = Integer.parseInt(idStr);
                    CRUD.eliminarReceta(id);
                    refreshListView();
                    clearFields();
                }
            }
        });
    }

    private void refreshListView() {
        listaRecetitas.clear();
        Cursor c = CRUD.mostrarRecetas();
        while (c.moveToNext()) {
            listaRecetitas.add(c.getString(1));
        }
        c.close();

        if (adaptador == null) {
            adaptador = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, listaRecetitas);
            listaRecetas.setAdapter(adaptador);
        } else {
            adaptador.notifyDataSetChanged();
        }
    }

    private void clearFields() {
        editRecipeId.setText("");
        editRecipeTitle.setText("");
        editRecipeDescription.setText("");
    }
}

