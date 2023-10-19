package com.visioncameraocr

import com.facebook.react.ReactPackage
import com.facebook.react.bridge.NativeModule
import com.facebook.react.bridge.ReactApplicationContext
import com.facebook.react.uimanager.ViewManager
import com.mrousavy.camera.frameprocessor.FrameProcessorPluginRegistry

class VisionCameraOcrPackage : ReactPackage {
    companion object {
        init {
            FrameProcessorPluginRegistry.addFrameProcessorPlugin("scanOCR") { options ->
                OCRFrameProcessorPlugin(options)
            }
        }
    }

    override fun createNativeModules(reactContext: ReactApplicationContext): List<NativeModule> {
        return emptyList()
    }

    override fun createViewManagers(reactContext: ReactApplicationContext): List<ViewManager<*, *>> {
        return emptyList()
    }
}
