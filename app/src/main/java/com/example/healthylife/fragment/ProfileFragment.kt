package com.example.healthylife.fragment

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.healthylife.R
import com.example.healthylife.activity.LoginActivity
import com.example.healthylife.databinding.FragmentProfileBinding
import android.os.Bundle as Bundle1


class ProfileFragment : Fragment() {

      private var _binding : FragmentProfileBinding? = null
      private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }


    override fun onCreateView(
          inflater: LayoutInflater, container: ViewGroup?,
          savedInstanceState: Bundle1?,
      ): View? {
         _binding = FragmentProfileBinding.inflate(inflater, container, false)
          return binding.root
      }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val userName : TextView = view?.findViewById(R.id.usernameChange)!!
        val password : TextView = view?.findViewById(R.id.passwordChange)!!
        val etpassword: EditText = view.findViewById(R.id.etkpasswordnew)
        val etconfirm : EditText =  view.findViewById(R.id.etkpasswordnewconfirm)
        val button : Button = view?.findViewById(R.id.buttonLogout)!!
        button.setOnClickListener {
            val intent = Intent( activity, LoginActivity::class.java)
            startActivity(intent)
        }
        userName.setOnClickListener{
            Toast.makeText(activity, "Kullanıcı adı değiştirildi", Toast.LENGTH_SHORT).show()
        }
        password.setOnClickListener{
            val newpassword = etpassword.text.toString()
            val confirmpassword = etconfirm.text.toString()
            if (newpassword == confirmpassword){
                Toast.makeText(activity, "Şifre değiştirildi", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(activity, "Şifreler eşleşmiyor", Toast.LENGTH_SHORT).show()
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}