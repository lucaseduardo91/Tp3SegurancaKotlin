package com.infnetseg.tp3segcrypto

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.auth.FirebaseAuth
import com.infnetseg.tp3segcrypto.adapters.MinhasAvaliacoesAdapter
import com.infnetseg.tp3segcrypto.entities.Avaliacao
import com.infnetseg.tp3segcrypto.repository.BancoDados
import com.infnetseg.tp3segcrypto.repository.DatabaseFactory
import com.infnetseg.tp3segcrypto.ui.LoadingAlerta
import kotlinx.android.synthetic.main.activity_minhas_avaliacoes.*

class MinhasAvaliacoesActivity : AppCompatActivity() {

    private lateinit var listaAvaliacao: MutableList<Avaliacao>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_minhas_avaliacoes)

        var serie = FirebaseAuth.getInstance().currentUser!!.uid
        var db = DatabaseFactory.getInstance(null)

        btn_voltar_minhas_aval.setOnClickListener {
            var intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }

        listaAvaliacao = mutableListOf<Avaliacao>()
        BuscarConvitesEnviadosAsync(this,listaAvaliacao,db,serie).execute()
    }



    class BuscarConvitesEnviadosAsync(activity: Activity, listaAval : MutableList<Avaliacao>, db : BancoDados, serie : String) : AsyncTask<Void, Void, List<Avaliacao>?>() {
        var activity = activity
        var listaAval = listaAval
        var db = db
        var serie = serie
        var dialogApi = LoadingAlerta(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Buscando suas avalições...")
        }

        override fun doInBackground(vararg params: Void?): List<Avaliacao>? {
            var lista = db.avaliacaoDao().loadBySerie(serie)
            dialogApi.dismiss()
            return lista
        }

        override fun onPostExecute(result: List<Avaliacao>?) {
            super.onPostExecute(result)

            var lista = activity!!.findViewById<RecyclerView>(R.id.listagem_minhas_aval)

            if (!result.isNullOrEmpty()) {
                result.forEach {
                    listaAval.add(it)
                }

                lista.layoutManager = LinearLayoutManager(activity)
                lista.adapter = MinhasAvaliacoesAdapter(listaAval)
            } else {
                var vazio = activity!!.findViewById<TextView>(R.id.empty_minhas_aval)
                lista.visibility = View.GONE
                vazio.visibility = View.VISIBLE
            }

        }
    }
}
