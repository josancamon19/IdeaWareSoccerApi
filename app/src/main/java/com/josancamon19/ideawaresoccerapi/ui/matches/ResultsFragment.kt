package com.josancamon19.ideawaresoccerapi.ui.matches

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout.VERTICAL
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.DividerItemDecoration
import com.josancamon19.ideawaresoccerapi.R
import com.josancamon19.ideawaresoccerapi.adapters.lists.ResultsListAdapter
import com.josancamon19.ideawaresoccerapi.databinding.FragmentResultsBinding
import com.josancamon19.ideawaresoccerapi.ui.viewmodels.MainActivityViewModel
import com.josancamon19.ideawaresoccerapi.ui.viewmodels.ResultsViewModel
import com.josancamon19.ideawaresoccerapi.di.viewmodel.ViewModelFactory
import dagger.android.support.DaggerFragment
import javax.inject.Inject

class ResultsFragment : DaggerFragment() {

    private lateinit var binding: FragmentResultsBinding

    private lateinit var mainViewModel: MainActivityViewModel

    private lateinit var viewModel: ResultsViewModel
    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    @Inject
    lateinit var fixturesAdapter: ResultsListAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        setupRecycler()
        setupViewModel()
        return binding.root
    }

    private fun setupRecycler() {
        binding.resultsRecycler.setHasFixedSize(true)
        binding.resultsRecycler.adapter = fixturesAdapter
        binding.resultsRecycler.addItemDecoration(DividerItemDecoration(context, VERTICAL))
    }

    private fun setupViewModel() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(ResultsViewModel::class.java)
        viewModel.resultsWithHeaders.observe(this, Observer {
            fixturesAdapter.submitList(it)
        })
        viewModel.pbVisibility.observe(this, Observer {
            when (it) {
                true -> binding.pb.visibility = View.VISIBLE
                false -> binding.pb.visibility = View.GONE
                else -> binding.pb.visibility = View.GONE
            }
        })
        activity?.let {
            mainViewModel = ViewModelProviders.of(it).get(MainActivityViewModel::class.java)
            mainViewModel.competitionSelected.observe(it, Observer { competition ->
                viewModel.filterByCompetition(competition)

            })
        }
        viewModel.competitions.observe(this, Observer {
            mainViewModel.setFilterOptions(it)
        })
    }
}