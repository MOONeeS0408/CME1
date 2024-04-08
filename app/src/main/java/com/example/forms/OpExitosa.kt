package com.example.forms

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.forms.databinding.ActivityMain2Binding
import com.example.forms.databinding.ActivityMainBinding

class OpExitosa : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMain2Binding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val datosRecibidos = intent.extras

        val tarjeta = datosRecibidos?.getString("tarjeta")

        val ultimosCuatroDigitos: String = if (tarjeta != null) {
            // Si la tarjeta no es nula, obtener los últimos cuatro dígitos
            tarjeta.substring(tarjeta.length - 4)
        } else {
            // Si la tarjeta es nula, asignar un valor predeterminado
            Toast.makeText(this, resources.getString(R.string.tarjeta_invalido), Toast.LENGTH_SHORT).show()
            ""
        }

        // Utilizar los datos como sea necesario
        // Por ejemplo, mostrar los datos en TextViews
        binding.tvDescrip.text = "El pago realizado con la tarjeta con terminación ***$ultimosCuatroDigitos fue realizado con éxito"

    }


}