#import <Foundation/Foundation.h>
#import <VisionCamera/FrameProcessorPlugin.h>
#import <VisionCamera/FrameProcessorPluginRegistry.h>

#if __has_include("VisionCameraOcr/VisionCameraOcr-Swift.h")
#import "VisionCameraOcr/VisionCameraOcr-Swift.h"
#else
#import "VisionCameraOcr-Swift.h"
#endif

VISION_EXPORT_SWIFT_FRAME_PROCESSOR(OCRFrameProcessorPlugin, scanOCR)
