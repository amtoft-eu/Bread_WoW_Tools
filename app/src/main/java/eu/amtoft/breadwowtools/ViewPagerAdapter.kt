package eu.amtoft.breadwowtools

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class ViewPagerAdapter(activity: AppCompatActivity, private val itemCount: Int): FragmentStateAdapter(activity){
    override fun getItemCount(): Int {
        return itemCount
    }

    override fun createFragment(position: Int): Fragment {
        if (position == 0 || position == 1 || position == 2){
        }
        return MountFragment.newInstance()

    }

}