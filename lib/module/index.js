import { VisionCameraProxy } from 'react-native-vision-camera';
const plugin = VisionCameraProxy.getFrameProcessorPlugin('scanOCR');

/**
 * Scans OCR.
 */

export function scanOCR(frame) {
  'worklet';

  return plugin?.call(frame);
}
//# sourceMappingURL=index.js.map