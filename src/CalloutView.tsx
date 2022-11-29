import Wrld3dCallOutView from './CallOutViewNativeComponent';
export * from './CallOutViewNativeComponent';
import * as React from 'react';
import type { ViewStyle } from 'react-native';
import type { Coordinates } from './types';

interface CallOutViewProps {
  children?: Element;
  style: ViewStyle;
  region: Coordinates;
  elevation?: number;
}

export const CallOutView = (props: CallOutViewProps) => {
  return <Wrld3dCallOutView {...props} />;
};
