package brere.nat.uploader;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.Context;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractUploader {
	
	private static final Class<?>[] EMPTY_PARAM_TYPES = new Class<?>[0];

	private static final Object[] EMPTY_ARGS = new Object[0];

	private static final Logger LOG = LoggerFactory.getLogger(AbstractUploader.class);
	
	private static final String EMPTY_STR = "";
	private static final String SET = "set";
	private static final long MAX_FILE_SIZE = 8589934592L;
	private static final int MAX_MEM_SIZE = 1073741824;
	
	@Context
	protected HttpServletResponse response;
	@Context
	protected HttpServletRequest request;

	/**
	 * 
	 * @return
	 */
	public ServletFileUpload getFileUploader() {
		// Get System Temporary Directory
		final String tempDir = System.getProperty("java.io.tmpdir");
		final File repository = new File(tempDir);
		
		// Create a factory for disk-based file items
		final DiskFileItemFactory factory = new DiskFileItemFactory(MAX_MEM_SIZE, repository);
		
		// Create a new file upload handler
		final ServletFileUpload upload = new ServletFileUpload(factory);
		upload.setSizeMax(MAX_FILE_SIZE);
		return upload;
	}
	
	/**
	 * 
	 * @param <T>
	 * @param items
	 * @param clazz
	 * @return instance of {@code T}, May be null if no Empty Constructor was found on the given {@code clazz}
	 */
	public <T> T getObjectFromUpload(List<FileItem> items, Class<T> clazz) {
		final Map<String, Method> methodMap = getSetterMap(clazz);
		T result = null;
		try {
			result = clazz.getConstructor(EMPTY_PARAM_TYPES).newInstance(EMPTY_ARGS);
		} catch (InstantiationException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			LOG.warn(e.getClass().getName() + " When getting/invoking empty Contructor for " + clazz.getName(), e);
		}
		
		if (result != null) {
			for (final FileItem item : items) {
				if (methodMap.containsKey(item.getFieldName())) {
					try {
						final Method method = methodMap.get(item.getFieldName());
						final Object[] args = getArgs(item, method.getParameters()[0]);
						
						method.invoke(result, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOG.warn(e.getClass().getName() + " When invoking Setter " + methodMap.get(item.getFieldName()).getName(), e);
					}
				}
				if (methodMap.containsKey("name") && "file".equals(item.getFieldName())) {
					final Method setName = methodMap.get("name");
					try {
						Object[] args = {item.getName()};
						setName.invoke(result, args);
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
						LOG.warn(e.getClass().getName() + " When invoking Setter " + setName.getName(), e);
					}
				}
			}
		}
		
		return result;
	}

	/**
	 * Tries to convert the value of {@code item.getString()} to the type of the {@code param}
	 * @param item
	 * @param param
	 * @return
	 */
	private Object[] getArgs(final FileItem item, final Parameter param) {
		
		final Object[] args = new Object[1];
		
		if (Integer.class.equals(param.getType()) || int.class.equals(param.getType())) {
			args[0] = Integer.valueOf(item.getString());
			
		} else if (Long.class.equals(param.getType()) || long.class.equals(param.getType())) {
			args[0] = Long.valueOf(item.getString());
			
		} else if (Double.class.equals(param.getType()) || double.class.equals(param.getType())) {
			args[0] = Double.valueOf(item.getString());
			
		} else if (Byte.class.equals(param.getType()) || byte.class.equals(param.getType())) {
			args[0] = Byte.valueOf(item.getString());
			
		} else if (Short.class.equals(param.getType()) || short.class.equals(param.getType())) {
			args[0] = Short.valueOf(item.getString());
			
		} else if (Float.class.equals(param.getType()) || float.class.equals(param.getType())) {
			args[0] = Float.valueOf(item.getString());
			
		} else if (byte[].class.equals(param.getType()) || Byte[].class.equals(param.getType())) {
			args[0] = item.get();
			
		} else {
			// Default to String
			args[0] = item.getString();
			
		}
		return args;
	}

	private <T> Map<String, Method> getSetterMap(Class<T> clazz) {
		final Map<String, Method> methodMap = new HashMap<>();
		for (final Method method : clazz.getMethods()) {
			if (method.getName().startsWith(SET) && method.getParameterCount() == 1) {
				// Put Method name
				methodMap.put(method.getName().replace(SET, EMPTY_STR), method);
				// Put Camel Case method Name
				methodMap.put(method.getName().replace(SET, EMPTY_STR).substring(0, 1).toLowerCase() + method.getName().replace(SET, EMPTY_STR).substring(1), method);
			}
		}
		return methodMap;
	}

}
