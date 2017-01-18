package com.utilities;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class G {
/*
 * Untuk membuat log ketika testing
 */
	public G(){
		
	}
	
	public static void l(String message){
		Log.e("Tempat Ibadah 31-8.1", message);
	}
	public static void n(String message, Context ctx){
		Toast.makeText(ctx, message, Toast.LENGTH_SHORT).show();
	}
}
