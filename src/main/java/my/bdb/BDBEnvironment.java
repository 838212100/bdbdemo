package my.bdb;

import java.io.File;

import com.sleepycat.je.Environment;
import com.sleepycat.je.EnvironmentConfig;
import com.sleepycat.persist.EntityStore;
import com.sleepycat.persist.StoreConfig;

public class BDBEnvironment {
	
	//数据库环境
	private Environment env;
	//
	private EntityStore store;
	
	public BDBEnvironment(File file, boolean readOnly) {
		//目录是否存在  不存在则创建目录
		if(!file.exists()) {
			file.mkdirs();
		}
		
		//创建一个BDB环境配置对象
		EnvironmentConfig envConfig = new EnvironmentConfig();
		//是否只为可读 true为只读 false可读写
		envConfig.setReadOnly(readOnly);
		//不存在的时候是否创建 true可以重建 false不可
		envConfig.setAllowCreate(!readOnly);
		
		//创建一个数据存储配置对象
		StoreConfig sConfig = new StoreConfig();
		//是否只读 true为只读 false可读写
		sConfig.setReadOnly(readOnly);
		//不存在的时候是否创建 true创建 false不可
		sConfig.setAllowCreate(!readOnly);
		
		env = new Environment(file, envConfig);
		store = new EntityStore(env, "EntityStore", sConfig);
		
	}
	
	public void close() {
		//关闭存储对象
		if(store != null) {
			store.close();
		}
		//关闭环境
		if(env != null) {
			env.close();
		}
	}

	public Environment getEnv() {
		return env;
	}

	public void setEnv(Environment env) {
		this.env = env;
	}

	public EntityStore getStore() {
		return store;
	}

	public void setStore(EntityStore store) {
		this.store = store;
	}

}
