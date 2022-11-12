#ifdef RCT_NEW_ARCH_ENABLED
#import "Wrld3dView.h"

#import <react/renderer/components/RNWrld3dViewSpec/ComponentDescriptors.h>
#import <react/renderer/components/RNWrld3dViewSpec/EventEmitters.h>
#import <react/renderer/components/RNWrld3dViewSpec/Props.h>
#import <react/renderer/components/RNWrld3dViewSpec/RCTComponentViewHelpers.h>

#import "RCTFabricComponentsPlugins.h"
#import "Utils.h"

using namespace facebook::react;

@interface Wrld3dView () <RCTWrld3dViewViewProtocol>

@end

@implementation Wrld3dView {
    UIView * _view;
}

+ (ComponentDescriptorProvider)componentDescriptorProvider
{
    return concreteComponentDescriptorProvider<Wrld3dViewComponentDescriptor>();
}

- (instancetype)initWithFrame:(CGRect)frame
{
  if (self = [super initWithFrame:frame]) {
    static const auto defaultProps = std::make_shared<const Wrld3dViewProps>();
    _props = defaultProps;

    _view = [[UIView alloc] init];

    self.contentView = _view;
  }

  return self;
}

- (void)updateProps:(Props::Shared const &)props oldProps:(Props::Shared const &)oldProps
{
    const auto &oldViewProps = *std::static_pointer_cast<Wrld3dViewProps const>(_props);
    const auto &newViewProps = *std::static_pointer_cast<Wrld3dViewProps const>(props);

    if (oldViewProps.color != newViewProps.color) {
        NSString * colorToConvert = [[NSString alloc] initWithUTF8String: newViewProps.color.c_str()];
        [_view setBackgroundColor: [Utils hexStringToColor:colorToConvert]];
    }

    [super updateProps:props oldProps:oldProps];
}

Class<RCTComponentViewProtocol> Wrld3dViewCls(void)
{
    return Wrld3dView.class;
}

@end
#endif
