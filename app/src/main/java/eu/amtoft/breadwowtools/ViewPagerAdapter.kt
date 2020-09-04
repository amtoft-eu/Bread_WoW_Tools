package eu.amtoft.breadwowtools

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity, private val itemCount: Int): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> CharacterFragment.newInstance()
            1 -> MountFragment.newInstance()
            2 -> SettingsFragment.newInstance()
            else -> CharacterFragment.newInstance()
        }

    }

}