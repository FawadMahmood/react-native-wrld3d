//
//  MarkerView.m
//  react-native-wrld3d
//
//  Created by Game on 11/10/2022.
//

#if __has_include("React/RCTViewManager.h")
#import "React/RCTViewManager.h"
#else
#import "RCTViewManager.h"
#endif

#import <UIKit/UIKit.h>
#import <AVKit/AVKit.h>

#import "MapCoordinates.h"

@import Wrld;



@interface MarkerView : UIView
@property (nonatomic, strong) WRLDMapView *map;
@property (nonatomic) WRLDPositioner *positioner;
@property (nonatomic,assign) MapCoordinates *location;

@property (nonatomic,assign) CLLocationDegrees longitude;
@property (nonatomic,assign) CLLocationDegrees latitude;

-(void) initializePointerWithPositioner;
-(void) positionerDidChange;


@end

