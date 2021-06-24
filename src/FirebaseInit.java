import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.google.api.core.ApiFuture;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.cloud.firestore.CollectionReference;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.DocumentSnapshot;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.google.firebase.cloud.FirestoreClient;


public class FirebaseInit {
	
	
	
	@SuppressWarnings("deprecation")
	public void initialize() throws IOException {
		
		// TODO Auto-generated method stub
		FileInputStream serviceAccount = new FileInputStream("C:\\Users\\maili\\Desktop\\AI_gBEAI\\apg-interface-service.json");


FirebaseOptions options = new FirebaseOptions.Builder()
  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
  .build();

FirebaseApp.initializeApp(options);
Firestore db = FirestoreClient.getFirestore();
				
		
				
	}
	
	
	public void UpdateDataP1(boolean isP1win, boolean isP2win ) {
		Firestore db = FirestoreClient.getFirestore();
		Map<String, Object> data = new HashMap<>();
		int GetcountP1 = 0;
		int GetcountP2 = 0;
		int GetTotal = 0;
		if(isP1win == true) {
			
			ApiFuture<QuerySnapshot> future =
				    db.collection("user").whereEqualTo("isP1team", true).get();
				// future.get() blocks on response
				List<QueryDocumentSnapshot> documents = null;
				try {
					documents = future.get().getDocuments();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				for (DocumentSnapshot document : documents) {
					
					DocumentReference docRef = db.collection("user").document(document.getId());
					
					//System.out.println(document.getId()+" "+updateDataP1); 
				  //data.put(document.getData());
				  //System.out.println(document.getData());
					String Getdata = document.get("countP1win").toString() ;
					String Getdatatotal = document.get("countSendMessage").toString() ;
					GetcountP1 =Integer.parseInt(Getdata);
					GetTotal = Integer.parseInt(Getdatatotal);
					//System.out.println(GetTotal);
					GetcountP1++;
					GetTotal++;
					data.put("countP1win", GetcountP1);
					data.put("countSendMessage", GetTotal);
					ApiFuture<WriteResult> update = docRef.update(data);
					
				  try {
					  //System.out.println(document.getData());
					System.out.println("Update time : " + update.get().getUpdateTime());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  //data.put("zzcxcxc",document.getId().);
				}
			
			
			
			
			
		}
		else if(isP2win == true) {
			
			ApiFuture<QuerySnapshot> future =
				    db.collection("user").whereEqualTo("isP2team", true).get();
				// future.get() blocks on response
				List<QueryDocumentSnapshot> documents = null;
				try {
					documents = future.get().getDocuments();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				
				for (DocumentSnapshot document : documents) {
					
					DocumentReference docRef = db.collection("user").document(document.getId());
					
					//System.out.println(document.getId()+" "+updateDataP1); 
				  //data.put(document.getData());
				  //System.out.println(document.getData());
					String Getdata = document.get("countP2win").toString() ;
					String Getdatatotal = document.get("countSendMessage").toString() ;
					GetcountP2 =Integer.parseInt(Getdata);
					GetTotal = Integer.parseInt(Getdatatotal);
					GetcountP2++;
					GetTotal++;
					data.put("countP2win", GetcountP2);
					data.put("countSendMessage", GetTotal);
					ApiFuture<WriteResult> update = docRef.update(data);
					
				  try {
					System.out.println("Update time : " + update.get().getUpdateTime());
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				  //data.put("zzcxcxc",document.getId().);
				}
			
			
		}
		
		
	}
	
	public class City {
		
		
		
	}

}
