package com.example.vpet_tam.ui.choosenewpet

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.vpet_tam.databinding.FragmentChooseBinding


class ChooseFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseFragment()
    }

    private val chooseViewModel: ChooseViewModel by activityViewModels()

    private var _binding: FragmentChooseBinding? = null
    private var ch_image :Int = 0
    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        val root: View = binding.root

       /* val textView: TextView = binding.typeNameTxt
        chooseViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }*/

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.btnStart.setOnClickListener{
            p1 -> p1.findNavController().navigate(com.example.vpet_tam.R.id.action_navigation_choose_to_navigation_home)
            //chooseViewModel.onButtonClicked()
        }
        binding.iconBear.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }
        binding.iconCat.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }
        binding.iconRabbit.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }
        binding.iconDog.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }
        binding.iconTurtle.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }
        binding.iconFrog.setOnClickListener {item ->
            chooseViewModel.onIconClicked(item)
        }

        /*viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chooseViewModel.chooseState.observe(viewLifecycleOwner) {
                    ch_image = it
                }
            }
    }*/
    /*override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
}
}