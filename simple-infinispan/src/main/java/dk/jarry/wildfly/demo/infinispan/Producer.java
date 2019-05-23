package dk.jarry.wildfly.demo.infinispan;

import java.util.Arrays;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Produces;
import javax.inject.Inject;

import org.infinispan.Cache;

@ApplicationScoped
public class Producer {

	@Resource(lookup = "java:jboss/infinispan/container/web")
	org.jboss.as.clustering.infinispan.DefaultCacheContainer container;

	@Inject
	SimpleListener listener;

	private Cache<Object, Object> cache;

	@PostConstruct
	public void initCache() {

		System.err.println("ClusterName : " + container.getClusterName());
		System.err.println("Name : " + container.getName());
		System.err.println("cacheExists : " + container.cacheExists("simple-infinispan.war"));
		System.err.println("ClusterNames [1] : " + Arrays.asList(container.getCacheNames())); 
		
		cache = container.getCache("default-server", true);

		System.err.println("ClusterNames [2] : " + Arrays.asList(container.getCacheNames())); 
		
		cache.addListener(listener);
	}

	@Produces
	public org.infinispan.Cache<Object, Object> getCache() {
		return cache;
	}

	public void setCache(org.infinispan.Cache<Object, Object> cache) {
		this.cache = cache;
	}

}