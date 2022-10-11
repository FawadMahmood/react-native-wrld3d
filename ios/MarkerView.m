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

- (void)mapView:(WRLDMapView *)mapView positionerDidChange: (WRLDPositioner*)positioner
{
    
    if([positioner screenPointProjectionDefined])
    {
        CGPoint *screenPoint = [positioner screenPointOrNull];
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

