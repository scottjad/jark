package jark.util;

import java.util.ArrayList;
import java.util.List;

/* import org.apache.log4j.Logger; */

public class SystemThreadList
{
    /**
     * The list of threads.
     */
    private ArrayList _threads;

    /**
     * Constructor.  Creates a list of all the 
     * threads running in the JVM in the system.
     */
    public SystemThreadList()
    {
	_threads = new ArrayList();

	ThreadGroup tg = Thread.currentThread().getThreadGroup();
	if(tg != null)
	{
	    _threads.add(tg);
	    while(tg.getParent() != null)
	    {
		tg = tg.getParent();
		if(tg != null)
		{
		    _threads.add(tg);
		}
	    }
	}
    }

    /**
     * Returns the thread count.
     *
     * @return int
     */
    public int getThreadCount()
    {
	if(_threads == null)
	    return -1;
	else
	    return _threads.size();
    }

    /**
     * Returns the thread group at the given index.
     *
     * @param index
     * @return ThreadGroup
     */
    public ThreadGroup getThreadGroup(int index)
    {
	if(getThreadCount() < 1)
	    return null;
	else if((index < 0) || (index > (getThreadCount() - 1)))
	    return null;
	else
	    return (ThreadGroup)_threads.get(index);
    }

    /**
     * Prints out the list of threads.
     */
    public void printThreads()
    {
	System.out.println(toString());
    }

    /**
     * Returns a String representation of this
     * SystemThreadList.
     *
     * @return String
     */
    public String toString()
    {
	StringBuffer sb = new StringBuffer("[SystemThreadList:\n");

	if(getThreadCount() < 1)
	{
	    sb.append(" No Threads ");
	}
	else
	{
	    for(int i = 0; i < getThreadCount(); i++)
	    {
		sb.append(" ThreadGroup " + i + "= ");
		sb.append(getThreadGroup(i).toString());
		sb.append(", activeCount = " + getThreadGroup(i).activeCount());
		sb.append("\n");
	    }
	}

	// Total active count
	sb.append(" totalActiveCount = " + getTotalActiveCount() + "\n");

	sb.append(" (End of SystemThreadList)]");
	return sb.toString();
    }

    /**
     * Returns the total active count: goes over
     * every group in the list, and sums their activeCount()
     * results.
     *
     * @return int
     */
    public int getTotalActiveCount()
    {
	if(getThreadCount() < 1)
	    return 0;
	else
	{
	    int totalActiveCount = 0;
	    ThreadGroup tg = null;
	    for(int i = 0; i < getThreadCount(); i++)
	    {
		tg = getThreadGroup(i);
		totalActiveCount += tg.activeCount();
	    }

	    return totalActiveCount;
	}
    }

    /**
     * Returns the root thread group, i.e.
     * the one whose parent is null.
     *
     * @return ThreadGroup
     */
    public ThreadGroup getRootThreadGroup()
    {
	if(getThreadCount() < 1)
	    return null;
	else
	{
	    ThreadGroup tg = null;
	    for(int i = 0; i < getThreadCount(); i++)
	    {
		tg = getThreadGroup(i);
		if(tg.getParent() == null)
		{
		    return tg;
		}
	    }

	    // If we got here, we didn't find one, so return null.
	    return null;
	}
    }

    /**
     * Gets all the threads.
     *
     * @return Thread[]
     */
    public Thread[] getAllThreads()
    {
	int estimatedCount = getTotalActiveCount();
	
	// Start with array twice size of estimated,
	// to be safe.  Trim later.
	Thread[] estimatedThreads = new Thread[estimatedCount * 2];
	
	// Locate root group
	ThreadGroup rootGroup = getRootThreadGroup();
	if(rootGroup == null)
	{
	    return null;
	}

	int actualCount = rootGroup.enumerate(estimatedThreads, true);

	// Check that something was returned
	if(actualCount < 1)
	    return null;

	// Copy into actualThreads of correct size
	Thread[] actualThreads = new Thread[actualCount];
	for(int i = 0; i < actualThreads.length; i++)
	{
	    actualThreads[i] = estimatedThreads[i];
	}

	return actualThreads;
    }

    /**
     * Gets all the threads whose name contains the
     * given string.  The search is CASE-SENSITIVE.
     *
     * @param nameMatch
     * @return List
     */
    public List getThreadsWithNameMatch(String nameMatch)
    {
	Thread[] allThreads = getAllThreads();
	if((allThreads == null) || (allThreads.length < 1))
	{
	    return null;
	}
	else
	{
	    ArrayList matchingThreads = new ArrayList();
	    for(int i = 0; i < allThreads.length; i++)
	    {
		if(allThreads[i].getName().indexOf(nameMatch) > -1)
		    matchingThreads.add(allThreads[i]);
	    }

	    if((matchingThreads == null) || (matchingThreads.size() < 1))
		return null;
	    else
		return matchingThreads;
	}
    }

    /**
     * Tries to destory the root group.
     */
    public void destroyRootThreadGroup()
    {
	ThreadGroup rootGroup = getRootThreadGroup();
	if(rootGroup == null)
	{
	    return;
	}
	else
	{
	    rootGroup.destroy();
	}
    }

    /**
     * Main.  Tests the functionality.
     *
     * @param args
     */
    public static void main(String[] args)
    {
	System.out.println("SystemThreadList: main(): starting.");

	// Create list
	SystemThreadList stl = new SystemThreadList();

	// Print info
	System.out.println("SystemThreadList: main(): calling SystemThreadList.toString().");
	stl.printThreads();

	// Get all threads
	System.out.println("SystemThreadList: main(): printing individual thread info.");
	Thread[] allThreads = stl.getAllThreads();

	if((allThreads == null) || (allThreads.length < 1))
	{
	    System.out.println("SystemThreadList: main(): allThreads is null or length < 1.");
	    System.exit(1);
	}

	// Print thread info
	Thread t = null;
	for(int i = 0; i < allThreads.length; i++)
	{
	    t = allThreads[i];
	    System.out.println("Thread " + i + " = " + t.toString());
	}

	// Try to destroy root group (Should throw a ThreadIllegalStateException)
	//System.out.println("SystemThreadList: main(): trying to destroy root group. ;)");
	//stl.destroyRootThreadGroup();

	// Try to get threads with "Signal" in their name.
	System.out.println("SystemThreadList: main(): getting threads with \"Signal\" in their name.");
	List signalThreads = stl.getThreadsWithNameMatch("Signal");
	if((signalThreads == null) || (signalThreads.size() < 1))
	{
	    System.out.println("... no signal threads found.");
	}
	else
	{
	    for(int j = 0; j < signalThreads.size(); j++)
	    {
		System.out.println("signalThreads[" + j + "] = " + ((Thread)signalThreads.get(j)).toString());
	    }
	}

	System.out.println("SystemThreadList: main(): done.");
    }
}
// End of class: SystemThreadTest

