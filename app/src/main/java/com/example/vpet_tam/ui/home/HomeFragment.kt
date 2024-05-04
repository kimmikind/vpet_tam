package com.example.vpet_tam.ui.home






import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import com.example.vpet_tam.R
import com.example.vpet_tam.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {

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
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

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

        binding.dropdownFood.setOnClickListener {
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
        }

        binding.btnSettings.setOnClickListener{p2 ->
            p2.findNavController().navigate(com.example.vpet_tam.R.id.action_navigation_home_to_navigation_settings)
        }


        return root
    }
 /*   override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    fun verticalDropDownIconMenu(view: View?) {
        if (dropDownMenuIconItem!!.visibility == View.VISIBLE) {
            dropDownMenuIconItem!!.visibility = View.INVISIBLE
        } else {
            dropDownMenuIconItem!!.visibility = View.VISIBLE
        }
    }

    fun menuItemClick(view: View?) {
        dropDownMenuIconItem!!.visibility = View.INVISIBLE
    }
*/
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

