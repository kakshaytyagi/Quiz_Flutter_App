package org.app.edufun.Configuration.hb_config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = { "classpath:directories-${property.environment}.properties" })

public class DirectoriesProperties {

	@Value("${storage.file.dir}")
	private String storage_dir;

	public String getStorage_dir() {
		return storage_dir;
	}

}
