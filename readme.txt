-Splash screen during initial loading.
-Fragments used to show list and detail
-Adapter for movies list using ViewHolder pattern
-TCImageLoader object which is using LRUCache to cache images and implementing ComponentCallbacks2 lighten up memory in order to avoid be killed
-API class to make calls to the backend. Methods in the class require the caller to implement a callback 
 interface in order to receive both objects response and data. This is an old pattern I was using before using Retrofit.
-Singleton object to hold data.
-Subclass of Log to use a flag to activate or deactivate logging.
-There are two tests, one testing the Adapter and the other one testing a Utility class


