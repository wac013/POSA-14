package edu.vuum.mocca;

import java.util.concurrent.locks.AbstractQueuedLongSynchronizer.ConditionObject;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.Condition;

/**
 * @class SimpleSemaphore
 * 
 * @brief This class provides a simple counting semaphore
 *        implementation using Java a ReentrantLock and a
 *        ConditionObject (which is accessed via a Condition). It must
 *        implement both "Fair" and "NonFair" semaphore semantics,
 *        just liked Java Semaphores.
 */
public class SimpleSemaphore {
    /**
     * Define a Lock to protect the critical section.
     */
    // TODO - you fill in here
	private final ReentrantLock m_RLock;

    /**
     * Define a Condition that waits while the number of permits is 0.
     */
    // TODO - you fill in here
	private final Condition m_CNoPermits;
	

    /**
     * Define a count of the number of available permits.
     */
    // TODO - you fill in here.  Make sure that this data member will
    // ensure its values aren't cached by multiple Threads..
	private volatile int m_nPermits = 0;

    public SimpleSemaphore(int permits, boolean fair) {
        // TODO - you fill in here to initialize the SimpleSemaphore,
        // making sure to allow both fair and non-fair Semaphore
        // semantics.
    	m_nPermits = permits;
    	m_RLock = new ReentrantLock(fair);
    	m_CNoPermits = m_RLock.newCondition();
    }

    /**
     * Acquire one permit from the semaphore in a manner that can be
     * interrupted.
     */
    public void acquire() throws InterruptedException {
        // TODO - you fill in here.
    	m_RLock.lockInterruptibly();
    	try
    	{
    		while(m_nPermits == 0)
    			m_CNoPermits.await();
    		m_nPermits--;
    	}
    	finally
    	{
    		m_RLock.unlock();
    	}
    }

    /**
     * Acquire one permit from the semaphore in a manner that cannot be
     * interrupted.
     */
    public void acquireUninterruptibly() {
        // TODO - you fill in here.
    	m_RLock.lock();
    	try
    	{
    		while(m_nPermits == 0)
    			m_CNoPermits.awaitUninterruptibly();
    		m_nPermits--;
    	}
    	finally
    	{
    		m_RLock.unlock();
    	}
    }

    /**
     * Return one permit to the semaphore.
     */
    public void release() {
        // TODO - you fill in here.
    	m_RLock.lock();
    	
    	try
    	{
    		m_nPermits++;
    		m_CNoPermits.signal();
    	}
    	finally
    	{
    		m_RLock.unlock();
    	}
    }

    /**
     * Return the number of permits available.
     */
    public int availablePermits() {
        // TODO - you fill in here by changing null to the appropriate
        // return value.    	
        return m_nPermits;
    }
}
