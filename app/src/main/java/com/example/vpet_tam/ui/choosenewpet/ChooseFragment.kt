package com.example.vpet_tam.ui.choosenewpet

import android.R
import android.R.attr.data
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vpet_tam.databinding.FragmentChooseBinding
import androidx.navigation.findNavController


class ChooseFragment : Fragment() {

  /*  companion object {
        fun newInstance() = ChooseFragment()
    }

    private val viewModel: ChooseViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_choose, container, false)
    }
*/

    private var _binding: FragmentChooseBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val chooseViewModel =
            ViewModelProvider(this).get(ChooseViewModel::class.java)

        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.typeNameTxt
        chooseViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/


        binding.btnStart.setOnClickListener{p1 ->
            p1.findNavController().navigate(com.example.vpet_tam.R.id.action_navigation_choose_to_navigation_home)
        }




        return root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}