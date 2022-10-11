#import <React/RCTViewManager.h>
#import "MarkerView.h"


#import "MapCoordinates.h"

@interface MarkerViewManager : RCTViewManager
@property(nonatomic, strong)MarkerView* markerView;
@end

@implementation MarkerViewManager

RCT_EXPORT_MODULE(MarkerView)

- (UIView *)view
{
    self.markerView =[[MarkerView alloc] init];
    return  self.markerView;
}

RCT_CUSTOM_VIEW_PROPERTY(location,MapCoordinates,UIView){
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * .2);
    dispatch_after(delay, dispatch_get_main_queue(), ^(void){
        MapCoordinates *coordinates = json;
        [self.markerView setCoordinates:coordinates];
        NSLog(@"##### Markers location updates");
    });

}

- hexStringToColor:(NSString *)stringToConvert
{
  NSString *noHashString = [stringToConvert stringByReplacingOccurrencesOfString:@"#" withString:@""];
  NSScanner *stringScanner = [NSScanner scannerWithString:noHashString];

  unsigned hex;
  if (![stringScanner scanHexInt:&hex]) return nil;
  int r = (hex >> 16) & 0xFF;
  int g = (hex >> 8) & 0xFF;
  int b = (hex) & 0xFF;

  return [UIColor colorWithRed:r / 255.0f green:g / 255.0f blue:b / 255.0f alpha:1.0f];
}

@end
