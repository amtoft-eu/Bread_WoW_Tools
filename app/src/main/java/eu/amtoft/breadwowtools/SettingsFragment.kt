package eu.amtoft.breadwowtools

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.view.*


class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private val PRIVATE_MODE = 0
    private val PREF_NAME = "OPTIONS"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        rootView.themeSwitch.isChecked = sharedPref.getBoolean("THEME", false)

        rootView.themeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (sharedPref.getBoolean("THEME", false) != isChecked) {

                val editor = sharedPref.edit()

                if (isChecked) {
                    requireActivity().setTheme(R.style.HordeTheme)
                } else {
                    requireActivity().setTheme(R.style.AllianceTheme)
                }
                editor.putBoolean("THEME", isChecked)
                editor.apply()
                activity?.recreate()
            }
        }

        rootView.regionSwitch.isChecked = sharedPref.getBoolean("REGION", false)

        rootView.regionSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (sharedPref.getBoolean("REGION", false) != isChecked) {

                val editor = sharedPref.edit()
                editor.putBoolean("REGION", isChecked)
                editor.apply()
            }
        }

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            SettingsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }


}