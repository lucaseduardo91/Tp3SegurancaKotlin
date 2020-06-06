package com.infnetseg.tp3segcrypto

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import kotlinx.android.synthetic.main.activity_menu.*

class MenuActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu)

        btn_cadastar_avaliacao.setOnClickListener {
            var intent = Intent(this,CadastroAvaliacaoActivity::class.java)
            startActivity(intent)
        }

        btn_minhas_avaliacoes.setOnClickListener {
            var intent = Intent(this,MinhasAvaliacoesActivity::class.java)
            startActivity(intent)
        }

        btn_dados_sintetizados.setOnClickListener {
            var intent = Intent(this,DadosSintetizadosActivity::class.java)
            startActivity(intent)
        }
    }
}
