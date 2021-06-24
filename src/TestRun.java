import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


public class TestRun {
	
	static FirebaseInit firebaseInit;

	public static void main(String[] args) throws IOException {
		
		firebaseInit = new FirebaseInit();
				
		firebaseInit.initialize();
		firebaseInit.UpdateDataP1(false, true);
		
		
		
		

	}

}
