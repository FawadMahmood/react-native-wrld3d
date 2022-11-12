#import <React/RCTViewManager.h>
#import <React/RCTUIManager.h>
#import "RCTBridge.h"
#import "Utils.h"

@interface Wrld3dViewManager : RCTViewManager
@end

@implementation Wrld3dViewManager

RCT_EXPORT_MODULE(Wrld3dView)

- (UIView *)view
{
  return [[UIView alloc] init];
}

RCT_CUSTOM_VIEW_PROPERTY(color, NSString, UIView)
{
  [view setBackgroundColor: [Utils hexStringToColor:colorToConvert]];
}

@end
