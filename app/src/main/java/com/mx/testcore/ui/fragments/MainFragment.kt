package com.mx.testcore.ui.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.mx.testcore.R
import com.mx.testcore.core.isNull
import com.mx.testcore.databinding.FragmentMainBinding
import com.mx.testcore.ui.UiState
import com.mx.testcore.ui.components.viewBinding
import com.mx.testcore.ui.fragments.recyclerview.TaskAdapter
import com.mx.testcore.ui.viewmodels.MainViewModel
import com.mx.testcore.utils.Tools
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainFragment : Fragment(R.layout.fragment_main) {

    private val bindingView by viewBinding(FragmentMainBinding::bind)

    private val mainViewModel : MainViewModel by viewModels()

    private var navController: NavController? = null
    private lateinit var taskAdapter: TaskAdapter


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        this.initComponents()
        this.initListeners()

        mainViewModel.getProducts()
    }

    private fun initComponents() = with(bindingView) {
        taskAdapter = TaskAdapter() { product ->
            val args = bundleOf("product" to product)
            navController?.navigate(R.id.action_mainFragment_to_detailFragment, args)
        }
        recyclerView.layoutManager = LinearLayoutManager(requireActivity())
        recyclerView.adapter = taskAdapter
    }

    private fun initListeners() {
        mainViewModel.getProductsUiState.observe(viewLifecycleOwner) { state ->
            when ( state ) {
                is UiState.Failure -> {
                    dismissLoader()
                }
                is UiState.Loading -> {
                    showLoader()
                }
                is UiState.Success -> {
                    dismissLoader()
                    if( state.data.isNotEmpty() ) {
                        changeViewState("data")
                        taskAdapter.setProducts(state.data)
                    } else {
                        changeViewState("empty")
                    }
                }
            }
        }
    }

    private fun showLoader() {
        activity?.let {
            it.runOnUiThread {
                Tools.progressDialog(
                    cancelable = false,
                    activity = it as AppCompatActivity
                )
            }
        }
    }

    private fun dismissLoader() {
        Tools.dismissProgressDialog()
    }

    private fun changeViewState(state: String) = with(bindingView) {
        when(state) {
            "empty" -> {
                emptyView.root.visibility = View.VISIBLE
                contentContainer.visibility = View.GONE
            }
            "data" -> {
                emptyView.root.visibility = View.GONE
                contentContainer.visibility = View.VISIBLE
            }
            else -> print("I don't know anything about it")
        }
    }
}