package eu.amtoft.breadwowtools

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_settings.view.*
import java.lang.reflect.Method


class SettingsFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    private var PRIVATE_MODE = 0
    private val PREF_NAME = "THEME"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val rootView = inflater.inflate(R.layout.fragment_settings, container, false)

        val sharedPref: SharedPreferences = requireActivity().getSharedPreferences(PREF_NAME, PRIVATE_MODE)

        rootView.themeSwitch.isChecked = sharedPref.getBoolean(PREF_NAME, false)

        rootView.themeSwitch.setOnCheckedChangeListener { _, isChecked ->

            if (sharedPref.getBoolean(PREF_NAME, false) != isChecked) {

                val editor = sharedPref.edit()

                if (isChecked) {
                    requireActivity().setTheme(R.style.HordeTheme)
                } else {
                    requireActivity().setTheme(R.style.AllianceTheme)
                }
                editor.putBoolean(PREF_NAME, isChecked)
                editor.apply()
                activity?.recreate()
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