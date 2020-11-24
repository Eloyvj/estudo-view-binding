package br.com.eloyvitorio.meuviewbinding

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.core.widget.addTextChangedListener
import br.com.eloyvitorio.meuviewbinding.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val names = mutableListOf<String>()
    private lateinit var mainAdapter: MainAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setAdapter()

        with(binding) {
            buttonDoAction.setOnClickListener {
                val name = inputName.text.toString()
                if (name.isNotEmpty()){
                    addName(name)
                    hideKeyboard()
                } else {
                    textNameValidationError.text = "Campo obrigatório"
                }
            }

            buttonClear.setOnClickListener {
                clearListName()
            }

            inputName.setOnEditorActionListener { view, _, _ ->
                addName(view.text.toString())
                hideKeyboard()
                true
            }

            inputName.addTextChangedListener {
                textNameValidationError.text = ""
            }
        }

    }

    private fun setAdapter(){
        mainAdapter = MainAdapter(names)
        with(binding.recyclerViewNames) {
            setHasFixedSize(true)
            adapter = mainAdapter
        }
    }

    private fun addName(name: String){
        binding.inputName.setText("")
        mainAdapter.addName(name)
    }

    private fun clearListName(){
        mainAdapter.clearNames()
    }

    private fun hideKeyboard(){
        val imm: InputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        var view: View? = currentFocus
        if (view == null) {
            view = View(this)
        }
        imm.hideSoftInputFromWindow(view.windowToken, 0)
    }
}