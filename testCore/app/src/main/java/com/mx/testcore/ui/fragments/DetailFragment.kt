package com.mx.testcore.ui.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.mx.testcore.R
import com.mx.testcore.core.load
import com.mx.testcore.data.models.Product
import com.mx.testcore.databinding.FragmentDetailBinding
import com.mx.testcore.databinding.FragmentDetailsBinding
import com.mx.testcore.databinding.FragmentMainBinding
import com.mx.testcore.ui.components.viewBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailFragment : Fragment(R.layout.fragment_detail) {

    private val bindingView by viewBinding(FragmentDetailBinding::bind)

    private var navController: NavController? = null

    private var product: Product? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        this.getProduct()
    }

    private fun getProduct() {
        product = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            arguments?.getParcelable("product", Product::class.java)!!
        } else {
            arguments?.getParcelable("product")!!
        }

        this.initLoadView()

    }

    private fun initLoadView() = with(bindingView) {
        ivPicture.load(product!!.image!![0])
        tvTitle.setText(product!!.title)
        txvDescription.setText(product!!.description)
        btnMoney.setText("$ ${product!!.price}")
        btnBack.setOnClickListener {
            navController!!.popBackStack()
        }
    }
}