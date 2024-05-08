package com.example.vpet_tam.view

import android.app.Application
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.example.vpet_tam.databinding.FragmentChooseBinding
import com.example.vpet_tam.view.SettingsFragment.Companion.save_img
import com.example.vpet_tam.viewmodel.ChooseViewModel
import com.example.vpet_tam.viewmodel.DbViewModel
import com.example.vpet_tam.viewmodel.HomeViewModel
import kotlinx.coroutines.launch


class ChooseFragment : Fragment() {

    companion object {
        fun newInstance() = ChooseFragment()


    }
    //решение без ViewModelProvider.get()
    private val chooseViewModel: ChooseViewModel by activityViewModels()
    private val dbViewModel: DbViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentChooseBinding? = null
    private var image :Int = 0
    private var name :String = ""
    private var id: Int = 0

    // This property is only valid between onCreateView and  onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseBinding.inflate(inflater, container, false)
        val root: View = binding.root

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //проверка на наличие питомца

        //dbViewModel.getPetDetails(requireActivity().application)

        binding.btnStart.setOnClickListener{ it ->
            //проверка строки на пустую и только из пробелов
            //получение айди питомца
            if (name.isNotEmpty() && image != 0) { it as View
                dbViewModel.insertData(requireActivity().application, name, "1", "100", "100", "100")
                dbViewModel.getId(requireActivity().application, name)
                homeViewModel.onSaveId(id)
                Toast.makeText(activity,"inserted in db", Toast.LENGTH_SHORT).show()
                it.findNavController().navigate(com.example.vpet_tam.R.id.action_navigation_choose_to_navigation_home)
            }
            else {
                Toast.makeText(activity,"name or image is null!", Toast.LENGTH_SHORT).show()
            }
        }
        binding.typeNameTxt.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                name = binding.typeNameTxt.text.toString().trim()
                chooseViewModel.inputName(name)

            }
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })

        with(binding) {
           iconBear.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
            iconCat.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
            iconRabbit.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
            iconDog.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
            iconTurtle.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
            iconFrog.setOnClickListener { item ->
                chooseViewModel.onIconClicked(item)

            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                chooseViewModel.chooseState.observe(viewLifecycleOwner, Observer {
                    image = it
                    homeViewModel.onSaveImg(it)

                })
                chooseViewModel.nameState.observe(viewLifecycleOwner, Observer {
                    name = it
                })
            }
    }
}
}

