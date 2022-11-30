export * from './CallOutViewNativeComponent';
import type { ViewStyle } from 'react-native';
import type { Coordinates } from './types';
interface CallOutViewProps {
    children?: Element;
    style: ViewStyle;
    region: Coordinates;
    elevation?: number;
}
export declare const CallOutView: (props: CallOutViewProps) => JSX.Element;
//# sourceMappingURL=CalloutView.d.ts.map