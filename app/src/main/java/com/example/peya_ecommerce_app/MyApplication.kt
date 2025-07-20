package com.example.peya_ecommerce_app


import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class MyApplication : Application()  // instancia en el manifest solo para configuración de Hilt
