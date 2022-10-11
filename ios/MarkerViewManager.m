#import <React/RCTViewManager.h>
#import "MarkerView.h"


#import "MapCoordinates.h"



@interface MarkerViewManager : RCTViewManager
@property(nonatomic, strong)MarkerView* markerView;
@property (nonatomic,strong) MapCoordinates *coordinates;
@end

@implementation MarkerViewManager

RCT_EXPORT_MODULE(MarkerView)

- (UIView *)view
{
    self.markerView =[[MarkerView alloc] init];
//    NSLog(@"iniit marker view with props %@",self.coordinates);
   
    return  self.markerView;
}

RCT_CUSTOM_VIEW_PROPERTY(location,NSDictionary,UIView){

//    NSDictionary *records =json;
    
    CLLocationDegrees latitude =(CLLocationDegrees) [[json objectForKey:@"latitude"] doubleValue];
    CLLocationDegrees longitude =(CLLocationDegrees) [[json objectForKey:@"longitude"] doubleValue];
    
//    MapCoordinates *coordinates;
//    [coordinates setLatitude:[records[@"latitude"] doubleValue]];
//    [coordinates setLongitude:[records[@"longitude"] doubleValue]];
    

    


//    CLLocationDegrees latitude =(CLLocationDegrees) json[@"latitude"];
//    CLLocationDegrees longitude = json[@"longitude"];

//    MapCoordinates* coordinates =json;
    

    
//    MapCoordinates *coordinates;
//    [coordinates setLatitude:latitude];
//    [coordinates setLongitude:latitude];
    
//    NSLog(@"data eceived %@",coordinates);
//
 
    
    MarkerView *markerView =(MarkerView*) view;
    [markerView setLatitude:latitude];
    [markerView setLongitude:longitude];
    
    NSLog(@"%f",markerView.latitude);
    NSLog(@"%f",markerView.longitude);
    
}

//RCT_EXPORT_VIEW_PROPERTY(location, MapCoordinates)

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
