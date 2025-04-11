package com.example.orgs.ui.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.orgs.database.AppDatabase
import com.example.orgs.databinding.ActivityListaProdutosBinding
import com.example.orgs.ui.recyclerview.adapter.ListaProdutosAdapter
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ListaProdutosActivity : AppCompatActivity() {

    private val adapter = ListaProdutosAdapter(
        context = this
    )
    private val binding by lazy {
        ActivityListaProdutosBinding.inflate(layoutInflater)
    }

    //    private val job = Job()
    private val handler = CoroutineExceptionHandler { coroutineContext, throwable ->
        Log.i("CoroutineExceptionHandler", "onResume: throwable: $throwable")
        Toast.makeText(this, "throwable: $throwable", Toast.LENGTH_SHORT).show()
    }

    private val produtoDao by lazy { AppDatabase.intancia(this).produtoDAO() }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        configuraRecyclerView()
        configuraFab()
    }

    override fun onResume() {
        super.onResume()
        lifecycleScope.launch(handler) {
            // Aqui podemos apresentar uma pre execucao como um loading

            val produtos = buscaTodosProdutos()

            // e aqui seria uma pos execucao como a atualizacao de um componente
            adapter.atualiza(produtos)
        }

    }

    private suspend fun buscaTodosProdutos() =
        withContext(Dispatchers.IO) {
            produtoDao.buscaTodos()
        }

    private fun configuraFab() {
        val fab = binding.activityListaProdutosFab
        fab.setOnClickListener {
            vaiParaFormularioProduto()
        }
    }

    private fun vaiParaFormularioProduto() {
        val intent = Intent(this, FormularioProdutoActivity::class.java)
        startActivity(intent)
    }

    private fun configuraRecyclerView() {
        val recyclerView = binding.activityListaProdutosRecyclerView
        recyclerView.adapter = adapter
        adapter.quandoClicaNoItem = {
            val intent = Intent(
                this,
                DetalhesProdutoActivity::class.java
            ).apply {
                putExtra(CHAVE_PRODUTO_ID, it.id)
            }
            startActivity(intent)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
//        job.cancel()
    }
}