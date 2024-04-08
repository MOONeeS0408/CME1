package com.example.forms

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.util.Patterns
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.forms.databinding.ActivityMainBinding
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //
        binding.tvPrecio.text = "${generarPrecio()}"

        binding.btnConfirmar.setOnClickListener{


            //VALIDACION EMAIL
            val correo = binding.etEmail.text.toString().trim()
            if (correo.isEmpty()) {// Cuando el correo electrónico no se coloca
                binding.etEmail.error = getString(R.string.email_requerido)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            } else if (!Patterns.EMAIL_ADDRESS.matcher(correo).matches()) {
                // Cuando el correo electrónico es inválido
                binding.etEmail.error = getString(R.string.email_invalido)
                binding.etEmail.requestFocus()
                return@setOnClickListener
            } else {
                // El correo electrónico es válido
            }


            //Validación para numero de tarjeta
            if (binding.etTarjeta.text.isNotEmpty()){
                //verificar numero de tarjeta

            }else{
                //Toast.makeText(this, resources.getString(R.string.tarjeta_requerida), Toast.LENGTH_SHORT).show()
                binding.etTarjeta.error = getString(R.string.tarjeta_requerida)
                binding.etTarjeta.requestFocus()
                return@setOnClickListener
            }

            //Validación para digitos del mes
            if (binding.etMes.text.isNotEmpty()){
                val mes = binding.etMes.text.toString().toIntOrNull()
                if (mes != null && mes in 1..12) {
                    // Mes válido si es un número entre 1 y 12
                } else {
                    // Mes inválido
                    binding.etMes.error = getString(R.string.mes_invalido)
                    Toast.makeText(this, resources.getString(R.string.mes_invalido), Toast.LENGTH_SHORT).show()
                    binding.etMes.requestFocus()
                    return@setOnClickListener
                }

            }else{
                //Toast.makeText(this, resources.getString(R.string.mes_requerido), Toast.LENGTH_SHORT).show()
                binding.etMes.error = getString(R.string.mes_requerido)
                binding.etMes.requestFocus()
                return@setOnClickListener
            }



            //Validación para digitos del año
            if (binding.etYear.text.isNotEmpty()){
                val year = binding.etYear.text.toString().toIntOrNull()
                if (year != null && year in 24..50) {
                    // Año válido a partir de tarjetas del 2024 hasta 2050
                } else {
                    // Mes inválido
                    binding.etYear.error = getString(R.string.year_invalido)
                    binding.etYear.requestFocus()
                    return@setOnClickListener
                    //Toast.makeText(this, resources.getString(R.string.year_invalido), Toast.LENGTH_SHORT).show()
                }

            }else{
                //Toast.makeText(this, resources.getString(R.string.mes_requerido), Toast.LENGTH_SHORT).show()
                binding.etYear.error = getString(R.string.mes_requerido)
                binding.etYear.requestFocus()
                return@setOnClickListener
            }


            //Validación para CVV
            if (binding.etCVV.text.isNotEmpty()){
                val clave = binding.etCVV.text.toString()
                if (validaCVV(clave)){
                    //si es correcto
                }else   {
                    //Toast.makeText(this, resources.getString(R.string.cvv_invalido), Toast.LENGTH_SHORT).show()
                    binding.etCVV.error = getString(R.string.cvv_invalido)
                    binding.etCVV.requestFocus()
                    return@setOnClickListener
                }

            }else{
                //Toast.makeText(this, resources.getString(R.string.cvv_requerido), Toast.LENGTH_SHORT).show()
                binding.etCVV.error = getString(R.string.cvv_requerido)
                binding.etCVV.requestFocus()
                return@setOnClickListener
            }

            //VALIDACION TITULAR
            if (binding.etTitular.text.isNotEmpty()) {
                // El campo no está vacío
            } else {
                binding.etTitular.error = getString(R.string.nombre_vacio)
                binding.etTitular.requestFocus()
                return@setOnClickListener
            }


            //para las actividades mostrar fallida o exitosa
            val operacionFallida = (0..3).random() == 0 // 25% de probabilidad de ser exitoso

            val intent = if (operacionFallida) {
                Intent(this, OpFallida::class.java)
            } else {
                Intent(this, OpExitosa::class.java)
            }


            var titular = binding.etTitular.text.toString()
            val tarjeta = binding.etTarjeta.text.toString()

            titular = titular.toLowerCase()

            Log.d("MainActivity", "Titular: $titular, Tarjeta: $tarjeta")
            // Crear un Bundle y agregar los datos
            val bundle = Bundle().apply {
                putString("titular", titular)
                putString("tarjeta", tarjeta)
            }



            // Preparar el Intent para iniciar la nueva actividad
            intent.putExtras(bundle)


            // Iniciar la nueva actividad
            startActivity(intent)


        }
    }

    //FUNCION QUE VALIDA QUE EL CVV TENGA 3 O 4 DIGITOS
    private fun validaCVV(cvv: String): Boolean {
        val cvvRegex = Regex("\\d{3,4}")
        return cvvRegex.matches(cvv)
    }

    //FUNCION QUE GENERA UN NUMERO ALEATORIO ENTRE 100 Y 5000
    private fun generarPrecio(): Int {
        return Random.nextInt(100, 5001)
    }


}