# ServiceException

## Why Start the Service on Android O and encounter a problem i.e Context.startForegroundService did not then call Service.startForeground


Android 8.0 has a complex feature; the system does not allow background applications to create background services. Therefore, Android 8.0 introduces a completely new method, Context.startForegroundService(), to start a new service in the foreground.
After the system creates the service, the app has 5 seconds to call the service’s startForeground() method to display the user-visible notification for the new service. If the app does not call startForeground() within this time limit, the system will stop the service and declare the application as ANR.

However, when calling: context.startForegroundService(intent), the following ANR is reported. The startForegroundService() document indicates that startForeground() is called after the service is started.



## Solution

1. Handle the exception and start foreground service

public void startService(View view) throws InterruptedException {

    Intent serviceIntent = new Intent(this, AService.class);
    try {
      startService(serviceIntent);
      Thread.sleep(12000);    // for verification the solution   As docs states > 5sec will lead to Exception
    }catch ( Exception e1){
      if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        this.startForegroundService(serviceIntent);
        Thread.sleep(12000);
      }else {
        this.startService(serviceIntent);
        Thread.sleep(12000);
      }
    }

  }

2. Call the startForeground

  private void dummyNotification() {
    NotificationChannel channel = null;
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      channel = new NotificationChannel(CHANNEL_ID,CHANNEL_NAME,
        NotificationManager.IMPORTANCE_HIGH);
    }

    NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
      manager.createNotificationChannel(channel);
    }

    Notification notification = null;
    if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
      notification = new Notification.Builder(getApplicationContext(),CHANNEL_ID).build();
    }
    startForeground(1, notification);
  }
  
  Build the source code 
  
  VOILA! NO CRASH
