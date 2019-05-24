package dk.jarry.wildfly.demo.cluster;

import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.Resource;

import org.wildfly.clustering.Registration;
import org.wildfly.clustering.group.Group;
import org.wildfly.clustering.group.GroupListener;
import org.wildfly.clustering.group.Membership;
import org.wildfly.clustering.group.Node;

public class MyGroupListener implements GroupListener {
	
    @Resource(lookup = "java:jboss/clustering/group/default") 
    private Group group;
    
    private Registration listenerRegistration;

    @PostConstruct
    public void init() {
        this.listenerRegistration = this.group.register(this);
        System.out.println("Initial membership: " + this.group.getMembership().getMembers());
    }

    @PreDestroy
    public void destroy() {
        this.listenerRegistration.close(); 
    }

    @Override
    public void membershipChanged(Membership previous, Membership current, boolean merged) {
    	
        List<Node> previousMembers = previous.getMembers();
        
        List<Node> currentMembers = current.getMembers();
        
        List<Node> joiners = currentMembers.stream() //
        		.filter(member -> !previousMembers.contains(member)) //
        		.collect(Collectors.toList());
        
        if (!joiners.isEmpty()) {
            System.out.println("Welcome: " + joiners);
        }

        List<Node> leavers = previousMembers.stream() //
        		.filter(member -> !currentMembers.contains(member)) //
        		.collect(Collectors.toList());
        
        if (!leavers.isEmpty()) {
            System.out.println("Goodbye: " + leavers);
        }        
    }
    
}