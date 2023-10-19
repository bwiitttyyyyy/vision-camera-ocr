import { VisionCameraProxy } from 'react-native-vision-camera';
const plugin = VisionCameraProxy.initFrameProcessorPlugin('scanOCR');

/**
 * Scans OCR.
 */

export function scanOCR(frame) {
  'worklet';

  return plugin?.call(frame);
}
//# sourceMappingURL=index.js.map