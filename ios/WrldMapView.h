#if __has_include("React/RCTViewManager.h")
#import "React/RCTViewManager.h"
#else
#import "RCTViewManager.h"
#endif

#import "MarkerView.h"

#import <UIKit/UIKit.h>
#import <AVKit/AVKit.h>

#import "MapCoordinates.h"

@import Wrld;


@interface WrldMapView: UIView <WRLDMapViewDelegate>
@property(nonatomic, strong)UIViewController* myViewController;
@property (nonatomic, strong) NSMutableArray<MarkerView *> *markers;
@property (nonatomic, strong) NSMutableArray<MapCoordinates *> *markersCoords;
@property (nonatomic, strong) WRLDMapView *map;
@property (nonatomic, assign) CLLocationDegrees longitude;
@property (nonatomic, assign) CLLocationDegrees latitude;
@property (nonatomic, assign) double zoomLevel;
@end
