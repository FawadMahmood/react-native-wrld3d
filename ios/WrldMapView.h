#if __has_include("React/RCTViewManager.h")
#import "React/RCTViewManager.h"
#else
#import "RCTViewManager.h"
#endif

#import <UIKit/UIKit.h>
#import <AVKit/AVKit.h>


@interface WrldMapView : UIView
@property(nonatomic, strong)UIViewController* myViewController;
@end
