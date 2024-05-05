package com.example.vpet_tam.ui.home



import android.content.DialogInterface
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentHomeBinding
import com.example.vpet_tam.ui.choosenewpet.ChooseFragment
import com.example.vpet_tam.ui.choosenewpet.ChooseViewModel


class HomeFragment : Fragment() {
    companion object {
        fun newInstance() = HomeFragment()
    }
    private val choosemodel: ChooseViewModel by activityViewModels()
    private val homeViewModel: HomeViewModel by activityViewModels()

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    var dropDownMenuIconItem: LinearLayout? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
       /* val homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)*/

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

      /*  binding.buttonFood.setOnClickListener {
            var wrapper: Context =  ContextThemeWrapper(context, R.style.CustomPopUpStyle)
            val popup = PopupMenu(wrapper, view)
            //val popup = PopupMenu(context, it)
            val inflater: MenuInflater = popup.menuInflater
            inflater.inflate(R.menu.menu_food, popup.menu)
            popup.setOnMenuItemClickListener { item ->
                when (item.itemId) {
                    R.id.one -> {
                        // Handle item 1 click
                        Toast.makeText(activity,"kkkk",Toast.LENGTH_SHORT).show()
                        true
                        }

                    R.id.two ->                 // Handle item 2 click
                        true

                    else -> false
                }
            }
            popup.show()


        }*/
       /* binding.spinnerFood.adapter = context?.let {
            FoodArrayAdapter(
                it,
                listOf(
                    Food(R.drawable.img_a),
                    Food(R.drawable.img_e),
                    Food(R.drawable.img_t)
                )
            )
        }*/
       /* binding.dropdownFood.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconFood
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }

        binding.dropdownCure.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconCure
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }
        binding.dropdownSleep.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconSleep
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }*/

        binding.btnSettings.setOnClickListener{p2 ->
            p2.findNavController().navigate(com.example.vpet_tam.R.id.action_navigation_home_to_navigation_settings)
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbar)
        setHasOptionsMenu(true)

        binding.btnEvent.setOnClickListener{

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            /*builder
                .setMessage("I am the message")
                .setTitle("I am the title")
                .setPositiveButton("Start") { dialog, id ->
                    // START THE GAME!
                    binding.textView.text ="1234"
                }
            */
            val inflater = requireActivity().layoutInflater;

            // Inflate and set the layout for the dialog.
            // Pass null as the parent view because it's going in the dialog
            // layout.
            builder.setView(inflater.inflate(R.layout.dialog_event, null))
                // Add action buttons.
                .setPositiveButton(R.string.ok_event,
                    DialogInterface.OnClickListener { dialog, id ->
                        // Sign in the user.
                    })
                /*.setNegativeButton(R.string.ok_event,
                    DialogInterface.OnClickListener { dialog, id ->

                    })*/
            val dialog: AlertDialog = builder.create()
            dialog.show()
            dialog.getWindow()?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))

            val positive_button = dialog.getButton(DialogInterface.BUTTON_POSITIVE)
            val negative_button = dialog.getButton(DialogInterface.BUTTON_NEGATIVE)

            if (positive_button != null) {
                positive_button.textSize = requireContext().resources.getDimension(R.dimen.btn_ok)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.press_start_2p)
                positive_button.setTypeface(typeface)
                positive_button.setTextColor(
                    requireContext().resources
                        .getColor(R.color.main)
                )
            }
            if (negative_button != null) {
                negative_button.textSize = requireContext().resources.getDimension(R.dimen.btn_ok)
                val typeface = ResourcesCompat.getFont(requireContext(), R.font.press_start_2p)
                negative_button.setTypeface(typeface)
                negative_button.setTextColor(
                    requireContext().resources
                        .getColor(R.color.main)
                )
            }

        }

        return root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        //передаю нажатие выбранной эконки питомца из choosefragment
        choosemodel.chooseState.observe(viewLifecycleOwner, Observer<Int> { item ->
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
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_items, menu);
        val item = menu.findItem(R.id.button_item)
        val btn1 = item.actionView!!.findViewById<Button>(R.id.button1)
        btn1.setOnClickListener {
            /*Toast.makeText(
                activity,
                "Toolbar Button Clicked!",
                Toast.LENGTH_SHORT
            ).show()
             */
            dropDownMenuIconItem = binding.dropdownIconFood
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }

        val btn2 = item.actionView!!.findViewById<Button>(R.id.button2)
        btn2.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconCure
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }
        val btn3 = item.actionView!!.findViewById<Button>(R.id.button3)
        btn3.setOnClickListener {
            dropDownMenuIconItem = binding.dropdownIconSleep
            if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
                dropDownMenuIconItem!!.visibility = View.INVISIBLE
            } else {
                dropDownMenuIconItem!!.visibility = View.VISIBLE
            }
        }

    }

}


