package com.infnetseg.tp3segcrypto

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.infnetseg.tp3segcrypto.converters.CriptoConverter
import com.infnetseg.tp3segcrypto.entities.Avaliacao
import com.infnetseg.tp3segcrypto.repository.BancoDados
import com.infnetseg.tp3segcrypto.repository.DatabaseFactory
import com.infnetseg.tp3segcrypto.security.CriptoString
import com.infnetseg.tp3segcrypto.ui.LoadingAlerta
import kotlinx.android.synthetic.main.activity_cadastro_avaliacao.*

class CadastroAvaliacaoActivity : AppCompatActivity() {

    private lateinit var auth : FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cadastro_avaliacao)

        auth = FirebaseAuth.getInstance()

        btn_send_aval.setOnClickListener {
            var empresa = cadastro_empresa.text.toString()
            var bairro = bairro_cadastro.text.toString()

            if(!empresa.isNullOrBlank() && !bairro.isNullOrBlank() && !verificaRespostas()) {
                var db = DatabaseFactory.getInstance(null)
                CadastrarAvaliacaoAsync(this, db,auth.currentUser!!.uid,empresa,bairro,concatenaRespostas()).execute()
            }
            else
                Toast.makeText(this,"Preencha os campos corretamente!",Toast.LENGTH_SHORT)
        }
    }

    class CadastrarAvaliacaoAsync(activity: Activity, db : BancoDados, serie : String, empresa : String, bairro : String, respostas : String) : AsyncTask<Void, Void, Void>() {
        var activity = activity
        var empresa = empresa
        var bairro = bairro
        var respostas = respostas
        var db = db
        var serie = serie
        var dialogApi = LoadingAlerta(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Cadastrando avaliação...")
        }

        override fun doInBackground(vararg params: Void?): Void? {
            var empresaCripto = CriptoString()

            empresaCripto.setClearText(empresa)

            db.avaliacaoDao().insertAll(Avaliacao(0,serie, empresaCripto, bairro,respostas))
            dialogApi.dismiss()

            var intent = Intent(activity,MenuActivity::class.java)
            activity.startActivity(intent)
            return null
        }
    }

    fun verificaRespostas() : Boolean{
        if(respostaSim1.isChecked || respostaNao1.isChecked)
            return false
        if(respostaSim2.isChecked || respostaNao2.isChecked)
            return false
        if(respostaSim3.isChecked || respostaNao3.isChecked)
            return false
        if(respostaSim4.isChecked || respostaNao4.isChecked)
            return false
        if(respostaSim5.isChecked || respostaNao5.isChecked)
            return false
        if(respostaSim6.isChecked || respostaNao6.isChecked)
            return false

        return true
    }

    fun concatenaRespostas() : String{
        var respostas = ""

        if(respostaSim1.isChecked)
            respostas += "1,"
        else
            respostas += "0,"

        if(respostaSim2.isChecked)
            respostas += "1,"
        else
            respostas += "0,"

        if(respostaSim3.isChecked)
            respostas += "1,"
        else
            respostas += "0,"

        if(respostaSim4.isChecked)
            respostas += "1,"
        else
            respostas += "0,"

        if(respostaSim5.isChecked)
            respostas += "1,"
        else
            respostas += "0,"

        if(respostaSim6.isChecked)
            respostas += "1"
        else
            respostas += "0"

        return respostas
    }
}
