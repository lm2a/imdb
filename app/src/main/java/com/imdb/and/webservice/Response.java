package com.imdb.and.webservice;

import android.content.Context;

import com.imdb.and.R;
import com.imdb.and.helpers.MMLog;
import com.imdb.and.model.DataObject;


public class Response extends DataObject {
	
	public boolean success;
	public int statusCode;
	public String errorCode;
	public String displayMessage;
	public String jsonResponse;
	public String location;
	
	public Response() {
		super();
	}

	public void generateDisplayMessage(Context ctx) {
		
		if(statusCode == 409){
			displayMessage = ctx.getResources().getString(R.string.message);
		}
	}
	
	public void logDetails() {
		MMLog.v("bebay (r-details) jresponse: " + jsonResponse);
		MMLog.v("bebay (r-details) success: " + success);
		MMLog.v("bebay (r-details) status: " + statusCode);
		MMLog.v("bebay (r-details) message: " + errorCode);
		MMLog.v("bebay (r-details) location: " + location);
	}

	


}
