/*
 * Copyright 2015 Chidiebere Okwudire.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.parse4cn1.TestApp;


import com.codename1.system.NativeLookup;
import com.codename1.ui.Display;
import com.codename1.ui.Form;
import userclasses.StateMachine;

public class Main {
   
    private Form current;

    public void init(Object context) {
        // Pro users - uncomment this code to get crash reports sent to you automatically
        /*Display.getInstance().addEdtErrorHandler(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                evt.consume();
                Log.p("Exception in AppName version " + Display.getInstance().getProperty("AppVersion", "Unknown"));
                Log.p("OS " + Display.getInstance().getPlatformName());
                Log.p("Error " + evt.getSource());
                Log.p("Current Form " + Display.getInstance().getCurrent().getName());
                Log.e((Throwable)evt.getSource());
                Log.sendLog();
            }
        });*/
    }

    public void start() {
        if(current != null){
            current.show();
            return;
        }
        
        ParseNativeInterface parseNative = (ParseNativeInterface)NativeLookup.create(ParseNativeInterface.class);
        if (parseNative != null && parseNative.isSupported()) {
            parseNative.test("start()");
            parseNative.initializePushNotifications(
                    "j1KMuH9otZlHcPncU9dZ1JFH7cXL8K5XUiQQ9ot8", "V6ZUyBtfERtzbq6vjeAb13tiFYij980HN9nQTWGB");
        }
        
        new StateMachine("/theme"); 
    }

    public void stop() {
        current = Display.getInstance().getCurrent();
    }
    
    public void destroy() {
    }
}
