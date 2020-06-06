package com.infnetseg.tp3segcrypto

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.infnetseg.tp3segcrypto.entities.Avaliacao
import com.infnetseg.tp3segcrypto.repository.BancoDados
import com.infnetseg.tp3segcrypto.repository.DatabaseFactory
import com.infnetseg.tp3segcrypto.ui.LoadingAlerta
import kotlinx.android.synthetic.main.activity_dados_sintetizados.*

class DadosSintetizadosActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dados_sintetizados)

        var textoFinal = text_dados_sintet
        var db = DatabaseFactory.getInstance(null)

        CarregarDadosSintetizadosAsync(this,db,textoFinal).execute()

        btn_voltar_sintetizado.setOnClickListener {
            var intent = Intent(this,MenuActivity::class.java)
            startActivity(intent)
        }
    }

    class CarregarDadosSintetizadosAsync(activity: Activity, db : BancoDados, textoFinal : TextView) : AsyncTask<Void, Void, Unit>() {
        var activity = activity
        var db = db
        var textoFinal = textoFinal
        var dialogApi = LoadingAlerta(activity)

        override fun onPreExecute() {
            super.onPreExecute()
            dialogApi.startLoadingDialog("Carregando dados sintetizados...")
        }

        override fun doInBackground(vararg params: Void?) {

            var lista = db.avaliacaoDao().getAllData()

            if(lista == null)
                mudarTextoComponente(textoFinal,"Ainda não foram cadastradas avaliações no sistema")
            else
            {
                gerarDadosAgrupados(lista)
            }
            dialogApi.dismiss()
        }

        fun mudarTextoComponente(textoFinal : TextView, texto : String)
        {
            textoFinal.text = texto
        }

        fun gerarDadosAgrupados(lista : List<Avaliacao>){

            var result = mutableMapOf<String, MutableList<Avaliacao>>()
            var ultimoBairro = ""
            var textoDados = ""
            var cont = 0

            var listaEmOrdem = lista.sortedBy { it.bairro }

            for(aval in listaEmOrdem)
            {
                if(aval.bairro != ultimoBairro)
                {
                    ultimoBairro = aval.bairro!!
                    result.put(aval.bairro, mutableListOf(aval))
                }
                else
                {
                     var l = result[aval.bairro]!!.add(aval)
                }

            }

            textoDados += "Legenda -> Bairro: nome, nº de avaliações ,Percentual de Sim resposta nº - %,...\n\n"

            for(pair in result)
            {
                var qtdAval = pair.value.size
                var respostasArray = obterQtdRespostasItem(pair.value)

                textoDados += "Bairro: ${pair.key}, $qtdAval , "

                while(cont < 6)
                {
                    if(cont == 5)
                        textoDados += "${cont+1} - ${String.format("%.2f",((respostasArray[cont] / qtdAval)*100.0))}%\n"
                    else
                        textoDados += "${cont+1} - ${String.format("%.2f",(respostasArray[cont] / qtdAval)*100.0)}%, "

                    cont++
                }
                cont = 0
            }

            mudarTextoComponente(textoFinal,textoDados)
        }

        fun obterQtdRespostasItem(listaBairro : MutableList<Avaliacao>) : DoubleArray{

            var respostasArray : DoubleArray = doubleArrayOf(0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
            var contResp = 0

            for(aval in listaBairro)
            {
                var parseResp = aval.respostas!!.split(",")

                while(contResp < 6)
                {
                    if(parseResp[contResp] == "1")
                        respostasArray[contResp]++

                    contResp++
                }
                contResp = 0
            }

            return respostasArray
        }
    }
}
