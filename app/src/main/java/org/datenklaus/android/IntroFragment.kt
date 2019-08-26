package org.datenklaus.android

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_custom.*


class IntroFragment : Fragment() {
    private lateinit var listener: MainActivity

    companion object {
        fun newInstance() = IntroFragment().apply {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_custom, container, false)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MainActivity
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        inputAddress.onFocusChangeListener = OnFocusChangeListener { _, hasFocus ->
            if (hasFocus) {
                inputAddress.hint = getString(R.string.datenklaus_url)
            } else {
                inputAddress.hint = ""
            }
        }
        buttonConnect.setOnClickListener {
            //var url = getString(R.string.datenklaus_url)
            var url: String? = null
            inputAddress.text?.let {
                if (it.isNotEmpty())
                    url = it.toString()
            }
            listener.startWebView(url)
        }
    }
}
