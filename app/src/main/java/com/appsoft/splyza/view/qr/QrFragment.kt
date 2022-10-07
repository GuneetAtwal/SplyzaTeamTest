package com.appsoft.splyza.view.qr

import android.graphics.Bitmap
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.appsoft.splyza.R
import com.appsoft.splyza.databinding.FragmentQrBinding
import com.appsoft.splyza.viewmodel.HomeViewModel
import com.google.zxing.BarcodeFormat
import com.google.zxing.qrcode.QRCodeWriter

class QrFragment : Fragment() {

    private val viewModel: HomeViewModel by activityViewModels()
    private lateinit var binding: FragmentQrBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentQrBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupHeader()
        viewModel.inviteUrl?.let { inviteUrl ->
            binding.ivQrCode.setImageBitmap(getQrCodeBitmap(inviteUrl))
        }
    }

    private fun setupHeader() {
        binding.customToolbar.apply {
            tvBack.setOnClickListener {
                activity?.onBackPressed()
            }
            tvTitle.text = getString(R.string.my_qr_code)
        }
    }

    private fun getQrCodeBitmap(content: String): Bitmap {
        val size = 512
        val bits = QRCodeWriter().encode(content, BarcodeFormat.QR_CODE, size, size)
        return Bitmap.createBitmap(size, size, Bitmap.Config.RGB_565).also {
            for (x in 0 until size) {
                for (y in 0 until size) {
                    it.setPixel(x, y, if (bits[x, y]) Color.BLACK else Color.WHITE)
                }
            }
        }
    }

    companion object {
        fun newInstance() = QrFragment()
    }
}