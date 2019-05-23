package dk.jarry.wildfly.demo.infinispan;

import java.io.IOException;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.infinispan.Cache;
import org.infinispan.CacheCollection;
import org.infinispan.CacheSet;

/**
 * Servlet implementation class Test
 */
@WebServlet("/Test")
public class Test extends HttpServlet {

	private static final long serialVersionUID = 1L;

	@Inject
	Cache<Object, Object> cache;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// Cache<Object, Object> cache = producer.getCache();

		HttpSession session = request.getSession();

		session.setAttribute("key", "value");

		response.getWriter().append("Served at: ").append(request.getContextPath());

		response.getWriter().append("\n").append("Session id : ").append(session.getId());

		response.getWriter().append("\n").append("--------------------");

		Enumeration<String> atts = session.getAttributeNames();
		while (atts.hasMoreElements()) {
			String attr = atts.nextElement();
			response.getWriter().append("\n").append("Attribute: " + attr + ", Value: " + session.getAttribute(attr));
		}

		try {
			response.getWriter().append("\n").append("--------------------");
			response.getWriter().append("\n").append("cache.getName(): " + cache.getName());
			response.getWriter().append("\n").append("cache.getListeners(): ");
			for (Object o : cache.getListeners()) {
				response.getWriter().append("\n").append(" - " + o.getClass().getName());
			}

		} catch (Exception e) {
			response.getWriter().append("\n").append("Error: " + e.getMessage());
		}

		response.getWriter().append("\n").append("--------------------");
		response.getWriter().append("\n").append("cache.size() : " + cache.size());
		response.getWriter().append("\n").append("cache.getVersion() : " + cache.getVersion());
		response.getWriter().append("\n").append("--------------------");
		
		cache.put("MyKey", "MyValue");
		
		response.getWriter().append("\n").append("--KeySet");
		
		CacheSet<Object> keySet = cache.keySet();
		response.getWriter().append("\n").append("keySet.size() : " + keySet.size());
		String myKey = keySet.stream().findFirst().get().toString();
		response.getWriter().append("\n").append(myKey + " : " + cache.get(myKey));
		
		keySet.stream().forEach(o -> System.out.println(o + "\n"));
		
		List<Object> list = Arrays.asList(keySet.toArray());
		response.getWriter().append("\n").append("list : " + list);
		
		for( Object o : list) {
			response.getWriter().append("\n").append(" - " + o.toString() + " : " + cache.get(o));
		}
		
		response.getWriter().append("\n").append("--------------------");
		
		CacheCollection<Object> values = cache.values();

		values.stream().forEach(o -> System.out.println(o + "\n"));

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		doGet(request, response);

	}

}
