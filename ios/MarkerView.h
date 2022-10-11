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

@import Wrld;

@interface MarkerView : UIView
@property (nonatomic, strong) WRLDMapView *map;
@end

