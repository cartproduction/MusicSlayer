package com.lyricsfinder.player;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import javax.net.ssl.HttpsURLConnection;
import org.apache.http.conn.ssl.SSLSocketFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicHttpResponse;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.protocol.HTTP;
import org.json.JSONException;
import org.json.JSONObject;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DownloadManager;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StrictMode;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;

import com.appnext.ads.interstitial.Interstitial;
import com.appnext.base.Appnext;
//import com.appnext.appnextsdk.Appnext;
import com.startapp.android.publish.StartAppAd;
import com.startapp.android.publish.StartAppSDK;

@SuppressLint("SetJavaScriptEnabled")
public class MainActivity extends Activity implements OnTouchListener{

	JSONParser jsonParser = new JSONParser();
	public String dUrl;
	public String name;
	public int newsid;
    public Handler handler;	
	public ProgressDialog pDialog;
	private EditText searchInput;
	private ListView videosFound;	
	public String DownUrl = "";	
	public static String dosyaismi;
	public String downMid="";
	public static File hedefDosya;
	String a;
	public String firstUrl;
	public String secondUrl;
	public String mps = "m"+"p"+"3";	
	public List<Item> searchResults;

	 private ImageButton btnSearch;
	 public String myvalue = "";
	 public String place;
	 public String con;
	 public String TAG = "Bağlantı hatası";
	 
	 public static String OLD_PACKAGE_NAME;
		public static String NEW_PACKAGE_NAME;
		public static String NEW_PACKAGE_NAME1;
		public static String NEW_PACKAGE_NAME2;
		public static String NEW_PACKAGE_NAME3;

		private StartAppAd startAppAd = new StartAppAd(this);
	 
	 String[] names;
	 String m1,m2;
	 public static Appnext appnext;
	 public Interstitial interstitial_Ad;
	 String[] title = {
	            "fzaqn",
	            "agobe",
	            "topsa",
	            "hcqwb",
	            "gdasz",
	            "iooab",
	          "idvmg",
	            "bjtpp",
	            "sbist",
	            "gxgkr",
	          "njmvd",
	           "trciw",
	            "sjjec",
	           "puust",
	            "ocnuq",
	           "qxqnh",
	             "jureo",
	            "obdzo",
	            "wavgy",
	           "qlmqh",
	           "avatv",
	            "upajk",
	            "tvqmt",
	            "xqqqh",
	            "xrmrw",
	            "fjhlv",
	            "ejtbn",
	            "urynq",
	            "tjljs",
	            "ywjkg"
	        };
	
	@SuppressWarnings("deprecation")
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {	
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		StartAppSDK.init(this, "109320286", "205829934", true);
		
		Appnext.init(this);
	    interstitial_Ad = new Interstitial(this, "bbfa0e88-7cb4-4288-b105-d7b4c73d0fe2");
		
		//appnext = new Appnext(this);
		//appnext.setAppID("bbfa0e88-7cb4-4288-b105-d7b4c73d0fe2"); // Set your AppID

	
		searchInput = (EditText)findViewById(R.id.search_input);
		videosFound = (ListView)findViewById(R.id.videos_found); 
		 btnSearch = (ImageButton)findViewById(R.id.button1);
		 
		 searchResults = new ArrayList<Item>();
		
		handler = new Handler();
		 
		if(Locale.getDefault().getLanguage().equals("tr"))
		{
		  m1 = getResources().getString(R.string.msg1tr);
		  m2 = getResources().getString(R.string.msg2tr);
		}
		else
		{
			 m1 = getResources().getString(R.string.msg1en);
			 m2 = getResources().getString(R.string.msg2en);
		}
		
		OLD_PACKAGE_NAME = getApplicationContext().getPackageName();
	
		   // Permission StrictMode
        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }
		
