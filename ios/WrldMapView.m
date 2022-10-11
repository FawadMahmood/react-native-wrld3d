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

-(void) addSubview:(UIView *)view{
    if([view isKindOfClass:[MarkerView class]] && !self.map){
        NSLog(@"Added some markers yo");
        MarkerView *marker = (MarkerView*)view;
        [self.markers addObject:marker];
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
                             zoomLevel:15
                              animated:NO];
    [_myViewController.view insertSubview:self.map atIndex:0];
    [self addMarkers];
    
}

-(void) addMarkers{
    for (MarkerView* marker in _markers)
    {
//        NSLog(@"%@", marker);
        [self addSubview:marker];
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


@end
