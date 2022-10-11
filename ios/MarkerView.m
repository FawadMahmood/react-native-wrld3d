//
//  MarkerView.m
//  react-native-wrld3d
//
//  Created by Game on 11/10/2022.
//
#import "MarkerView.h"
#import <AVFoundation/AVFoundation.h>
#import <AVKit/AVKit.h>
@import Wrld;


#import <Foundation/Foundation.h>

@implementation MarkerView
- (instancetype)init
{
    self = [super init];
    return self;
}

-(void) setMapReference:(WRLDMapView *)mapView{
    _map =mapView;
}

-(void) initializePointerWithPositioner{
//    NSLog(@"creating marker with positioner %f",self.latitude);
//    NSLog(@"creating marker with positioner %f",self.longitude);
//
    self.positioner = [WRLDPositioner positionerAtCoordinate:CLLocationCoordinate2DMake(self.latitude,self.longitude)];
    [self.map addPositioner:self.positioner];
}

-(void)removeFromSuperview{
    [self.map removePositioner:self.positioner];
    [super removeFromSuperview];
}

-(void) positionerDidChange{
    if([self.positioner screenPointProjectionDefined])
    {
        CGPoint *screenPoint = [self.positioner screenPointOrNull];
        if(screenPoint != nil)
        {
            CGPoint anchorUV = CGPointMake(0.5f, 0.5f);
            [WRLDViewAnchor positionView:self screenPoint:screenPoint anchorUV:&anchorUV];
        }
        [self setHidden:false];
    }
    else
    {
        [self setHidden:true];
    }
}

@end