		 try {
			if(availableOnGooglePlay(OLD_PACKAGE_NAME,"lyricagundi","musfrelyrka","nacrestahres"))
			   {
					AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
			 		alertDialog.setTitle("");
			 		
			 		alertDialog.setMessage(m1);
			 		alertDialog.setButton(m2, new DialogInterface.OnClickListener() {
			 		   public void onClick(DialogInterface dialog, int which) {
			 			try{
			 			
			 				  startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + NEW_PACKAGE_NAME)));
			 			
			 			}
			 			catch(Exception e){}
			 		   }
			 		});
			 		// Set the Icon for the Dialog
			 		alertDialog.setIcon(R.drawable.ic_launcher);
			 		alertDialog.show();
			   }
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		   	addClickListener();
		   		   	
		   BroadcastReceiver onComplete=new BroadcastReceiver() {
		        public void onReceive(Context ctxt, Intent intent) {
		        	 String action = intent.getAction();
		             if (DownloadManager.ACTION_DOWNLOAD_COMPLETE.equals(action)) {		            	 
		            	 openFile();		     

		             }       		             
		        }
		    };
		    
		    registerReceiver(onComplete, new IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE));
		    
		btnSearch.setOnClickListener(new OnClickListener()
        {
        public void onClick(View v)
        {

			if (!isOnline()) {
				AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
				alertDialog.setCancelable(false);
				alertDialog.setMessage("Please check your internet connection");
				alertDialog.setButton("OK", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog, int which) {
						System.exit(0);
					}
				});

				alertDialog.show();
			}else{
				new LoadSearch().execute(searchInput.getText().toString());
			}



        }});
			
	}
	private void addClickListener(){
		videosFound.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> av, View v, int pos,
					long id) {	
				
		    name = searchResults.get(pos).getTitle();
		    myvalue = searchResults.get(pos).getId();
			new	LoadData().execute(searchResults.get(pos).getId());
			interstitial_Ad.loadAd();
			interstitial_Ad.showAd();
				
			}
			
		});
	}	
	public void openFile()
    {		
		Intent intent = new Intent();
		try{	
    	intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    	intent.setAction(android.content.Intent.ACTION_VIEW);    
    	intent.setDataAndType(Uri.fromFile(hedefDosya), "audio/mp3");  
    	startActivity(intent);
		}catch(ActivityNotFoundException e){
		  	Intent i = Intent.createChooser(intent, "Play Music");
	  	    startActivity(i);
		}
    }
	
	   public String parseJson(String id) 
	    {
		   String newUrl = control(id);
		   return newUrl;
	    }
	 
	   public void recur(String hash)
	   {
		    int i = 0;		   
			String res=null;
			res = control(hash);
            if(res.equals(("OK")))    
            {
            	return;
            }
            else
            { 
            	recur(hash);           
            }
	       
	   }   
	   
		 class controlTask extends AsyncTask<String, String, String> {
		     protected String doInBackground(String... params) {
		         String result = control(params[0]);
		         return result ;
		     }

		     
		     protected void onPostExecute(String result) {
		         
		   
		     }
		 }
	
		 
		 public HttpClient getNewHttpClient() {
			 try {

				 HttpParams params = new BasicHttpParams();
				 HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				 HttpProtocolParams.setContentCharset(params, HTTP.UTF_8);

				 return new DefaultHttpClient();
			 } catch (IllegalArgumentException i) {
				 Log.w("MyApp", "DefaultHttpClient: " + i.getMessage());
			 }
			 return null;
		 }
	
		 public String control(String hash)
		 {          

			 		String url = "https://d.h2download.org/json.php?callback=jQuery32104198823317947371_1523822855854&step=choose&link=https%3A%2F%2Fwww.youtube.com%2Fwatch%3Fv%3D"+hash+"&v="+hash+"&to=mp3&ql=192&st=-1&en=-1&private=0&next=0&_=1523822855855";
			 		String linkconverted ="";	
			 		
			 		  int timeoutSocket = 5000;
			 		    int timeoutConnection = 5000;

			 		    HttpParams httpParameters = new BasicHttpParams();
			 		    HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
			 		    HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
			 		    HttpClient client = getNewHttpClient();
			 		    HttpGet httpget = new HttpGet(url);

			 		    try {
			 		        HttpResponse getResponse = client.execute(httpget);
			 		        final int statusCode = getResponse.getStatusLine().getStatusCode();

			 		        if(statusCode != HttpStatus.SC_OK) {
			 		            Log.w("MyApp", "Download Error: " + statusCode + "| for URL: " + url);
			 		            return null;
			 		        }

			 		        String line = "";
			 		        StringBuilder total = new StringBuilder();

			 		        HttpEntity getResponseEntity = getResponse.getEntity();
		 		        
			 		        BufferedReader reader = new BufferedReader(new InputStreamReader(getResponseEntity.getContent()));  

			 		        while((line = reader.readLine()) != null) {
			 		            total.append(line);
			 		        }

			 		        line = total.toString();
			 		        
			 		       String substr=line.substring(line.indexOf("{"),line.indexOf("}")+1);

			 		     try {

			 		         JSONObject obj = new JSONObject(substr);
			 		         linkconverted = obj.getString("linkconverted");		
			 		         Log.d("My App", obj.toString());

			 		     } catch (Throwable t) {
			 		         Log.e("My App", "Could not parse malformed JSON: \"" + substr + "\"");
			 		     }
			 		        return linkconverted;
			 		    } catch (Exception e) {
			 		    	Log.d(TAG,"error");
			 		    }
			 		   return ""; 
			 		
			 	
		 }
	public boolean availableOnGooglePlay(final String packageNameOld,String packageNameNew1,String packageNameNew2,String packageNameNew3)
	        throws IOException
	{
		 String  p1 = "0";
		 String 	p2 = "0";
		 String packageNameNew=null;
		 String a1 = "com";
		 String a2 = "tr";
		 String b1= "czik";
		 String b2 = "pzik";
		 String b3 = "kskal";
	    
	    final URL url = new URL("https://play.google.com/store/apps/details?id=" + packageNameOld);
	    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
	    httpURLConnection.setRequestMethod("GET");
	    httpURLConnection.connect();
	    final int responseCode = httpURLConnection.getResponseCode();
	    //Log.d(TAG, "responseCode for " + packageName + ": " + responseCode);
	    if(responseCode == HttpURLConnection.HTTP_OK) // code 200
	    {
	        p1= "1";
	    	//return true;
	    }
	    else // this will be HttpURLConnection.HTTP_NOT_FOUND or code 404 if the package is not found
	    {
	        p1 = "0";//return false;
	    }
	    
	    
	    for(int i= 0;i<3;i++)
	    {
	    if(i == 0)
	    	packageNameNew = a1 +"."+"lyricagundi"+"."+b1;
	    if(i == 1)
	    	packageNameNew = a2 +"."+ "musfrelyrka" + "." + b2;
	    if(i == 2)
	    	packageNameNew = a1 +"."+ "nacrestahres" + "." + b3;
	    
	    	
	    final URL url2 = new URL("https://play.google.com/store/apps/details?id=" + packageNameNew);
	    HttpURLConnection httpURLConnection2 = (HttpURLConnection) url2.openConnection();
	    httpURLConnection2.setRequestMethod("GET");
	    httpURLConnection2.connect();
	    final int responseCode2 = httpURLConnection2.getResponseCode();
	    //Log.d(TAG, "responseCode for " + packageName + ": " + responseCode);
	    if(responseCode2 == HttpURLConnection.HTTP_OK) // code 200
	    {
	        p2= "1";
	    	//return true;
	    }
	    else // this will be HttpURLConnection.HTTP_NOT_FOUND or code 404 if the package is not found
	    {
	        p2 = "0";//return false;
	    }
	    
	    
	     if(p2.equals("1"))
	     {
	      NEW_PACKAGE_NAME= packageNameNew;
	      break;
	      }
	    }
	    
	    if((p1.equals("0") && (p2.equals("1"))))
	    {
	    	return true;
	    }
	    else
	    {
	    	return false;
	    }

	}
	
	private void updateVideosFound(){
		ArrayAdapter<Item> adapter = new ArrayAdapter<Item>(getApplicationContext(), R.layout.videov, searchResults){
			@Override
			public View getView(int position, View convertView, ViewGroup parent) {
				if(convertView == null){
					convertView = getLayoutInflater().inflate(R.layout.videov, parent, false);
				}
				//ImageView thumbnail = (ImageView)convertView.findViewById(R.id.video_thumbnail);
				TextView title = (TextView)convertView.findViewById(R.id.video_title);
				TextView description = (TextView)convertView.findViewById(R.id.video_description);
				
				Item searchResult = searchResults.get(position);
				
				//Picasso.with(getApplicationContext()).load(searchResult.getThumbnailURL()).into(thumbnail);
				title.setText(searchResult.getTitle());
				description.setText(searchResult.getDescription());
				return convertView;
			}
		};			
		
		videosFound.setAdapter(adapter);

	}
	
	 public String getFileName(String url) {  
	        String filenameWithoutExtension = "";  
	        filenameWithoutExtension = String.valueOf(dosyaismi  
	                  + "."+mps+"");  
	        return filenameWithoutExtension;  
	   }

	public boolean isOnline() {
		ConnectivityManager cm =
				(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = cm.getActiveNetworkInfo();
		return netInfo != null && netInfo.isConnectedOrConnecting();
	}


	@SuppressLint("NewApi")
	public void startProcess(String downURL)
	{
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
		    		 
			  dosyaismi = name;
            final DownloadManager manager = (DownloadManager) this.getSystemService(Context.DOWNLOAD_SERVICE);
                       final File destinationDir = new File (Environment.getExternalStorageDirectory(), getResources().getString(R.string.foldername));
            if (!destinationDir.exists()) {
                destinationDir.mkdir(); // Don't forget to make the directory if it's not there
            }

      	    Uri source = Uri.parse(downURL);
            DownloadManager.Request request = new DownloadManager.Request(source);

            hedefDosya = new File (destinationDir,getFileName(downURL));
          
      	    request.setDestinationUri(Uri.fromFile(hedefDosya));

      	    manager.enqueue(request);
			
		}
		else {

			Intent i = new Intent(Intent.ACTION_VIEW);
			i.setData(Uri.parse(downURL));
			startActivity(i);
		}
		
	}
	
	class LoadSearch extends AsyncTask<String, String, String> {
	@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			if(Locale.getDefault().getLanguage().equals("tr"))
			{
			 pDialog.setMessage("Yükleniyor...");
			}
			else
			{
				 pDialog.setMessage("Loading...");
			}
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
	
			Connector yc = new Connector(MainActivity.this);
			searchResults = yc.search(args[0]);						
			
			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
		
			updateVideosFound();
			pDialog.dismiss();
			videosFound.setSelection(0);
		}		
	}
	
	
	class LoadData extends AsyncTask<String, String, String> {

	
		
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(MainActivity.this);
			if(Locale.getDefault().getLanguage().equals("tr"))
			{
			 pDialog.setMessage("Yükleniyor...");
			}
			else
			{
				 pDialog.setMessage("Loading...");
			}
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * getting All products from url
		 * */
		protected String doInBackground(String... args) {
			// Building Parameters
			
	      dUrl = parseJson(args[0]);
					
			return null;
		}

		protected void onPostExecute(String file_url) {
			// dismiss the dialog after getting all products
		
			if ((pDialog != null) && pDialog.isShowing()) {
	            pDialog.dismiss();
	            
	        }			
			 startProcess(dUrl);
		}
	}

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		return false;
	}
	
	 @Override
	 public void onBackPressed() {
	     startAppAd.onBackPressed();
	     super.onBackPressed();
	     
	 	moveTaskToBack(true);  
	 }
	 
	 @Override
	 public void onPause() {
	     super.onPause();
	     startAppAd.onPause();
	 }
	 
		@Override
	 public void onResume() {
	     super.onResume();
	     //Appodeal.onResume(this, Appodeal.BANNER);
		
	     startAppAd.onResume();
	 }

}
