package org.xman.jmx.notification;

import javax.management.Notification;
import javax.management.NotificationBroadcasterSupport;

public class MyNotification extends NotificationBroadcasterSupport implements MyNotificationMBean {

    public MyNotification() {
	this.greeting = "Hello, My Notification!";
    }

    public MyNotification(String greeting) {
	this.greeting = greeting;
    }

    public void setGreeting(String greeting) {
	this.greeting = greeting;

	Notification notification = new Notification("org.xman.jmx.notification", this, -1, System.currentTimeMillis(),
		greeting);

	sendNotification(notification);

    }

    public String getGreeting() {
	return greeting;
    }

    public void printGreeting() {
	System.out.println(greeting);
    }

    private String greeting;

}
