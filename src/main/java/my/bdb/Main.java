package my.bdb;

import java.io.File;

import org.apache.commons.lang3.builder.ToStringBuilder;

import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;

public class Main {

	private final static String FILE_PATH = "bdb_evn";
	
	private final static File FILE = new File(FILE_PATH);
	
	private final static boolean readOnly = false;
	
	private static PrimaryIndex<Long, User> uID;
	
	private static SecondaryIndex<String, Long, User> cID;
	
	
	public static void main(String[] args) {
		
		BDBEnvironment evn = new BDBEnvironment(FILE, readOnly);
		
		uID = evn.getStore().getPrimaryIndex(Long.class, User.class);
		
		cID = evn.getStore().getSecondaryIndex(uID, String.class, "city");
		
		User user = new User();
		user.setId(1L);
		user.setName("mike");
		user.setCity("beijing");
		
		uID.put(user);
		
		User userQ = uID.get(1L);
		System.out.println(ToStringBuilder.reflectionToString(userQ));
		
		
		EntityCursor<User> userC = cID.subIndex("beijing").entities();
		
		System.out.println(ToStringBuilder.reflectionToString(userC.iterator().next()));
		
		if(userC != null) {
			userC.close();
		}
		
		evn.close();
	}

}
