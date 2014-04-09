package heiderse.msu.edu.project1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import android.util.Log;
import android.util.Xml;

public class Service {

	private String _name;


	private String _password;
	
	private int _select;
	
	public String get_name() {
		return _name;
	}

	public void set_name(String _name) {
		this._name = _name;
	}

	public String get_password() {
		return _password;
	}

	public void set_password(String _password) {
		this._password = _password;
	}

	public int get_select() {
		return _select;
	}

	public void set_select(int _select) {
		this._select = _select;
	}
	
	private static final String USER = "demo";
	private static final String PASSWORD = "demo";
	private static final String MAGIC = "NechAtHa6RuzeR8x";
    private static final String CREATE_URL = "https://www.cse.msu.edu/~tranluan/cse476-project2/user/create2.php";
    private static final String LOGIN_URL = "https://www.cse.msu.edu/~tranluan/cse476-project2/user/login.php";
    private static final String GAME_READY_URL = "https://www.cse.msu.edu/~tranluan/cse476-project2/game/ready.php";
    private static final String UTF8 = "UTF-8";
	
 
    
	public Service() {
		// TODO Auto-generated constructor stub
		_name = null;
		_password = null;
	}
	
<<<<<<< HEAD
    public String getUser(int option) {
=======
    public InputStream getUser() {
>>>>>>> 0f337abb27f1dc392674196bfed4789381c33fc5
        // Create a get query
		Log.i("user",_name);
		Log.i("password",_password);
    	
		InputStream stream = null;
		
    	String query = "";
<<<<<<< HEAD
    	if(_name == null && option == 0)
    		query = LOGIN_URL + "?magic=" + MAGIC + "&user=" + USER + "&pw=" + PASSWORD;
    	else if (_name != null && option == 0)
    		query = LOGIN_URL + "?magic=" + MAGIC + "&user=" + _name + "&pw=" + _password;
    	else if (option == 1)
    		query = CREATE_URL + "?magic=" + MAGIC + "&user=" + _name + "&pw=" + _password;
=======
    	if(_name == null)
    		query = LOGIN_URL + "?magic=" + MAGIC + "&user=" + USER + "&pw=" + PASSWORD;
    	else
    		query = LOGIN_URL + "?magic=" + MAGIC + "&user=" + _name + "&pw=" + _password;
>>>>>>> 0f337abb27f1dc392674196bfed4789381c33fc5
    	
    	Log.i("query",query);
    	
        stream = request(query);
        
        return stream;
    }
    
    public boolean createUser(){
    	String query = CREATE_URL + "?magic=" + MAGIC + "&user=" + _name + "&pw=" + _password;
    	InputStream stream = null;
        stream = request(query);
        if (stream==null)
        	return false;
        
        return handleResult(stream);
    }
    
    public boolean isGameReady(String gameId){
    	InputStream stream = null;
    	
    	String query = GAME_READY_URL+"?magic=" + MAGIC + "&gameId=" + gameId;
        stream = request(query);
        if (stream==null)
        	return false;
        //logStream(stream);
        
        return handleResult(stream);
    }
    
    private InputStream request(String query){   	
    	try {
            URL url = new URL(query);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return null;
            }
            
            InputStream stream = conn.getInputStream();
            
            return stream;
            
        } catch (MalformedURLException e) {
            // Should never happen
            return null;
        } catch (IOException ex) {
            return null;
        }
<<<<<<< HEAD
        
        /**
         * Create an XML parser for the result
         */
        try {
            XmlPullParser xml = Xml.newPullParser();
            xml.setInput(stream, UTF8);
=======
    }
    
    private boolean handleResult(InputStream stream){
    	try {
            XmlPullParser xmlR = Xml.newPullParser();
            xmlR.setInput(stream, UTF8);
>>>>>>> 0f337abb27f1dc392674196bfed4789381c33fc5
            
            xmlR.nextTag();      // Advance to first tag
            xmlR.require(XmlPullParser.START_TAG, null, "stacker");
            
<<<<<<< HEAD
            String status = xml.getAttributeValue(null, "status");
            String player = xml.getAttributeValue(null, "player");
=======
            String status = xmlR.getAttributeValue(null, "status");
>>>>>>> 0f337abb27f1dc392674196bfed4789381c33fc5
            if(status.equals("no")) {
                return false;
            }
            
            // We are done
        } catch(XmlPullParserException ex) {
            return false;
        } catch(IOException ex) {
            return false;
        }
    	
    	return true;
    }
    
    public static void logStream(InputStream stream) {
        BufferedReader reader = new BufferedReader(
                new InputStreamReader(stream));
    
        Log.e("476", "logStream: If you leave this in, code after will not work!");
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                Log.e("476", line);
            }
        } catch (IOException ex) {
            return;
        }
    }
	
    public boolean CreateUser()
    {
    	_name = _name.trim();
        if(_name.length() == 0) {
            return false;
        }
        
        // Create an XML packet with the information about the current image
        XmlSerializer xml = Xml.newSerializer();
        StringWriter writer = new StringWriter();
        
        try {
            xml.setOutput(writer);
            
            xml.startDocument(UTF8, true);
            
            xml.startTag(null, "stacker");

            xml.attribute(null, "user", _name);
            xml.attribute(null, "pw", _password);
            xml.attribute(null, "magic", MAGIC);
            
            xml.endDocument();

        } catch (IOException e) {
            // This won't occur when writing to a string
            return false;
        }
        
        final String xmlStr = writer.toString();
        
        /*
         * Convert the XML into HTTP POST data
         */
        String postDataStr;
        try {
            postDataStr = "xml=" + URLEncoder.encode(xmlStr, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            return false;
        }
        
        /*
         * Send the data to the server
         */
        byte[] postData = postDataStr.getBytes();
        
        InputStream stream = null;
        try {
            URL url = new URL(CREATE_URL);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();

            conn.setDoOutput(true);
            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Length", Integer.toString(postData.length));
            conn.setUseCaches(false);
            
            
            OutputStream out = conn.getOutputStream();
            out.write(postData);
            out.close();

            int responseCode = conn.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_OK) {
                return false;
            }
            
            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                Log.i("hatter", line);
            } 
            
            stream = conn.getInputStream();
            //logStream(stream);
            
            /**
             * Create an XML parser for the result
             */
            try {
                XmlPullParser xmlR = Xml.newPullParser();
                xmlR.setInput(stream, UTF8);
                
                xmlR.nextTag();      // Advance to first tag
                xmlR.require(XmlPullParser.START_TAG, null, "stacker");
                
                // We are done
            } catch(XmlPullParserException ex) {
                return false;
            } catch(IOException ex) {
                return false;
            }
            
        } catch (MalformedURLException e) {
            return false;
        } catch (IOException ex) {
        	ex.printStackTrace();
            return false;
        } finally {
            if(stream != null) {
                try {
                    stream.close();
                } catch(IOException ex) {
                    // Fail silently
                }
            }
        }
        
        return true;
    }
}
