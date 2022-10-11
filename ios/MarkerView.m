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
   
    
    CLLocationDegrees latitude = self.location.latitude;
    CLLocationDegrees longitude =self.location.longitude;
    
    NSLog(@"creating marker with positioner %f",latitude);
    NSLog(@"creating marker with positioner %f",longitude);
    
    self.positioner = [WRLDPositioner positionerAtCoordinate:CLLocationCoordinate2DMake(self.latitude,self.longitude)];
//    self.positioner = [WRLDPositioner positionerAtCoordinate:CLLocationCoordinate2DMake(37.802355, -122.405848)];
    [self.map addPositioner:self.positioner];
//    NSLog(@"creating marker with positioner %@",self.map);
}

//- (void)mapView:(WRLDMapView *)mapView positionerDidChange: (WRLDPositioner*)positioner
//{
//
//    if([positioner screenPointProjectionDefined])
//    {
//        CGPoint *screenPoint = [positioner screenPointOrNull];
//        if(screenPoint != nil)
//        {
//            CGPoint anchorUV = CGPointMake(0.5f, 0.5f);
//            [WRLDViewAnchor positionView:self screenPoint:screenPoint anchorUV:&anchorUV];
//        }
//        [self setHidden:false];
//    }
//    else
//    {
//        [self setHidden:true];
//    }
//}

-(void) positionerDidChange{
//    NSLog(@"Position did changed called in marker");
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

