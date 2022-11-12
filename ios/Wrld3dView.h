// This guard prevent this file to be compiled in the old architecture.
#ifdef RCT_NEW_ARCH_ENABLED
#import <React/RCTViewComponentView.h>
#import <UIKit/UIKit.h>

#ifndef Wrld3dViewNativeComponent_h
#define Wrld3dViewNativeComponent_h

NS_ASSUME_NONNULL_BEGIN

@interface Wrld3dView : RCTViewComponentView
@end

NS_ASSUME_NONNULL_END

#endif /* Wrld3dViewNativeComponent_h */
#endif /* RCT_NEW_ARCH_ENABLED */
