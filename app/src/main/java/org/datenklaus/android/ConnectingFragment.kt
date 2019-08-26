package org.datenklaus.android

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.Animation
import android.view.animation.RotateAnimation
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_connecting.*


class ConnectingFragment : Fragment() {
    private lateinit var listener: MainActivity

    companion object {
        fun newInstance() = ConnectingFragment().apply { }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        listener = context as MainActivity
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_connecting, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rotate = RotateAnimation(
            0f,
            360f,
            Animation.RELATIVE_TO_SELF,
            0.5f,
            Animation.RELATIVE_TO_SELF,
            0.5f
        )
        rotate.duration = 2500
        rotate.repeatCount = RotateAnimation.INFINITE
        rotate.interpolator = AccelerateDecelerateInterpolator()
        imageView.startAnimation(rotate)
        listener.loadUrl()
    }

    fun setLoading() {
        textView.text = getString(R.string.loading_page)
    }

    fun setError() {
        textView.text = getString(R.string.conn_error)
        context?.let {
            textView.setTextColor(ContextCompat.getColor(it, R.color.design_default_color_error))
        }
    }

}
