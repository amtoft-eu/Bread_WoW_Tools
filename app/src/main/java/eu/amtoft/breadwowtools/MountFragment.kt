package eu.amtoft.breadwowtools

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MountFragment : Fragment() {

    private lateinit var adapter: MountAdapter
    private lateinit var linearLayoutManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_mount, container, false)

        val mountRecycler = rootView.findViewById(R.id.mountRecycler) as RecyclerView
        linearLayoutManager = LinearLayoutManager(context)

        mountRecycler.layoutManager = linearLayoutManager

        var mountList: ArrayList<Mount> = ArrayList()
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())
        mountList.add(Mount())

        adapter = MountAdapter(mountList)
        mountRecycler.adapter = adapter

        return rootView
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MountFragment().apply {
                arguments = Bundle().apply {
                }

            }
    }

}