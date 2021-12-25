package com.duodevloopers.appscheduler.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.duodevloopers.appscheduler.adapter.RecordAdapter
import com.duodevloopers.appscheduler.databinding.FragmentRecordBinding
import com.duodevloopers.appscheduler.viewmodel.MainActivityViewModel

class RecordFragment : Fragment() {

    private lateinit var binding: FragmentRecordBinding

    private lateinit var mainActivityViewModel: MainActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        mainActivityViewModel =
            ViewModelProvider(requireActivity())[MainActivityViewModel::class.java]
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentRecordBinding.inflate(layoutInflater)

        requireActivity().title = "Schedule Record"

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainActivityViewModel.getAllScheduledApps().observe(viewLifecycleOwner, Observer {
            val adapter = RecordAdapter(it)
            binding.appList.adapter = adapter
        })
    }
}