package dev.wolveringer.bungeeutil;

import java.util.ArrayList;

import net.md_5.bungee.api.plugin.Plugin;

public class AsyncCatcher {
	
	public static enum AsyncCatcherMode {
		DISABLED,
		EXCEPTION,
		WARNING;
		
		private AsyncCatcherMode() {}
	}
	
	private static AsyncCatcherMode mode = AsyncCatcherMode.EXCEPTION;
	private static ArrayList<ThreadGroup> unchecked_threads = new ArrayList<ThreadGroup>();
	private static ArrayList<Plugin> unchecked_plugins = new ArrayList<Plugin>();
	
	public static void catchOp(String reason) {
		if(mode != AsyncCatcherMode.DISABLED){
			if(unchecked_threads.size() != 0)
				for(Plugin p : unchecked_plugins)
					if(p.getDescription().getName().contains(Thread.currentThread().getThreadGroup().getName()))
						return;
			if(unchecked_threads.size() != 0)
				if(unchecked_threads.contains(Thread.currentThread().getThreadGroup()))
					return;
			if(mode == AsyncCatcherMode.EXCEPTION)
				throw new IllegalStateException("Asynchronous " + reason + "!"); 
			else if(mode == AsyncCatcherMode.WARNING){
				StackTraceElement e = ExceptionUtils.getCurruntMethodeStackTraceElement();
				if(e == null)
					BungeeUtil.getInstance().sendMessage("Async catcher catched from underknown src. Message: "+reason);
				else
					BungeeUtil.getInstance().sendMessage("Async catcher catched from "+e.getClassName()+"#"+e.getMethodName()+"("+e.getLineNumber()+"). Message: "+reason);
			}
		}
	}
	
	public static void disable(Plugin plugin){
		if(unchecked_threads.contains(Thread.currentThread().getThreadGroup()));
			//throw new UnsupportedOperationException("Thread alredy unregistered!");
		unchecked_threads.add(Thread.currentThread().getThreadGroup());
		unchecked_plugins.add(plugin);
	}
	public static void enable(Plugin plugin){
		if(!unchecked_threads.contains(Thread.currentThread().getThreadGroup()));
			//throw new UnsupportedOperationException("Thread alredy registered!");
		unchecked_plugins.remove(plugin);
		unchecked_threads.remove(Thread.currentThread().getThreadGroup());
	}
	@Deprecated
	public static void disableAll(){
		mode = AsyncCatcherMode.DISABLED;
	}

	public static void init() {
		mode = Configuration.getAsyncMode();
		if(mode == null)
			throw new RuntimeException("Async catcher mode not found!");
	}
}