import { NativeModules } from 'react-native';

const { BackgroundLocation } = NativeModules;

export default {
  PERMISSIONS: BackgroundLocation.PERMISSIONS,
  startTracking: (serverUrl) => BackgroundLocation.startTracking(serverUrl),
  stopTracking: () => BackgroundLocation.stopTracking(),
};
