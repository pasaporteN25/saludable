package com.feluts.saludable

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.View
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.feluts.saludable.model.Trago
import com.feluts.saludable.ui.gallery.GalleryFragment
import com.google.firebase.database.DatabaseReference
import com.google.firebase.firestore.FirebaseFirestore
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.time.LocalDateTime

class ComidaActivity : AppCompatActivity(){

    private lateinit var ComidaVM: ComidaViewModel
    private val dbcloud = FirebaseFirestore.getInstance()
    private lateinit var db: DatabaseReference
    lateinit var comida_sp:Spinner
    lateinit var principal:EditText
    lateinit var secundaria:EditText
    lateinit var bebida_sp:Spinner
    lateinit var postre_sw:Switch
    lateinit var postre:EditText
    lateinit var tentacion_sw:Switch
    lateinit var tentacion:EditText
    lateinit var hambre_sw:Switch
    lateinit var enviarbtn: Button
    lateinit var limpiarbtn: Button
    val comidas = arrayOf("Desayuno","Almuerzo","Merienda","Cena","Colacion")
    val bebidas = arrayOf("Agua","Agua saborizada","Jugo de furtas","Gaseosa","Bebida con alcohol")
    var comida_sel:String = ""
    var bebida_sel:String = ""
    var hambre:String = ""
    lateinit var btn:Button
    lateinit var texto:EditText
    lateinit var img:ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comida)

        inicializar()
        inicializarsp()

        comida_sp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                comida_sel = comidas[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                comida_sel = comidas[0]
            }
        }

        bebida_sp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                bebida_sel = bebidas[p2]
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                bebida_sel = bebidas[0]
            }
        }

        postre_sw.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isCheked: Boolean) {
                if(isCheked){
                    postre.isVisible = true
                }else{
                    postre.isVisible = false
                }
            }
        })

        tentacion_sw.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    tentacion.isVisible = true
                }else{
                    tentacion.isVisible = false
                }
            }
        })

        hambre_sw.setOnCheckedChangeListener(object: CompoundButton.OnCheckedChangeListener{
            override fun onCheckedChanged(buttonView: CompoundButton?, isChecked: Boolean) {
                if(isChecked){
                    hambre = "Con hambre"
                }else{
                    hambre = "Sin hambre"
                }
            }

        })


        enviarbtn.setOnClickListener(
            View.OnClickListener {

                val currentDT = LocalDateTime.now()
                val comida = Comidas(0,comida_sel,principal.text.toString(),secundaria.text.toString(),bebida_sel
                    ,postre?.text.toString(),tentacion.text.toString(),hambre,currentDT.toString())

                ComidaVM = ViewModelProvider(this).get(ComidaViewModel::class.java)
                ComidaVM.insertardb(comida)
                limpiarF()

                val tragoFrag: GalleryFragment = GalleryFragment()



                //val view = View.inflate(this,R.layout.dialog_view,null)
                //val builder = AlertDialog.Builder(this)
                //builder.setView(view)

//                val dialog = builder.create()
//                dialog.show()
//                dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

                //btn = view.findViewById(R.id.btn_tr)


                ComidaVM.getTrago().enqueue(object: Callback<Trago>{
                    override fun onResponse(call: Call<Trago>, response: Response<Trago>) {
                        if(response.body() != null){
                            val data = response.body()
                            val testo = data!!.drinks[0].strDrink
                            texto.setText(testo)
                            Glide.with(it.context)
                                .load(data?.drinks[0].strDrinkThumb)
                                .centerCrop().into(img)

                        }
                    }

                    override fun onFailure(call: Call<Trago>, t: Throwable) {
                        Log.e("Error en la API", t.message.toString())
                    }

                })

                //btn.setOnClickListener(
                //    View.OnClickListener {
                //        dialog.dismiss()
                //    }
                //)


            }
        )

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    private fun inicializar(){
        comida_sp = findViewById(R.id.tipocom_sp)
        principal = findViewById(R.id.compri_in)
        secundaria = findViewById(R.id.comsec_in)
        bebida_sp = findViewById(R.id.tipobeb_sp)
        postre_sw = findViewById(R.id.postre_sw)
        postre = findViewById(R.id.postre_in)
        postre.isVisible = false
        tentacion_sw = findViewById(R.id.tenta_sw)
        tentacion = findViewById(R.id.tenta_in)
        tentacion.isVisible = false
        hambre_sw = findViewById(R.id.mashambre)
        limpiarbtn = findViewById(R.id.limpiar_btn)
        enviarbtn = findViewById(R.id.enviar_btn)
        texto = findViewById(R.id.txtshow)
        img = findViewById(R.id.img_nu)
    }

    private fun inicializarsp(){
        comida_sp.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item, comidas)
        bebida_sp.adapter = ArrayAdapter(this,android.R.layout.simple_spinner_item,bebidas)
    }

    private fun limpiarF(){
        comida_sp.setSelection(0)
        principal.setText("")
        secundaria.setText("")
        bebida_sp.setSelection(0)
        postre_sw.setEnabled(false)
        postre.setText("")
        tentacion_sw.setEnabled(false)
        tentacion.setText("")
        hambre_sw.setEnabled(false)


    }
}