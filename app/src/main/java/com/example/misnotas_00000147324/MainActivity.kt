package com.example.misnotas_00000147324

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import java.io.*

class MainActivity : AppCompatActivity() {
    var notas = ArrayList<Nota>()
    lateinit var adaptador: AdaptadorNotas

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val fab: com.google.android.material.floatingactionbutton.FloatingActionButton = findViewById(R.id.fab)
        val listview: ListView = findViewById(R.id.listview)
        fab.setOnClickListener {
            var intent = Intent(this, AgregarNotaActivity::class.java)
            startActivityForResult(intent, 123)
        }
        leerNotas()
        adaptador = AdaptadorNotas(this, notas)
        listview.adapter = adaptador
    }

    fun leerNotas() {
        notas.clear()
        var carpeta = File(ubicacion().absolutePath)

        if (carpeta.exists()) {
            var archivos = carpeta.listFiles()
            if (archivos != null) {
                for (archivo in archivos) {
                    leerArchivo(archivo)
                }
            }
        }
    }


    fun leerArchivo(archivo: File) {
        val fis = FileInputStream(archivo)
        val di = DataInputStream(fis)
        val br = BufferedReader(InputStreamReader(di))
        var strLine: String? = br.readLine()
        var myData = ""
        while (strLine != null) {
            myData = myData + strLine
            strLine = br.readLine()

        }
        br.close()
        di.close()
        fis.close()
                  //elimina .txt
        var nombre = archivo.name.substring(0, archivo.name.length - 4)
            //crea la nota y add .list
        var nota = Nota(nombre, myData)
        notas.add(nota)
    }

    public fun ubicacion(): File {
        val folder = File(getExternalFilesDir(null), "notas")
        if (!folder.exists()) {
            folder.mkdir()
        }
        return folder
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == 123) {
            leerNotas()
            adaptador.notifyDataSetChanged()
        }
    }



}