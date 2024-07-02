package com.mx.testcore.core

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.wifi.WifiManager
import android.telephony.TelephonyManager

/**
 * Check device's network connectivity and speed
 * Created by Angel Vazquez on 10/12/15.
 * Updated by Angel Vazquez on 27/05/19.
 *
 * @author Angel Vazquez<aovazquez4037@gmail.com>
 * @version 1.0.0
 *
 * Clase encargada de consultar el status de la red
 */
class Connectivity {

    companion object {

        /**
         * Get the network info
         * @param context
         * @return
         */
        fun getNetworkInfo (context:Context): NetworkInfo? {
            val cm : ConnectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            return cm.activeNetworkInfo
        }

        /**
         * Check if there is any connectivity
         * @param context
         * @return
         */
        fun isConnected(context: Context) : Boolean {
            getNetworkInfo(context)?.let {
                return  it.isConnected
            }
            return false
        }

        /**
         * Check if there is any connectivity to a Wifi network
         * @param context
         * @return
         */
        fun isConnectedWifi(context: Context) : Boolean {
            val info : NetworkInfo? = getNetworkInfo(context)!!
            return (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_WIFI)
        }

        /**
         * Check if there is any connectivity to a mobile network
         * @param context
         * @return
         */
        fun isConnectedMobile(context: Context) : Boolean {
            val info : NetworkInfo? = getNetworkInfo(context)!!
            return (info != null && info.isConnected && info.type == ConnectivityManager.TYPE_MOBILE)
        }

        /**
         * Check if there is fast connectivity
         * @param context
         * @return
         */
        fun isConnectedFast(context: Context) : Boolean {
            val info : NetworkInfo? = getNetworkInfo(context)!!
            return (info != null && info.isConnected && isConnectionFast(info.type, info.subtype))
        }

        /**
         * Check if the connection is fast
         * @param type
         * @param subType
         * @return
         */
        fun isConnectionFast(type:Int, subType:Int): Boolean {

            if ( type == ConnectivityManager.TYPE_WIFI ) {
                return true
            } else if( type == ConnectivityManager.TYPE_MOBILE ) {

                when ( subType ) {
                    TelephonyManager.NETWORK_TYPE_1xRTT -> return false
                    TelephonyManager.NETWORK_TYPE_CDMA -> return false
                    TelephonyManager.NETWORK_TYPE_EDGE -> return false
                    TelephonyManager.NETWORK_TYPE_EVDO_0 -> return false
                    TelephonyManager.NETWORK_TYPE_EVDO_A -> return false
                    TelephonyManager.NETWORK_TYPE_GPRS -> return false
                    TelephonyManager.NETWORK_TYPE_HSDPA -> return true
                    TelephonyManager.NETWORK_TYPE_HSPA -> return true
                    TelephonyManager.NETWORK_TYPE_HSUPA -> return true
                    TelephonyManager.NETWORK_TYPE_UMTS -> return true
                    /*
                     * Above API level 7, make sure to set android:targetSdkVersion
                     * to appropriate level to use these
                     */
                    TelephonyManager.NETWORK_TYPE_EHRPD -> return true
                    TelephonyManager.NETWORK_TYPE_EVDO_B -> return true
                    TelephonyManager.NETWORK_TYPE_HSPAP -> return true
                    TelephonyManager.NETWORK_TYPE_IDEN -> return false
                    TelephonyManager.NETWORK_TYPE_LTE -> return true
                    TelephonyManager.NETWORK_TYPE_UNKNOWN -> return true
                    else -> return false
                }
            } else {
                return false
            }
        }

        fun getMacAddressDevice(context:Context):String {
            var wifiManager:WifiManager = context.getSystemService(Context.WIFI_SERVICE) as WifiManager
            var wInfo = wifiManager.connectionInfo
            var macAddress: String = wInfo.macAddress
            return macAddress.replace(":","")
        }

    }
}