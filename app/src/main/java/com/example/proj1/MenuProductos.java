package com.example.proj1;

import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.proj1.databinding.ActivityMenuProductosBinding;

public class MenuProductos extends AppCompatActivity  {

    private AppBarConfiguration appBarConfiguration;
    private ActivityMenuProductosBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityMenuProductosBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_productos);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);

        Button botonHaciaAtras = findViewById(R.id.botonAtras);
        botonHaciaAtras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuProductos.this, MainActivity.class));
                finish(); //Finalizo la actividad actual
            }
        });

        Button botonParaPagar = findViewById(R.id.botonPagar);
        botonParaPagar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MenuProductos.this,MainActivity2.class));
                finish();
            }
        });

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_menu_productos);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }



    //Overflow options menu

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    //Administrador de el directorio
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id= item.getItemId();

        //Opcion para mostrar datos del usuario
        if(id == R.id.item1) {
           //Intent intent = new Intent(MenuProductos.this, Dades.class);
           // startActivity(intent);

        //Opcion para ir al Menu de Productos
        }else if(id == R.id.item2){
            Intent intent = new Intent(MenuProductos.this,MenuProductos.class);
            startActivity(intent);

        //Opcion para ir al Menu de Comandes
        }else if(id == R.id.item3){
            Intent intent= new Intent(MenuProductos.this, MainActivity2.class);
            startActivity(intent);
        } //Opcion para salir de la aplicacion
        else if (id == R.id.item4){
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_HOME);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}