package com.imdb.and.webservice;



import com.imdb.and.model.DataObject;

import java.util.List;




/*
 * Class to define different callback wrappers from the server. In this case just one.
 */


public class Interfaces {
	
	public abstract interface APICallback {
		public void apiDidFinish(Response response, DataObject dataObjects);

	}


}
