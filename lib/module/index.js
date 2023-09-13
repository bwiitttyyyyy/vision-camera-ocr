import { VisionCameraProxy } from 'react-native-vision-camera';
const plugin = VisionCameraProxy.getFrameProcessorPlugin('scanOCR');

/**
 * Scans OCR.
 */

export function scanOCR(frame) {
  'worklet';

  return plugin === null || plugin === void 0 ? void 0 : plugin.call(frame);
}
//# sourceMappingURL=index.js.map