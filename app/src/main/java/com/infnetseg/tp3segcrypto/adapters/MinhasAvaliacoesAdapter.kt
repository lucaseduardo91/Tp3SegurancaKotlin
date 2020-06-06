package com.infnetseg.tp3segcrypto.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.infnetseg.tp3segcrypto.R
import com.infnetseg.tp3segcrypto.entities.Avaliacao

class MinhasAvaliacoesAdapter (avaliacoes : List<Avaliacao>) :
    RecyclerView.Adapter<MinhasAvaliacoesAdapter.MinhasAvaliacoesViewHolder>(){
    var listaAval = avaliacoes

    class MinhasAvaliacoesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        var empresa = itemView.findViewById<TextView>(R.id.empresa_lista_aval)
        var bairro = itemView.findViewById<TextView>(R.id.bairro_lista_aval)
        var respostas = itemView.findViewById<TextView>(R.id.respostas_lista_aval)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MinhasAvaliacoesViewHolder {
        val card = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.item_minha_aval, parent, false)

        return MinhasAvaliacoesViewHolder(card)
    }

    override fun getItemCount() = listaAval.size

    override fun onBindViewHolder(holder: MinhasAvaliacoesViewHolder, position: Int) {

        holder.empresa.text = listaAval[position].empresa!!.getClearText()
        holder.bairro.text = listaAval[position].bairro!!
        organizaRespostas(holder.respostas, listaAval[position].respostas!!)
    }

    fun organizaRespostas(respostasText : TextView, respostas : String){
        var listaResp = respostas.split(",")
        var formatado = ""
        var cont = 1

        while (cont <= 6)
        {
            if(listaResp[cont-1] == "1")
                formatado += "$cont-Sim"
            else
                formatado += "$cont-Não"

            if(cont < 6)
                formatado += ", "

            cont++
        }

        respostasText.text = formatado
    }
}