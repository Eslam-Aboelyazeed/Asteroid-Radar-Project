package com.udacity.asteroidradar.main

import android.os.Build
import android.os.Bundle
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.squareup.picasso.Picasso
import com.udacity.asteroidradar.*
import com.udacity.asteroidradar.databinding.FragmentMainBinding
import kotlinx.android.synthetic.main.fragment_detail.*

@RequiresApi(Build.VERSION_CODES.N)
class MainFragment2 : Fragment() {

    private val viewModel:MainViewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }

    private lateinit var adapter: RecyclerViewAdapter

    private var _binding: FragmentMainBinding? = null

    private val binding get() = _binding!!

    @RequiresApi(Build.VERSION_CODES.N)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {

            _binding = FragmentMainBinding.inflate(inflater, container, false)

        binding.lifecycleOwner = this


        setHasOptionsMenu(true)


        adapter = RecyclerViewAdapter(AsteroidListener {

            findNavController().navigate(MainFragment2Directions.actionShowDetail(it))


        })

        binding.asteroidRecycler.adapter = adapter

        viewModel.p.observe(viewLifecycleOwner){

            Picasso.with(context)
            .load(it?.url)
            .placeholder(R.drawable.placeholder_picture_of_day)
            .into(binding.activityMainImageOfTheDay)

            binding.activityMainImageOfTheDay.contentDescription = it?.title

        }

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

            viewModel.w.observe(viewLifecycleOwner, Observer {

                adapter.submitList(it)

            })

        }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.main_overflow_menu, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

            when (item.itemId) {
                R.id.show_saved_menu -> viewModel.type.value = 1
                R.id.show_today_menu -> viewModel.type.value = 2
                R.id.show_week_menu -> viewModel.type.value = 3
            }

        return true
    }
}
