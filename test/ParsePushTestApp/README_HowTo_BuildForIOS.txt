Tutorial: http://www.raywenderlich.com/32960/apple-push-notification-services-in-ios-6-tutorial-part-1

Issue: no valid aps: 
1. https://www.google.nl/webhp?sourceid=chrome-instant&rlz=1C1CHFX_enNL640NL640&ion=1&espv=2&ie=UTF-8#q=no%20valid%20aps-environment%20entitlement%20string%20found%20for%20application
2. Manually signing http://stackoverflow.com/questions/5681172/bundle-identifier-and-push-certificate-aps-environment-entitlement-error
http://stackoverflow.com/questions/15634188/resigning-an-ios-provisioning-profile
http://stackoverflow.com/questions/6896029/re-sign-ipa-iphone
- Initial result: code object is not signed at all

Error: "no valid 'aps-environment' entitlement string found for application"

Tips
- Registration for push different in iOS 8: http://stackoverflow.com/questions/4086599/why-didregisterforremotenotificationswithdevicetoken-is-not-called

Device token registration not working
- (@"" trick) http://stackoverflow.com/questions/31116849/parse-com-devicetoken-and-pfinstallation-not-saved See also: http://stackoverflow.com/questions/31181428/parse-installation-table-not-registering-devicetoken

// ios.add_libs
Foundation.framework;AudioToolbox.framework;CFNetwork.framework;CoreGraphics.framework;CoreLocation.framework;QuartzCore.framework;Security.framework;StoreKit.framework;SystemConfiguration.framework;libz.dylib;libsqlite3.dylib;Parse.a;Bolts.a

//Extra add_libs: Parse.a and Bolts.a (renamed from original non .a files)

// If you're using the -ObjC linker flag required by some third-party libraries, add these as well: 
// ios.add_libs
Accounts.framework;Social.framework

// Also remember to enable -ObjC linker flag with 
// ios.objC

// ios.glAppDelegateHeader
#import "Parse.h" // <Parse/Parse.h> --> "Parse.h" since included libraries are flattened


// ios.afterFinishLaunching
[Parse setApplicationId:@"j1KMuH9otZlHcPncU9dZ1JFH7cXL8K5XUiQQ9ot8" clientKey:@"V6ZUyBtfERtzbq6vjeAb13tiFYij980HN9nQTWGB"];

UIUserNotificationType userNotificationTypes = (UIUserNotificationTypeAlert |
                                                  UIUserNotificationTypeBadge |
                                                  UIUserNotificationTypeSound);
  UIUserNotificationSettings *settings = [UIUserNotificationSettings settingsForTypes:userNotificationTypes
                                                                           categories:nil];
  [application registerUserNotificationSettings:settings];
  [application registerForRemoteNotifications];
  
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Wait" message:@"Are you sure you want to delete this.  This action cannot be undone" delegate:nil cancelButtonTitle:@"Delete" otherButtonTitles:@"Cancel", nil];
 [alert show];
 [alert autorelease];

// ios.glAppDelegateBody
// Following three methods should be added without line breaks:


- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {PFInstallation *currentInstallation = [PFInstallation currentInstallation]; [currentInstallation setDeviceTokenFromData:deviceToken]; currentInstallation.channels = @[@"global"];[currentInstallation saveInBackground];} - (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error { if (error.code == 3010) { NSLog(@"Push notifications are not supported in the iOS Simulator.");} else { NSLog(@"application:didFailToRegisterForRemoteNotificationsWithError: %@", error);}} - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {[PFPush handlePush:userInfo];}

// With line breaks for readability of tutorial

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken {
    PFInstallation *currentInstallation = [PFInstallation currentInstallation];
	currentInstallation.deviceToken = @""; // Tip from stackoverflow!!!
    [currentInstallation setDeviceTokenFromData:deviceToken];
    currentInstallation.channels = @[@"global"];
    [currentInstallation saveInBackground];
}

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {
    if (error.code == 3010) {
        NSLog(@"Push notifications are not supported in the iOS Simulator.");
    } else {
        // show some alert or otherwise handle the failure to register.
        NSLog(@"application:didFailToRegisterForRemoteNotificationsWithError: %@", error);
    }
}

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
    [PFPush handlePush:userInfo];
}

// With popups


- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken { PFInstallation *currentInstallation = [PFInstallation currentInstallation];  [currentInstallation setDeviceTokenFromData:deviceToken]; currentInstallation.channels = @[@"global"]; [currentInstallation saveInBackground]; UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Installation" message:@"Installation saved in background" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease]; } - (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {   UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Registration Error" message:error.localizedDescription delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease];    if (error.code == 3010) {        NSLog(@"Push notifications are not supported in the iOS Simulator.");    } else {        NSLog(@"application:didFailToRegisterForRemoteNotificationsWithError: %@", error);    }} - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Notification" message:@"Received push while app was running" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease];    [PFPush handlePush:userInfo];}

// With debugging

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken { 
  PFInstallation *currentInstallation = [PFInstallation currentInstallation];  
  currentInstallation.deviceToken = @""; // Tip from stackoverflow!!!
  [currentInstallation setDeviceTokenFromData:deviceToken]; 
  currentInstallation.channels = @[@"global"]; 
  
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Device token" message:currentInstallation.deviceToken delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; 
  [alert show]; 
  [alert autorelease]; 
  
  [currentInstallation saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {
    if (succeeded) {

        UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Installation saved" message:@"Installation saved in background" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; 
  [alert show]; 
  [alert autorelease];

    }

    if (error) {

         UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Installation not saved" message:error.localizedDescription delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; 
  [alert show]; 
  [alert autorelease];

    }
}];
} 

- (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {   
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Registration Error" message:error.localizedDescription delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; 
  [alert show]; 
  [alert autorelease];    
} 

- (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {
  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Notification" message:@"Received push while app was running" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; 
  [alert autorelease];    
  [PFPush handlePush:userInfo];
}


// Compressed

- (void)application:(UIApplication *)application didRegisterForRemoteNotificationsWithDeviceToken:(NSData *)deviceToken { PFInstallation *currentInstallation = [PFInstallation currentInstallation];  currentInstallation.deviceToken = @""; [currentInstallation setDeviceTokenFromData:deviceToken]; currentInstallation.channels = @[@"global"]; UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Device token" message:currentInstallation.deviceToken delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease]; [currentInstallation saveInBackgroundWithBlock:^(BOOL succeeded, NSError *error) {   if (succeeded) { UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Installation saved" message:@"Installation saved in background" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease];  }  if (error) { UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Installation not saved" message:error.localizedDescription delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease]; }}];} - (void)application:(UIApplication *)application didFailToRegisterForRemoteNotificationsWithError:(NSError *)error {   UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Registration Error" message:error.localizedDescription delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil];  [alert show];  [alert autorelease]; } - (void)application:(UIApplication *)application didReceiveRemoteNotification:(NSDictionary *)userInfo {  UIAlertView *alert = [[UIAlertView alloc] initWithTitle:@"Notification" message:@"Received push while app was running" delegate:nil cancelButtonTitle:@"Cancel" otherButtonTitles:@"Ok", nil]; [alert show]; [alert autorelease]; [PFPush handlePush:userInfo]; }