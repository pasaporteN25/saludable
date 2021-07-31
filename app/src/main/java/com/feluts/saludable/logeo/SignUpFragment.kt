package com.feluts.saludable.logeo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.feluts.saludable.R
import com.feluts.saludable.model.User
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SignUpFragment : Fragment() {

    companion object {
        fun newInstance() = SignUpFragment()
    }
    //private lateinit var auth:FirebaseAuth
    private lateinit var SignUpVM: SignUpViewModel
    lateinit var nombre:EditText
    lateinit var apellido:EditText
    lateinit var dni:EditText
    lateinit var sexo_sp:Spinner
    lateinit var bday:DatePicker
    lateinit var localidad:EditText
    lateinit var usuario:EditText
    lateinit var pass:EditText
    lateinit var tratamiento_sp:Spinner
    lateinit var guardar:Button
    var sexo_sel:String = ""
    var tratam_sel:String = ""
    val sexos = arrayOf("Hombre","Mujer","Otro")
    val tratamientos = arrayOf("Anorexia", "Bulimia", "Obesidad")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.sign_up_fragment, container, false)
        inicializar(view)
        getSP()


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        SignUpVM = ViewModelProvider(this).get(SignUpViewModel::class.java)

        guardar.setOnClickListener(
            View.OnClickListener {
                val nuevousr = User(nombre.text.toString(),apellido.text.toString()
                    ,dni.text.toString().toInt(),sexo_sel,"1234",localidad.text.toString()
                    ,usuario.text.toString(),pass.text.toString(),tratam_sel)

                val user = usuario.text.toString() +"@saludable.com"
                FirebaseAuth.getInstance().createUserWithEmailAndPassword(user,pass.text.toString())
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            Toast.makeText(context,"Usuario Creado",Toast.LENGTH_SHORT).show()
                            limpiarF()
                            //volver atras?
                        }else{
                            Toast.makeText(context,"Error al crear",Toast.LENGTH_SHORT).show()
                            Log.w("TAG","fallo",task.exception)
                        }
                    }
                //creo que esto tambien puede pasar al VM  y aca quedaria la logica
                SignUpVM.insertardb(nuevousr)
                //limpiar campos, y el datepicker
            }
        )

    }

    private fun inicializar(view:View){
        nombre = view.findViewById(R.id.nombre_us)
        apellido = view.findViewById(R.id.apellido_us)
        dni = view.findViewById(R.id.dni_us)
        sexo_sp = view.findViewById(R.id.sexo_sp)
        sexo_sp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, sexos)
        //bday = view.findViewById(R.id.bday_us)
        localidad = view.findViewById(R.id.localidad_us)
        usuario = view.findViewById(R.id.user_log)
        pass = view.findViewById(R.id.pass_log)
        tratamiento_sp = view.findViewById(R.id.tratamiento_sp)
        tratamiento_sp.adapter = ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, tratamientos)
        guardar = view.findViewById(R.id.registrar_us)
    }

    private fun getSP(){

        sexo_sp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                sexo_sel = sexos[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }
        }

        tratamiento_sp.onItemSelectedListener = object: AdapterView.OnItemSelectedListener{
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, pos: Int, id: Long) {
                tratam_sel = tratamientos[pos]
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun limpiarF(){
        nombre.setText("")
        apellido.setText("")
        dni.setText("")
        localidad.setText("")
        usuario.setText("")
        pass.setText("")
    }

}