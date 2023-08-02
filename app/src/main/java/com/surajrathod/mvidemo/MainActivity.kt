package com.surajrathod.mvidemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import com.surajrathod.mvidemo.adapter.UserAdapter
import com.surajrathod.mvidemo.databinding.ActivityMainBinding
import com.surajrathod.mvidemo.intent.MainIntent
import com.surajrathod.mvidemo.model.Users
import com.surajrathod.mvidemo.network.NetworkInterface
import com.surajrathod.mvidemo.viewmodel.MainViewModel
import com.surajrathod.mvidemo.viewstate.MainState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import org.koin.android.ext.android.get
import org.koin.android.ext.android.inject
import org.koin.android.scope.AndroidScopeComponent
import org.koin.androidx.scope.activityScope
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.qualifier.named
import org.koin.core.scope.Scope

class MainActivity : AppCompatActivity() , AndroidScopeComponent {

    override val scope: Scope by activityScope()
    private val hello by inject<String>(named("Hello"))
    private val bye by inject<String>(named("Bye"))
    private val vm by viewModel<MainViewModel>()

    //private val api = get<NetworkInterface>()     instant injection
    //private val api by inject<NetworkInterface>()       lazy injection

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //Toast.makeText(this,hello+" $bye",Toast.LENGTH_LONG).show()

        observeViewModel()
        loadInitialData()
        setupListeners()
    }

    private fun setupListeners() {
        binding.refreshSwipe.setOnRefreshListener {
            lifecycleScope.launch {
                vm.userIntent.send(MainIntent.fetchUser)
            }
        }
    }

    private fun loadInitialData() {
        lifecycleScope.launch {
            vm.userIntent.send(MainIntent.fetchUser)
        }
    }

    private fun observeViewModel() {
        lifecycleScope.launch {
            vm.state.collect{
                when(it){
                    is MainState.Idle ->{
                        binding.txtMainState.text = "Main state : Idle"
                    }
                    is MainState.Loading->{
                        binding.txtMainState.text = "Main state : Loading"
                        binding.refreshSwipe.isRefreshing = true
                    }
                    is MainState.Error ->{
                        binding.txtMainState.text = "Main state : Error"
                        binding.refreshSwipe.isRefreshing = false
                        Toast.makeText(this@MainActivity,it.error,Toast.LENGTH_LONG).show()
                    }
                    is MainState.Users->{
                        binding.txtMainState.text = "Main state : Users"
                        binding.refreshSwipe.isRefreshing = false
                        setupRv(it.users)
                    }
                    is MainState.ShowMsg->{
                        binding.txtMainState.text = "Main state Msg : ${it.msg}"
                        Toast.makeText(this@MainActivity,it.msg,Toast.LENGTH_LONG).show()
                        binding.refreshSwipe.isRefreshing = false
                    }
                }
            }
        }
    }

    private fun setupRv(users: Users) {
        binding.rvUsers.adapter = UserAdapter(users){
            lifecycleScope.launch {
                vm.userIntent.send(MainIntent.showMessage(it.email))
            }
        }
    }
}