#import "WrldMapView.h"
#import <AVFoundation/AVFoundation.h>
#import <AVKit/AVKit.h>
#import "MarkerView.h"


@import Wrld;


@implementation WrldMapView

- (instancetype)init
{
    self = [super init];
    [self addViewControllerAsSubView];
//    NSLog(@"Adding view init");
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

-(void) addSubview:(UIView *)view{
    if([view isKindOfClass:[MarkerView class]]){
        NSLog(@"Added some markers yo");
    }else{
        [super addSubview:(UIView*)view];
    }
}


//-(void) removeReactSubview:(UIView *)subview{
//    NSLog(@"Added some markers yo removeReactSubview");
//}

-(void)addViewControllerAsSubView
{
    NSLog(@"Adding view controller");
    _myViewController = [UIViewController new];
    UIWindow *window = (UIWindow*)[[UIApplication sharedApplication] keyWindow];
    [window.rootViewController addChildViewController:_myViewController];
    _myViewController.view.frame = self.superview.frame;
    _myViewController.view.backgroundColor = UIColor.brownColor;
    [self addSubview:_myViewController.view];
    
    
    dispatch_time_t delay = dispatch_time(DISPATCH_TIME_NOW, NSEC_PER_SEC * 1.2);
    dispatch_after(delay, dispatch_get_main_queue(), ^(void){
        // do work in the UI thread here
        
        WRLDMapView *mapView = [[WRLDMapView alloc] initWithFrame:window.frame];
             mapView.autoresizingMask = UIViewAutoresizingFlexibleWidth | UIViewAutoresizingFlexibleHeight;
             // set the center of the map and the zoom level
             [mapView setCenterCoordinate:CLLocationCoordinate2DMake(37.7858, -122.401)
                                 zoomLevel:15
                                  animated:NO];
        [_myViewController.view insertSubview:mapView atIndex:0];
    });
   
    
    [_myViewController didMoveToParentViewController:window.rootViewController];
}

@end
