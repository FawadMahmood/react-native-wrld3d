#import <React/RCTViewManager.h>
#import "WrldMapView.h"
#import "MarkerView.h"

@interface Wrld3dViewManager : RCTViewManager

@property(nonatomic, strong)WrldMapView* mapView;
@end

@implementation Wrld3dViewManager

RCT_EXPORT_MODULE(Wrld3dView)

- (UIView *)view
{
    self.mapView =[[WrldMapView alloc] init];
    return  self.mapView;
}


RCT_CUSTOM_VIEW_PROPERTY(initialCenter,NSDictionary,UIView){
    CLLocationDegrees latitude =(CLLocationDegrees) [[json objectForKey:@"latitude"] doubleValue];
    CLLocationDegrees longitude =(CLLocationDegrees) [[json objectForKey:@"longitude"] doubleValue];
    
    WrldMapView *mapView =(WrldMapView*) view;
    [mapView setLatitude:latitude];
    [mapView setLongitude:longitude];
}


RCT_CUSTOM_VIEW_PROPERTY(color, NSString, UIView)
{
  [view setBackgroundColor:[self hexStringToColor:json]];
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
