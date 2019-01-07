package com.patterncat;

import java.io.File;
import java.io.IOException;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.patterncat.model.Vendor;
import com.sleepycat.je.DatabaseException;
import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityCursor;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.PrimaryIndex;
import com.sleepycat.persist.SecondaryIndex;
import com.sleepycat.persist.StoreConfig;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = BdbdemoApplication.class)
public class TestMy {
	
	private Environment myEnv;
	
	private EntityStore store;
	
	private PrimaryIndex<String, Vendor> vendorByName;
	private SecondaryIndex<String, String, Vendor> subVendor;
	
	private boolean readOnly = false;
	
	private File envHome = new File(System.getProperty("user.dir") + File.separator + "bdb");
	
	@Before
	public void prepare() {
		EnvironmentConfig config = new EnvironmentConfig();
		
		config.setReadOnly(readOnly);
		config.setAllowCreate(!readOnly);
		
		StoreConfig storeConfig = new StoreConfig();
		
		storeConfig.setReadOnly(readOnly);
		storeConfig.setAllowCreate(!readOnly);
		
		System.out.println("-------------------"+envHome.getAbsolutePath()+"-------------------");
		if (!envHome.exists()) {
            envHome.mkdir();
        }
		myEnv = new Environment(envHome, config);
		
		store = new EntityStore(myEnv, "EntityStor", storeConfig);
		
		vendorByName = store.getPrimaryIndex(String.class, Vendor.class);
	}
	
	@After
    public void close() {
        if (store != null) {
            try {
                store.close();
            } catch (DatabaseException dbe) {
                dbe.printStackTrace();
            }
        }

        if (myEnv != null) {
            try {
                // Finally, close the store and environment.
                myEnv.close();
            } catch (DatabaseException dbe) {
                dbe.printStackTrace();
            }
        }
    }
	
	@Test
    public void putData() throws IOException {
        for (int i=1; i < 10; i++) {
            Vendor theVendor = new Vendor();
            theVendor.setVendorName(i+"");
            theVendor.setAddress(i+"");
            theVendor.setCity(i+"");
            theVendor.setState(i+"");
            theVendor.setZipcode(i+"");
            theVendor.setBusinessPhoneNumber(i+"");
            theVendor.setRepName(i+"");
            theVendor.setRepPhoneNumber(i+"");
            // Put it in the store. Because we do not explicitly set
            // a transaction here, and because the store was opened
            // with transactional support, auto commit is used for each
            // write to the store.
            vendorByName.put(theVendor);
            myEnv.sync();
        }
	}
	
	@Test
    public void getAllInventory() {
        // Get a cursor that will walk every
        // inventory object in the store.
//        EntityCursor<Vendor> items = vendorByName.entities();
        
        EntityCursor<Vendor> items = subVendor.subIndex("6").entities();

        try {
        	System.out.println("开始的时候");
            for (Vendor item : items) {
                System.out.println(ToStringBuilder.reflectionToString(item));
            }
            System.out.println("结束的时候");
        } finally {
            items.close();
        }
    }
	
	
	

}
