package com.example.misnotas_00000147324

import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import java.io.File
import java.io.FileOutputStream
import java.lang.Exception


class AgregarNotaActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_agregar_nota)

        val btnGuardar: Button = findViewById(R.id.btnGuardar)

        btnGuardar.setOnClickListener{
            guardarNota();
        }
    }

    fun guardarNota(){
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
            != PackageManager.PERMISSION_GRANTED
        ){
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                235
            )
        }else{
            guardar()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray){
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when(requestCode){
            235 ->{
                if ((grantResults.isNotEmpty()&&grantResults[0]== PackageManager.PERMISSION_GRANTED)) {
                    guardar()
                }
                else{
                    Toast.makeText(this, "Error: permisos denegados", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    public fun guardar(){

        val etTitulo: EditText = findViewById(R.id.etTitulo)
        val etContenido: EditText = findViewById(R.id.etContenido)

        var titulo = etTitulo.text.toString()
        var cuerpo = etContenido.text.toString()

        if(titulo==""||cuerpo==""){
            Toast.makeText(this, "Error", Toast.LENGTH_SHORT).show()
        }else{
            try{
                val archivo = File(ubicacion(), titulo+".txt")
                val fos = FileOutputStream(archivo)
                fos.write(cuerpo.toByteArray())
                fos.close()
                Toast.makeText(this, "Archivo guardado", Toast.LENGTH_SHORT).show()

            }catch (e: Exception){
                Toast.makeText(this, "Error:Archivo no guardado", Toast.LENGTH_SHORT).show()

            }
        }
        finish()

    }

    public fun ubicacion(): String {
        val carpeta = File(getExternalFilesDir(null),"Notas")
        if(!carpeta.exists()){
            carpeta.mkdir()
        }

        return carpeta.absolutePath
    }

}