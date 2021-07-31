package com.feluts.saludable.logeo

import android.content.Intent
import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import com.feluts.saludable.MainActivity
import com.feluts.saludable.R
import com.google.firebase.auth.FirebaseAuth

class LogInFragment : Fragment() {

    companion object {
        fun newInstance() = LogInFragment()
    }

    private lateinit var viewModel: LogInViewModel
    lateinit var user:EditText
    lateinit var pass:EditText
    lateinit var signup:Button
    lateinit var login:Button
    val signupFrag: SignUpFragment = SignUpFragment()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.log_in_fragment, container, false)

        inicializar(view)

        signup.setOnClickListener(
            View.OnClickListener {
                val fragmentManager: FragmentManager = requireActivity().supportFragmentManager
                val fragmentTransaction: FragmentTransaction = fragmentManager.beginTransaction()
                fragmentTransaction.replace(R.id.logfrag_container,signupFrag)
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()



            }
        )
        login.setOnClickListener(
            View.OnClickListener {

                val usuario = user.text.toString() + "@saludable.com"

                FirebaseAuth.getInstance()
                    .signInWithEmailAndPassword(usuario,pass.text.toString())
                    .addOnCompleteListener{task->
                        if(task.isSuccessful){
                            Log.w("TAG","Iniciando...",task.exception)
                            val intent:Intent = Intent(it.context,MainActivity::class.java)
                            //intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY)
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            it.context.startActivity(intent)
                        }else{
                            Log.w("TAG","fallo",task.exception)

                        }
                    }
            }
        )
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(LogInViewModel::class.java)
        // TODO: Use the ViewModel
    }

    fun inicializar(view: View){
        user = view.findViewById(R.id.user_log)
        pass = view.findViewById(R.id.pass_log)
        signup = view.findViewById(R.id.signup_us)
        login = view.findViewById(R.id.registrar_us)
    }

}