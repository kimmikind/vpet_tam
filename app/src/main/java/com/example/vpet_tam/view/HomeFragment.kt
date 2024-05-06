package com.example.vpet_tam.view


import android.app.Application
import android.app.Dialog
import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentHomeBinding
import com.example.vpet_tam.viewmodel.ChooseViewModel
import com.example.vpet_tam.viewmodel.DbViewModel
import com.example.vpet_tam.viewmodel.HomeViewModel


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private val chooseViewModel: ChooseViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()
    private val dbViewModel: DbViewModel by activityViewModels()




    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and  onDestroyView.
    private val binding get() = _binding!!

    var dropDownMenuIconItem: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        binding.btnSettings.setOnClickListener{p2 ->
            p2.findNavController().navigate(R.id.action_navigation_home_to_navigation_settings)
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        binding.btnEvent.setOnClickListener{
            //получить строку из бд
            dbViewModel.getEvent(requireActivity().application)
            dbViewModel.liveDataEvent?.observe(viewLifecycleOwner, Observer {
                showDialog(it.Event)
            })
            //showDialog("ggh")
        }
        return root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //передаю нажатие выбранной эконки питомца из choosefragment
        chooseViewModel.chooseState.observe(viewLifecycleOwner, Observer<Int> { item ->
            // Update the UI
            with(binding) {
                when (item) {
                    1 -> petMain.setImageResource(R.drawable.img_art_bear)
                    2 -> petMain.setImageResource(R.drawable.img_art_cat)
                    3 -> petMain.setImageResource(R.drawable.img_art_bunny)
                    4 -> petMain.setImageResource(R.drawable.img_art_dog)
                    5 -> petMain.setImageResource(R.drawable.img_art_turtle);
                    6 -> petMain.setImageResource(R.drawable.img_art_frog);
                    else -> Toast.makeText(activity,"rrr",Toast.LENGTH_SHORT).show()
                } }

        })
        //observer
        dbViewModel.liveDataPet?.observe(viewLifecycleOwner, Observer {
        //dbViewModel.getPetDetails(context = Application(),id)?.observe(viewLifecycleOwner, Observer {
                    if (it == null) {
                        Toast.makeText(activity,"id of pet is null", Toast.LENGTH_SHORT).show()

                    } else {
                        with(binding) {
                            setName.text = it.Petname
                            setAge.text = it.Petage
                            statHunger.text = it.Pethunger
                            statHealth.text = it.Pethealth
                            statEnergy.text = it.Petenergy
                        }
                    }
                })

    }

    /* override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }*/
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu);
        val item = menu.findItem(R.id.button_item)
        val btn1 = item.actionView!!.findViewById<Button>(R.id.button1)
        btn1.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconFood
            homeViewModel.onButtonClicked(dropDownMenuIconItem)
        }
        val btn2 = item.actionView!!.findViewById<Button>(R.id.button2)
        btn2.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconCure
            homeViewModel.onButtonClicked(dropDownMenuIconItem)
        }
        val btn3 = item.actionView!!.findViewById<Button>(R.id.button3)
        btn3.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconSleep
            homeViewModel.onButtonClicked(dropDownMenuIconItem)
        }

    }

    fun showDialog(title: String) {
        val dialog = Dialog(activity as AppCompatActivity)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setCancelable(false)
        dialog.setContentView(R.layout.dialog_event)
        val body = dialog.findViewById(R.id.input_event) as TextView
        body.text = title
        val okBtn = dialog.findViewById(R.id.ok_button) as Button
        okBtn.setOnClickListener {
            dialog.dismiss()
        }
        dialog.show()
    }


}


