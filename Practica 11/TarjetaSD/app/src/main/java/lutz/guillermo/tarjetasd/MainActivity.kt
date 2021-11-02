package lutz.guillermo.tarjetasd

import android.app.Activity
import android.app.ActivityManager
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View.inflate
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.core.content.res.ComplexColorCompat.inflate
import androidx.core.graphics.drawable.DrawableCompat.inflate
import java.io.BufferedReader
import java.io.File
import java.io.FileInputStream
import java.io.InputStreamReader
import java.lang.Exception
import java.util.jar.Manifest


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityCompat


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCompat.inflate(layoutInflater)
        setContentView(binding.root)

        if ((ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.READ_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                    ) || (ContextCompat.checkSelfPermission(this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE) !=
                    PackageManager.PERMISSION_GRANTED
                    )) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(
                    android.Manifest.permission.READ_EXTERNAL_STORAGE,
                    android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                123

            )
        }
        binding.btGuardar.setOnClickListener {
            Guardar(binding.etNuevoDato.text.toString())
            binding.tvContenido.text = Cargar()
        }
    }

    fun Guardar(texto: String) {
        try {
            val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
            val miCarpeta = File(rutaSD, "datos")
            if (!miCarpeta.exists()) {
                miCarpeta.mkdir()
            }
            val ficheroFisico = File(miCarpeta, "datos.txt")
            ficheroFisico.appendText("$texto\n")
        } catch (e: Exception) {
            Toast.makeText(
                this,
                "No se ha podido guardar",
                Toast.LENGTH_LONG).show()
        }

    }
        fun Cargar(): String {
            var texto = ""
            try {
                val rutaSD = baseContext.getExternalFilesDir(null)?.absolutePath
                val miCarpeta = File(rutaSD, "datos")
                val ficheroFisico = File(miCarpeta, "datos.txt")
                val fichero = BufferedReader(
                    InputStreamReader(FileInputStream(ficheroFisico))
                )
                texto = fichero.use(BufferedReader::readText)
            }
            catch (e: Exception) {
                // Nada
            }
            return texto

        }

    }
