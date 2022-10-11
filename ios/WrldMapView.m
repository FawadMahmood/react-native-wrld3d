#import "WrldMapView.h"
#import <AVFoundation/AVFoundation.h>
#import <AVKit/AVKit.h>
#import "MarkerView.h"


@import Wrld;


@implementation WrldMapView

- (instancetype)init
{
    self = [super init];
    _markers = [NSMutableArray<MarkerView *> array];
    _markersCoords = [NSMutableArray<MapCoordinates *> array];
    [self addViewControllerAsSubView];
    return self;
}

- (void)dealloc
{

}

- (void)layoutSubviews
{
    [super layoutSubviews];

    if (_myViewController != nil) {
        _myViewController.view.frame = self.frame;
    }
}

- (void)removeFromSuperview {
    if (_myViewController != nil) {
         [_myViewController willMoveToParentViewController:nil];
         [_myViewController.view removeFromSuperview];
         [_myViewController removeFromParentViewController];
         _myViewController = nil;
         [super removeFromSuperview];
    }
}

//-(void) addSubview:(UIView *)view{
//    if([view isKindOfClass:[MarkerView class]]){
//        MarkerView *marker = (MarkerView*)view;
//        [super addSubview:(UIView*)marker];
//        NSLog(@"added a view %@", marker.location);
//        [view setHidden:true];
//    }else{
//        [super addSubview:(UIView*)view];
//    }
//}

-(void) addSubview:(UIView *)view{
    if([view isKindOfClass:[MarkerView class]] && !self.map){
            MarkerView *marker = (MarkerView*)view;
//            marker = [marker init];
//            NSLog(@"found in a subview %@" ,marker.location);
//            [marker setLocation:marker.location];

            [self.markers addObject:marker];
    }else if(self.map && [view isKindOfClass:[MarkerView class]]){
        MarkerView *marker = (MarkerView*)view;
        [marker setMap:self.map];
        [marker setLocation:marker.location];
        [super addSubview:(UIView*)marker];
        [marker initializePointerWithPositioner];
    }else{
        [super addSubview:(UIView*)view];
    }
}


//-(void) removeReactSubview:(UIView *)subview{
//    NSLog(@"Added some markers yo removeReactSubview");
//}

-(void) viewDidLoad{
    
}

-(void)addMapView{
    UIWindow *window = (UIWindow*)[[UIApplication sharedApplication] keyWindow];
    self.map = [[WRLDMapView alloc] initWithFrame:window.frame];
    self.map.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
         // set the center of the map and the zoom level
         [self.map setCenterCoordinate:CLLocationCoordinate2DMake(37.7858, -122.401)
                             zoomLevel:2
                              animated:NO];
    self.map.delegate = self;
    [_myViewController.view insertSubview:self.map atIndex:0];
    
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * .2);
    dispatch_after(delay, dispatch_get_main_queue(), ^(void){
        // do work in the UI thread here
        [self addMarkers];
    });
    
   
    
}

-(void) addMarkers{
    NSUInteger index = 0;
    for (MarkerView* marker in _markers)
    {
        [marker setMap:self.map];
        [marker setLocation:marker.location];
        [self addSubview:marker];
        [marker initializePointerWithPositioner];
        index++;
    }
}

-(void)addViewControllerAsSubView
{
    NSLog(@"Adding view controller");
    _myViewController = [UIViewController new];
    UIWindow *window = (UIWindow*)[[UIApplication sharedApplication] keyWindow];
    [window.rootViewController addChildViewController:_myViewController];
    _myViewController.view.frame = self.superview.frame;
    _myViewController.view.backgroundColor = UIColor.brownColor;
    [self addSubview:_myViewController.view];
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * .2);
    dispatch_after(delay, dispatch_get_main_queue(), ^(void){
        // do work in the UI thread here
        [self addMapView];
    });
    
    [_myViewController didMoveToParentViewController:window.rootViewController];
}

- (void)mapView:(WRLDMapView *)mapView positionerDidChange: (WRLDPositioner*)positioner
{
    for (UIView* child in self.subviews)
    {
        if([child isKindOfClass:[MarkerView class]]){
            MarkerView *marker = (MarkerView*)child;
            [marker positionerDidChange];
        }
    }
}


@end
